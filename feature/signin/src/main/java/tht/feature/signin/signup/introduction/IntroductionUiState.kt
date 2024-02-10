package tht.feature.signin.signup.introduction

import tht.core.ui.base.UiState

data class IntroductionUiState(
    val introduction: String,
    val validation: IntroductionValidation,
    val loading: Boolean,
    val invalidatePhone: Boolean = false,
    val maxLength: Int
) : UiState {
    enum class IntroductionValidation {
        IDLE,
        VALIDATE
    }

    companion object {
        val DEFAULT = IntroductionUiState(
            introduction = "",
            validation = IntroductionValidation.IDLE,
            loading = false,
            maxLength = 0
        )
    }
}
