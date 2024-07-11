package tht.feature.signin.signup.height

import tht.core.ui.base.UiState

data class HeightUiState(
    val height: Int?,
    val loading: Boolean,
    val heightSelectModalShow: Boolean
) : UiState {
    companion object {
        val DEFAULT: HeightUiState
            get() = HeightUiState(
                height = null,
                loading = false,
                heightSelectModalShow = false
            )
    }
}
