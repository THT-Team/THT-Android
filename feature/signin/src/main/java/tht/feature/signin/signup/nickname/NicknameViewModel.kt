package tht.feature.signin.signup.nickname

import com.tht.tht.domain.signup.usecase.FetchSignupUserUseCase
import com.tht.tht.domain.signup.usecase.PatchSignupNickNameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import tht.core.ui.base.BaseStateViewModel
import tht.core.ui.base.SideEffect
import tht.core.ui.base.UiState
import javax.inject.Inject

@HiltViewModel
class NicknameViewModel @Inject constructor(
    private val fetchSignupUserUseCase: FetchSignupUserUseCase,
    private val patchSignupNickNameUseCase: PatchSignupNickNameUseCase
) : BaseStateViewModel<NicknameViewModel.NicknameUiState, NicknameViewModel.NicknameSideEffect>() {

    override val _uiStateFlow: MutableStateFlow<NicknameUiState> =
        MutableStateFlow(NicknameUiState.Empty)

    sealed class NicknameUiState : UiState {
        object Empty : NicknameUiState()
    }
    sealed class NicknameSideEffect : SideEffect
}
