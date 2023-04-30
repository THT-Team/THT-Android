package tht.feature.signin.signup.profileimage

import com.tht.tht.domain.signup.usecase.FetchSignupUserUseCase
import com.tht.tht.domain.signup.usecase.PatchSignupProfileImagesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import tht.core.ui.base.BaseStateViewModel
import tht.core.ui.base.SideEffect
import tht.core.ui.base.UiState
import javax.inject.Inject

@HiltViewModel
class ProfileImageViewModel @Inject constructor(
    private val fetchSignupUserUseCase: FetchSignupUserUseCase,
    private val patchSignupProfileImagesUseCase: PatchSignupProfileImagesUseCase
) : BaseStateViewModel<ProfileImageViewModel.ProfileImageUiState, ProfileImageViewModel.ProfileImageSideEffect>() {

    override val _uiStateFlow: MutableStateFlow<ProfileImageUiState> =
        MutableStateFlow(ProfileImageUiState.Empty)

    sealed class ProfileImageUiState : UiState {
        object Empty : ProfileImageUiState()
    }
    sealed class ProfileImageSideEffect : SideEffect
}
