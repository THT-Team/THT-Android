package tht.feature.signin.signup.introduction

import com.tht.tht.domain.signup.usecase.FetchSignupUserUseCase
import com.tht.tht.domain.signup.usecase.PatchSignupIntroduceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import tht.core.ui.base.BaseStateViewModel
import tht.core.ui.base.SideEffect
import tht.core.ui.base.UiState
import javax.inject.Inject

@HiltViewModel
class IntroductionViewModel @Inject constructor(
    private val fetchSignupUserUseCase: FetchSignupUserUseCase,
    private val patchSignupIntroduceUseCase: PatchSignupIntroduceUseCase
) : BaseStateViewModel<IntroductionViewModel.IntroductionUiState, IntroductionViewModel.IntroductionSideEffect>() {

    override val _uiStateFlow: MutableStateFlow<IntroductionUiState> =
        MutableStateFlow(IntroductionUiState.Empty)

    fun nextEvent() {
        postSideEffect(IntroductionSideEffect.NavigateNextView)
    }

    sealed class IntroductionUiState : UiState {
        object Empty : IntroductionUiState()
    }
    sealed class IntroductionSideEffect : SideEffect {
        object NavigateNextView : IntroductionSideEffect()
    }
}
