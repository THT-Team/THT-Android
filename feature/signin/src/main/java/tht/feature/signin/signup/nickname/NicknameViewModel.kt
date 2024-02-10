package tht.feature.signin.signup.nickname

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.tht.tht.domain.signup.usecase.CheckNicknameDuplicateUseCase
import com.tht.tht.domain.signup.usecase.FetchSignupUserUseCase
import com.tht.tht.domain.signup.usecase.PatchSignupDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import tht.core.ui.base.BaseStateViewModel
import tht.core.ui.base.SideEffect
import tht.feature.signin.StringProvider
import javax.inject.Inject

@HiltViewModel
class NicknameViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val fetchSignupUserUseCase: FetchSignupUserUseCase,
    private val checkNicknameDuplicateUseCase: CheckNicknameDuplicateUseCase,
    private val patchSignupDataUseCase: PatchSignupDataUseCase,
    private val stringProvider: StringProvider
) : BaseStateViewModel<NicknameUiState, NicknameViewModel.NicknameSideEffect>() {

    private val phone: String = savedStateHandle[KEY_PHONE] ?: ""

    override val _uiStateFlow: MutableStateFlow<NicknameUiState> = MutableStateFlow(
        NicknameUiState.DEFAULT.copy(
            maxLength = MAX_LENGTH
        )
    )

    private val validInputValue = MutableStateFlow("") // 중복 체크를 통과한 유효 데이터

    init {
        _uiStateFlow.map { it.nickname }
            .distinctUntilChanged()
            .debounce(500L)
            .filter { it.isNotBlank() && it != validInputValue.value }
            .onEach { input ->
                _uiStateFlow.update {
                    it.copy(
                        validation = NicknameUiState.NicknameValidation.Idle,
                        loading = true
                    )
                }
                checkNicknameDuplicateUseCase(input)
                    .onSuccess { isDuplicate ->
                        if (!isDuplicate) {
                            _uiStateFlow.update { it.copy(validation = NicknameUiState.NicknameValidation.Validate) }
                            validInputValue.value = input
                        } else {
                            _uiStateFlow.update {
                                it.copy(
                                    validation = NicknameUiState.NicknameValidation.Invalid(
                                        stringProvider.getString(
                                            StringProvider.ResId.DuplicateNickname
                                        )
                                    )
                                )
                            }
                        }
                    }.onFailure {
                        _uiStateFlow.update {
                            it.copy(
                                validation = NicknameUiState.NicknameValidation.Invalid(
                                    stringProvider.getString(
                                        StringProvider.ResId.DuplicateCheckFail
                                    )
                                )
                            )
                        }
                    }
                _uiStateFlow.update { it.copy(loading = false) }
            }.launchIn(viewModelScope)

        fetchSavedData()
    }

    private fun fetchSavedData() {
        if (phone.isBlank()) {
            _uiStateFlow.update {
                it.copy(
                    validation = NicknameUiState.NicknameValidation.Invalid(
                        stringProvider.getString(StringProvider.ResId.InvalidatePhone)
                    )
                )
            }
        }
        viewModelScope.launch {
            _uiStateFlow.update { it.copy(loading = true) }
            fetchSignupUserUseCase(phone)
                .onSuccess { user ->
                    _uiStateFlow.update { it.copy(nickname = user.nickname) }
                }.onFailure {
                    _sideEffectFlow.emit(
                        NicknameSideEffect.ShowToast(
                            stringProvider.getString(StringProvider.ResId.InvalidateSignupProcess) +
                                stringProvider.getString(StringProvider.ResId.CustomerService)
                        )
                    )
                }
            _uiStateFlow.update { it.copy(loading = false) }
        }
    }

    fun onTextInputEvent(text: String) {
        if (text.length <= MAX_LENGTH) {
            _uiStateFlow.update { it.copy(nickname = text) }
        }
    }

    fun onClearEvent() {
        _uiStateFlow.update {
            it.copy(
                nickname = "",
                validation = NicknameUiState.NicknameValidation.Idle
            )
        }
    }

    fun onBackgroundTouchEvent() {
        postSideEffect(NicknameSideEffect.KeyboardVisible(false))
    }

    fun onClickNextEvent() {
        if (phone.isBlank()) {
            _uiStateFlow.update {
                it.copy(
                    validation = NicknameUiState.NicknameValidation.Invalid(
                        stringProvider.getString(StringProvider.ResId.InvalidatePhone)
                    )
                )
            }
        }
        val nickname = _uiStateFlow.value.nickname
        if (nickname.isBlank()) {
            _uiStateFlow.update { it.copy(validation = NicknameUiState.NicknameValidation.Idle) }
            return
        }
        if (nickname != validInputValue.value) {
            postSideEffect(
                NicknameSideEffect.ShowToast(
                    stringProvider.getString(StringProvider.ResId.DuplicateCheckLoading)
                )
            )
            return
        }
        postSideEffect(NicknameSideEffect.KeyboardVisible(false))
        viewModelScope.launch {
            _uiStateFlow.update { it.copy(loading = true) }
            patchSignupDataUseCase(phone) {
                it.copy(nickname = nickname)
            }.onSuccess {
                _sideEffectFlow.emit(NicknameSideEffect.NavigateNextView)
            }.onFailure {
                _sideEffectFlow.emit(
                    NicknameSideEffect.ShowToast(
                        stringProvider.getString(StringProvider.ResId.SendAuthFail)
                    )
                )
            }
            _uiStateFlow.update { it.copy(loading = false) }
        }
    }
    sealed class NicknameSideEffect : SideEffect {
        data class ShowToast(val message: String) : NicknameSideEffect()
        data class KeyboardVisible(val visible: Boolean) : NicknameSideEffect()
        object NavigateNextView : NicknameSideEffect()
    }

    companion object {
        const val MAX_LENGTH = 12
        private const val KEY_PHONE = "phone"
    }
}
