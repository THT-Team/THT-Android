package tht.feature.signin.signup.preferredgender

import com.tht.tht.domain.signup.usecase.FetchSignupUserUseCase
import com.tht.tht.domain.signup.usecase.PatchSignupGenderUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import tht.core.ui.base.BaseStateViewModel
import tht.core.ui.base.SideEffect
import tht.core.ui.base.UiState
import javax.inject.Inject

@HiltViewModel
class PreferredGenderViewModel @Inject constructor(
    private val fetchSignupUserUseCase: FetchSignupUserUseCase,
    private val patchSignupGenderUseCase: PatchSignupGenderUseCase
) : BaseStateViewModel<PreferredGenderViewModel.GenderUiState, PreferredGenderViewModel.GenderSideEffect>() {

    override val _uiStateFlow: MutableStateFlow<GenderUiState> =
        MutableStateFlow(GenderUiState.Empty)

    sealed class GenderUiState : UiState {
        object Empty : GenderUiState()
    }

    sealed class GenderSideEffect : SideEffect
}
