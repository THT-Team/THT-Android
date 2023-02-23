package tht.feature.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class IntroViewModel : ViewModel() {

    private val _uiState = MutableSharedFlow<UiState>()
    val uiState = _uiState.asSharedFlow()

    fun signupEvent() {
        viewModelScope.launch {
            _uiState.emit(UiState.Signup)
        }
    }

    fun loginEvent() {
        viewModelScope.launch {
            _uiState.emit(UiState.Login)
        }
    }

    fun loginIssueEvent() {
        viewModelScope.launch {
            _uiState.emit(UiState.LoginIssue)
        }
    }

    sealed class UiState {
        object Signup : UiState()
        object Login : UiState()
        object LoginIssue : UiState()
    }
}
