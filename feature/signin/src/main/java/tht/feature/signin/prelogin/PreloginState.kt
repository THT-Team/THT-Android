package tht.feature.signin.prelogin

import tht.core.ui.base.UiState

sealed class PreloginState : UiState {

    object Uninitialized : PreloginState()

    object Loading : PreloginState()

    object Success : PreloginState()

    object Error : PreloginState()
}
