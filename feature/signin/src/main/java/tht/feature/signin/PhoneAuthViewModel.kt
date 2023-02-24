package tht.feature.signin

import androidx.lifecycle.viewModelScope
import com.tht.tht.domain.signup.usecase.RequestAuthenticationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import tht.core.ui.base.BaseStateViewModel
import tht.core.ui.base.SideEffect
import tht.core.ui.base.UiState
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class PhoneAuthViewModel @Inject constructor(
    private val requestAuthenticationUseCase: RequestAuthenticationUseCase
) : BaseStateViewModel<PhoneAuthViewModel.PhoneAuthUiState, PhoneAuthViewModel.PhoneAuthSideEffect>() {

    override val _uiStateFlow: MutableStateFlow<PhoneAuthUiState> =
        MutableStateFlow(PhoneAuthUiState.InputPhoneNumEmpty)

    private val _dataLoading = MutableStateFlow(false)
    val dataLoading = _dataLoading.asStateFlow()

    fun textInputEvent(text: String?) {
        if (text.isNullOrBlank()) {
            setUiState(PhoneAuthUiState.InputPhoneNumEmpty)
            return
        }
        when (checkPhoneValidation(text)) {
            true -> setUiState(PhoneAuthUiState.InputPhoneNumCorrect)
            else -> setUiState(PhoneAuthUiState.InputPhoneNumError)
        }
    }
    private fun checkPhoneValidation(phone: String): Boolean {
        val phoneNumPattern = "^01([0|16789])(\\d{4})(\\d{4})$"
        return Pattern.matches(phoneNumPattern, phone)
    }

    fun backgroundTouchEvent() {
        postSideEffect(PhoneAuthSideEffect.KeyboardVisible(false))
    }

    fun backEvent() {
        postSideEffect(PhoneAuthSideEffect.Back)
    }

    fun authEvent(phone: String?) {
        viewModelScope.launch {
            if (phone.isNullOrBlank() || !checkPhoneValidation(phone)) {
                setUiState(PhoneAuthUiState.InputPhoneNumError)
                return@launch
            }
            _dataLoading.value = true
            requestAuthenticationUseCase(phone)
                .onSuccess {
                    when (it) {
                        true -> _sideEffectFlow.emit(PhoneAuthSideEffect.NavigateVerifyView(phone))
                        else -> _sideEffectFlow.emit(PhoneAuthSideEffect.ShowToast("fail send auth"))
                    }
                }.onFailure {
                    _sideEffectFlow.emit(PhoneAuthSideEffect.ShowToast(it.message ?: "$it"))
                }.also {
                    _sideEffectFlow.emit(PhoneAuthSideEffect.KeyboardVisible(false))
                    _dataLoading.value = false
                }
        }
    }

    sealed class PhoneAuthUiState : UiState {
        object InputPhoneNumCorrect : PhoneAuthUiState()
        object InputPhoneNumEmpty : PhoneAuthUiState()
        object InputPhoneNumError : PhoneAuthUiState()
    }

    sealed class PhoneAuthSideEffect : SideEffect {
        data class ShowToast(val message: String) : PhoneAuthSideEffect()
        data class NavigateVerifyView(val phone: String) : PhoneAuthSideEffect()
        data class KeyboardVisible(val visible: Boolean) : PhoneAuthSideEffect()
        object Back : PhoneAuthSideEffect()
    }
}
