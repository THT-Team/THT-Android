package tht.feature.signin.signup.nickname

import com.tht.tht.domain.signup.usecase.PatchSignupBirthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import tht.core.ui.base.BaseStateViewModel
import tht.core.ui.base.SideEffect
import tht.core.ui.base.UiState
import javax.inject.Inject

@HiltViewModel
class NicknameViewModel @Inject constructor(
    private val patchSignupBirthUseCase: PatchSignupBirthUseCase
) : BaseStateViewModel<NicknameViewModel.NicknameUiState, NicknameViewModel.NicknameSideEffect>() {

    override val _uiStateFlow: MutableStateFlow<NicknameUiState> =
        MutableStateFlow(NicknameUiState.Empty)

    fun nextEvent(name: String?) {
        postSideEffect(NicknameSideEffect.NavigateNextView)
    }

    sealed class NicknameUiState : UiState {
        object Correct : NicknameUiState()
        object Empty : NicknameUiState()
        object Error : NicknameUiState()
    }
    sealed class NicknameSideEffect : SideEffect {
        object NavigateNextView : NicknameSideEffect()
    }
    companion object {
        const val EXTRA_PHONE_KEY = "extra_phone_key"

        private const val VERIFY_SIZE = 6
    }
}
