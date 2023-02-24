package tht.core.ui.base

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

abstract class BaseStateViewModel<S : UiState, SE : SideEffect> : BaseViewModel() {

    protected abstract val _uiStateFlow: MutableStateFlow<S>
    val uiStateFlow: StateFlow<S>
        get() = _uiStateFlow

    protected val _sideEffectFlow: MutableSharedFlow<SE> = MutableSharedFlow()
    val sideEffectFlow: SharedFlow<SE>
        get() = _sideEffectFlow

    protected inline fun <reified S : UiState> withUiState(block: (S) -> Unit): Boolean {
        if (uiStateFlow.value is S) {
            block(uiStateFlow.value as S)
            return true
        }
        return false
    }

    protected fun setUiState(state: S) {
        _uiStateFlow.value = state
    }

    protected fun postSideEffect(sideEffect: SE) {
        viewModelScope.launch {
            _sideEffectFlow.emit(sideEffect)
        }
    }
}
