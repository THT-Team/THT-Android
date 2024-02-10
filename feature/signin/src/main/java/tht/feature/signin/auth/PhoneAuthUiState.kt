package tht.feature.signin.auth

import tht.core.ui.base.UiState

data class PhoneAuthUiState(
    val phone: String,
    val phoneValidation: PhoneValidation,
    val loading: Boolean
) : UiState {
    enum class PhoneValidation {
        IDLE,
        VALIDATE,
        INVALIDATE
    }

    companion object {
        val DEFAULT = PhoneAuthUiState(
            phone = "",
            phoneValidation = PhoneValidation.IDLE,
            loading = false
        )
    }
}
