package tht.feature.signin.signup.picture

import com.tht.tht.domain.signup.usecase.FetchSignupUserUseCase
import com.tht.tht.domain.signup.usecase.PatchSignupProfileImagesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import tht.core.ui.base.BaseStateViewModel
import tht.core.ui.base.SideEffect
import tht.core.ui.base.UiState
import javax.inject.Inject

@HiltViewModel
class PictureViewModel @Inject constructor(
    private val fetchSignupUserUseCase: FetchSignupUserUseCase,
    private val patchSignupProfileImagesUseCase: PatchSignupProfileImagesUseCase
) : BaseStateViewModel<PictureViewModel.PictureUiState, PictureViewModel.PictureSideEffect>() {

    override val _uiStateFlow: MutableStateFlow<PictureUiState> =
        MutableStateFlow(PictureUiState.Empty)

    sealed class PictureUiState : UiState {
        object Empty : PictureUiState()
    }
    sealed class PictureSideEffect : SideEffect {
    }
}
