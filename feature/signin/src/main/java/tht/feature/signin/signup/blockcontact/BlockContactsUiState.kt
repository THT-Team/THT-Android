package tht.feature.signin.signup.blockcontact

import tht.core.ui.base.UiState

data class BlockContactsUiState(
    val loading: Boolean
) : UiState {
    companion object {
        val default: BlockContactsUiState get() = BlockContactsUiState(
            loading = false
        )
    }
}
