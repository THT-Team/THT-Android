package tht.feature.signin.intro

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import tht.core.ui.base.SideEffect

class IntroViewModel : ViewModel() {

    private val _sideEffectFlow = MutableSharedFlow<IntroSideEffect>()
    val sideEffectFlow = _sideEffectFlow.asSharedFlow()

    fun signupEvent() {
        viewModelScope.launch {
            _sideEffectFlow.emit(IntroSideEffect.NavigateSignupView)
        }
    }

    fun loginEvent() {
        viewModelScope.launch {
            _sideEffectFlow.emit(IntroSideEffect.NavigateLoginView)
        }
    }

    fun loginIssueEvent() {
        viewModelScope.launch {
            _sideEffectFlow.emit(IntroSideEffect.NavigateLoginIssueView)
        }
    }

    sealed class IntroSideEffect : SideEffect {
        object NavigateSignupView : IntroSideEffect()
        object NavigateLoginView : IntroSideEffect()
        object NavigateLoginIssueView : IntroSideEffect()
    }
}
