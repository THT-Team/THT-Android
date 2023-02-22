package tht.feature.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tht.tht.domain.signup.usecase.RequestAuthenticationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class PhoneAuthViewModel @Inject constructor(
    private val requestAuthenticationUseCase: RequestAuthenticationUseCase
) : ViewModel() {

    private val _dataLoading = MutableStateFlow(false)
    val dataLoading = _dataLoading.asStateFlow()

    private val _uiState = MutableSharedFlow<UiState>()
    val uiState = _uiState.asSharedFlow()

    fun textInputEvent(text: String?) = viewModelScope.launch {
        if(text.isNullOrBlank()) {
            _uiState.emit(UiState.InputPhoneNumEmpty)
            return@launch
        }
        when (checkPhoneValidation(text)) {
            true -> _uiState.emit(UiState.InputPhoneNumCorrect)
            else -> _uiState.emit(UiState.InputPhoneNumError)
        }
    }

    private fun checkPhoneValidation(phone: String): Boolean {
//        val phoneNumPattern = "^01([0|1|6|7|8|9])-?([0-9]{4})-?([0-9]{4})$"
        val phoneNumPattern = "^01([0|16789])(\\d{4})(\\d{4})$"
        return Pattern.matches(phoneNumPattern, phone)
    }

    fun backgroundTouchEvent() = viewModelScope.launch {
        _uiState.emit(UiState.KeyboardVisible(false))
    }

    fun backEvent() = viewModelScope.launch {
        _uiState.emit(UiState.Back)
    }

    fun authEvent(phone: String?) = viewModelScope.launch {
        if(phone.isNullOrBlank() || !checkPhoneValidation(phone)) {
            _uiState.emit(UiState.InputPhoneNumError)
            return@launch
        }
        _dataLoading.value = true
        requestAuthenticationUseCase(phone)
            .onSuccess {
                _uiState.emit(UiState.SuccessRequestAuth)
            }.onFailure {
                _uiState.emit(UiState.ShowToast(it.message ?: "$it"))
            }.also {
                _uiState.emit(UiState.KeyboardVisible(false))
                _dataLoading.value = false
            }
    }

    sealed class UiState {
        object SuccessRequestAuth : UiState()
        data class ShowToast(val message: String) : UiState()
        data class KeyboardVisible(val visible: Boolean) : UiState()
        object InputPhoneNumCorrect : UiState()
        object InputPhoneNumEmpty : UiState()
        object InputPhoneNumError : UiState()
        object Back : UiState()
    }
}
