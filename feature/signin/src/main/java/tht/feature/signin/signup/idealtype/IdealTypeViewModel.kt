package tht.feature.signin.signup.idealtype

import com.tht.tht.domain.signup.usecase.FetchSignupUserUseCase
import com.tht.tht.domain.signup.usecase.PatchSignupIdealTypeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import tht.core.ui.base.BaseStateViewModel
import tht.core.ui.base.SideEffect
import tht.core.ui.base.UiState
import javax.inject.Inject

@HiltViewModel
class IdealTypeViewModel @Inject constructor(
    private val fetchSignupUserUseCase: FetchSignupUserUseCase,
    private val patchSignupIdealTypeUseCase: PatchSignupIdealTypeUseCase
) : BaseStateViewModel<IdealTypeViewModel.IdealTypeUiState, IdealTypeViewModel.IdealTypeSideEffect>() {

    override val _uiStateFlow: MutableStateFlow<IdealTypeUiState> =
        MutableStateFlow(IdealTypeUiState.Empty)

    sealed class IdealTypeUiState : UiState {
        object Empty : IdealTypeUiState()
    }
    sealed class IdealTypeSideEffect : SideEffect {
    }
}
