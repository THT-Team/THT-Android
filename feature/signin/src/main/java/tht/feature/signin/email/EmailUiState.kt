package tht.feature.signin.email

import tht.core.ui.base.UiState

data class EmailUiState(
    val email: String,
    val emailValidation: EmailValidation,
    val loading: Boolean,
    val invalidatePhone: Boolean = false
) : UiState {
    enum class EmailValidation {
        IDLE,
        VALIDATE,
        INVALIDATE
    }

    companion object {
        val DEFAULT = EmailUiState(
            email = "",
            emailValidation = EmailValidation.IDLE,
            loading = false
        )
    }
}

