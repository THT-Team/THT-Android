package tht.feature.signin.signup.interest

import com.tht.tht.domain.signup.usecase.FetchSignupUserUseCase
import com.tht.tht.domain.signup.usecase.PatchSignupInterestUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import tht.core.ui.base.BaseStateViewModel
import tht.core.ui.base.SideEffect
import tht.core.ui.base.UiState
import javax.inject.Inject

@HiltViewModel
class InterestViewModel @Inject constructor(
    private val fetchSignupUserUseCase: FetchSignupUserUseCase,
    private val patchSignupInterestUseCase: PatchSignupInterestUseCase
) : BaseStateViewModel<InterestViewModel.InterestUiState, InterestViewModel.InterestSideEffect>() {

    override val _uiStateFlow: MutableStateFlow<InterestUiState> =
        MutableStateFlow(InterestUiState.Empty)

    sealed class InterestUiState : UiState {
        object Empty : InterestUiState()
    }
    sealed class InterestSideEffect : SideEffect {
    }
}
