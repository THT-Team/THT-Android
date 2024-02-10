package tht.feature.signin.signup.introduction

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.tht.tht.domain.signup.usecase.FetchSignupUserUseCase
import com.tht.tht.domain.signup.usecase.PatchSignupDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import tht.core.ui.base.BaseStateViewModel
import tht.core.ui.base.SideEffect
import tht.feature.signin.StringProvider
import javax.inject.Inject

@HiltViewModel
class IntroductionViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val fetchSignupUserUseCase: FetchSignupUserUseCase,
    private val patchSignupDataUseCase: PatchSignupDataUseCase,
    private val stringProvider: StringProvider
) : BaseStateViewModel<IntroductionUiState, IntroductionViewModel.IntroductionSideEffect>() {

    override val _uiStateFlow: MutableStateFlow<IntroductionUiState> =
        MutableStateFlow(IntroductionUiState.DEFAULT.copy(maxLength = MAX_LENGTH))

    private val phone: String = (savedStateHandle[KEY_PHONE] ?: "").also {
        Log.d("cwj", "phone => $it")
    }

    init {
        fetchSavedData()
    }

    fun fetchSavedData() {
        if (phone.isBlank()) {
            _uiStateFlow.update { it.copy(invalidatePhone = true) }
        }
        viewModelScope.launch {
            _uiStateFlow.update { it.copy(loading = true) }
            fetchSignupUserUseCase(phone)
                .onSuccess { user ->
                    _uiStateFlow.update {
                        it.copy(
                            introduction = user.introduce,
                            validation = IntroductionUiState.IntroductionValidation.VALIDATE
                        )
                    }
                }.onFailure {
                    _sideEffectFlow.emit(
                        IntroductionSideEffect.ShowToast(
                            stringProvider.getString(StringProvider.ResId.InvalidateSignupProcess) +
                                stringProvider.getString(StringProvider.ResId.CustomerService)
                        )
                    )
                }
            _uiStateFlow.update { it.copy(loading = false) }
        }
    }

    fun onTextInputEvent(text: String) {
        if (text.length > MAX_LENGTH) return
        _uiStateFlow.update {
            it.copy(
                introduction = text,
                validation = when (text.isBlank()) {
                    true -> IntroductionUiState.IntroductionValidation.IDLE
                    else -> IntroductionUiState.IntroductionValidation.VALIDATE
                }
            )
        }
    }

    fun onClear() {
        _uiStateFlow.update {
            it.copy(
                introduction = "",
                validation = IntroductionUiState.IntroductionValidation.IDLE
            )
        }
    }

    fun onBackgroundTouchEvent() {
        postSideEffect(IntroductionSideEffect.KeyboardVisible(false))
    }

    fun onClickNextEvent() {
        val introduction = _uiStateFlow.value.introduction
        if (introduction.isBlank()) {
            return
        }
        postSideEffect(IntroductionSideEffect.KeyboardVisible(false))
        viewModelScope.launch {
            _uiStateFlow.update { it.copy(loading = true) }
            patchSignupDataUseCase(phone) {
                it.copy(introduce = introduction)
            }.onSuccess {
                _sideEffectFlow.emit(IntroductionSideEffect.NavigateNextView)
            }.onFailure {
                _sideEffectFlow.emit(
                    IntroductionSideEffect.ShowToast(
                        stringProvider.getString(StringProvider.ResId.SendAuthFail)
                    )
                )
            }
            _uiStateFlow.update { it.copy(loading = false) }
        }
    }

    sealed class IntroductionSideEffect : SideEffect {
        data class ShowToast(val message: String) : IntroductionSideEffect()
        data class KeyboardVisible(val visible: Boolean) : IntroductionSideEffect()
        object NavigateNextView : IntroductionSideEffect()
    }

    companion object {
        const val MAX_LENGTH = 200
        private const val KEY_PHONE = "phone"
    }
}
