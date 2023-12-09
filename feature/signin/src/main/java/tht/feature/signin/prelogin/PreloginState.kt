package tht.feature.signin.prelogin

import tht.core.ui.base.UiState

sealed class PreLoginState : UiState {

    object Uninitialized : PreLoginState()

    object Loading : PreLoginState()

    object Success : PreLoginState()

    object Error : PreLoginState()
}
