package tht.feature.signin.signup.nickname

import androidx.lifecycle.viewModelScope
import com.tht.tht.domain.signup.usecase.CheckNicknameDuplicateUseCase
import com.tht.tht.domain.signup.usecase.FetchSignupUserUseCase
import com.tht.tht.domain.signup.usecase.PatchSignupNickNameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import tht.core.ui.base.BaseStateViewModel
import tht.core.ui.base.SideEffect
import tht.core.ui.base.UiState
import tht.feature.signin.StringProvider
import javax.inject.Inject

@HiltViewModel
class NicknameViewModel @Inject constructor(
    private val fetchSignupUserUseCase: FetchSignupUserUseCase,
    private val checkNicknameDuplicateUseCase: CheckNicknameDuplicateUseCase,
    private val patchSignupNickNameUseCase: PatchSignupNickNameUseCase,
    private val stringProvider: StringProvider
) : BaseStateViewModel<NicknameViewModel.NicknameUiState, NicknameViewModel.NicknameSideEffect>() {

    override val _uiStateFlow: MutableStateFlow<NicknameUiState> = MutableStateFlow(NicknameUiState.Default)

    private val _inputLength = MutableStateFlow(0)
    val inputLength = _inputLength.asStateFlow()

    private val _dataLoading = MutableStateFlow(false)
    val dataLoading = _dataLoading.asStateFlow()

    private val _inputValue = MutableStateFlow("") // 입력한 값. 검증을 위한 용도
    val inputValue = _inputValue.asStateFlow()

    private val validInputValue = MutableStateFlow("") // 중복 체크를 통과한 유효 데이터

    init {
        _inputValue.debounce(500L)
            .filter { it.isNotBlank() && it != validInputValue.value }
            .onEach {
                _dataLoading.value = true
                checkNicknameDuplicateUseCase(it)
                    .onSuccess { success ->
                        if (success) {
                            _uiStateFlow.value = NicknameUiState.ValidInput
                            validInputValue.value = it
                        } else {
                            _uiStateFlow.value = NicknameUiState.InvalidInput(
                                stringProvider.getString(
                                    StringProvider.ResId.DuplicateNickname
                                )
                            )
                        }
                    }.onFailure {
                        _uiStateFlow.value = NicknameUiState.InvalidInput(
                            stringProvider.getString(
                                StringProvider.ResId.DuplicateCheckFail
                            )
                        )
                    }.also { _ ->
                        _dataLoading.value = false
                    }
            }.launchIn(viewModelScope)

        _inputValue.filter { it.isNotBlank() }
            .onEach {
                _inputLength.value = it.length
                _uiStateFlow.value = when (it == validInputValue.value) {
                    true -> NicknameUiState.ValidInput
                    else -> NicknameUiState.Default
                }
            }.launchIn(viewModelScope)

        _inputValue.filter { it.isBlank() }
            .onEach {
                _inputLength.value = 0
                _uiStateFlow.value = NicknameUiState.Default
            }.launchIn(viewModelScope)
    }

    fun fetchSavedData(phone: String) {
        if (phone.isBlank()) {
            _uiStateFlow.value = NicknameUiState.InvalidPhoneNumber(
                stringProvider.getString(StringProvider.ResId.InvalidatePhone)
            )
        }
        viewModelScope.launch {
            _dataLoading.value = true
            fetchSignupUserUseCase(phone)
                .onSuccess {
                    _inputValue.value = it.nickname
                }.onFailure {
                    _sideEffectFlow.emit(
                        NicknameSideEffect.ShowToast(
                            stringProvider.getString(StringProvider.ResId.InvalidateSignupProcess) +
                                stringProvider.getString(StringProvider.ResId.CustomerService)
                        )
                    )
                }.also {
                    _dataLoading.value = false
                }
        }
    }

    fun textInputEvent(text: String?) {
        _inputValue.value = text ?: ""
    }

    fun backgroundTouchEvent() {
        postSideEffect(NicknameSideEffect.KeyboardVisible(false))
    }

    fun clickNextEvent(phone: String, text: String?) {
        if (phone.isBlank()) {
            _uiStateFlow.value = NicknameUiState.InvalidPhoneNumber(
                stringProvider.getString(StringProvider.ResId.InvalidatePhone)
            )
        }
        if (text.isNullOrBlank()) {
            _inputLength.value = 0
            _uiStateFlow.value = NicknameUiState.Default
            return
        }
        if (text != validInputValue.value) {
            postSideEffect(
                NicknameSideEffect.ShowToast(
                    stringProvider.getString(StringProvider.ResId.DuplicateCheckLoading)
                )
            )
            return
        }
        postSideEffect(NicknameSideEffect.KeyboardVisible(false))
        viewModelScope.launch {
            _dataLoading.value = true
            patchSignupNickNameUseCase(phone, text)
                .onSuccess {
                    _sideEffectFlow.emit(NicknameSideEffect.NavigateNextView)
                }.onFailure {
                    _sideEffectFlow.emit(
                        NicknameSideEffect.ShowToast(
                            stringProvider.getString(StringProvider.ResId.SendAuthFail)
                        )
                    )
                }.also {
                    _dataLoading.value = false
                }
        }
    }

    sealed class NicknameUiState : UiState {
        object Default : NicknameUiState()
        data class InvalidInput(val message: String) : NicknameUiState()
        object ValidInput : NicknameUiState()
        data class InvalidPhoneNumber(val message: String) : NicknameUiState()
    }
    sealed class NicknameSideEffect : SideEffect {
        data class ShowToast(val message: String) : NicknameSideEffect()
        data class KeyboardVisible(val visible: Boolean) : NicknameSideEffect()
        object NavigateNextView : NicknameSideEffect()
    }

    companion object {
        const val MAX_LENGTH = 12
    }
}
