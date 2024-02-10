package tht.feature.signin.signup.nickname

import androidx.compose.runtime.Immutable
import tht.core.ui.base.UiState

@Immutable
data class NicknameUiState(
    val nickname: String,
    val loading: Boolean,
    val validation: NicknameValidation,
    val maxLength: Int
) : UiState {
    sealed interface NicknameValidation {
        object Idle : NicknameValidation
        object Validate : NicknameValidation
        data class Invalid(val errorMessage: String) : NicknameValidation
    }

    companion object {
        val DEFAULT: NicknameUiState
            get() = NicknameUiState(
                nickname = "",
                loading = false,
                validation = NicknameValidation.Idle,
                maxLength = 0
            )
    }
}
