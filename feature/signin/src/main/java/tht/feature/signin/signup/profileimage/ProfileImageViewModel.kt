package tht.feature.signin.signup.profileimage

import com.tht.tht.domain.signup.usecase.FetchSignupUserUseCase
import com.tht.tht.domain.signup.usecase.PatchSignupProfileImagesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import tht.core.ui.base.BaseStateViewModel
import tht.core.ui.base.SideEffect
import tht.core.ui.base.UiState
import javax.inject.Inject

@HiltViewModel
class ProfileImageViewModel @Inject constructor(
    private val fetchSignupUserUseCase: FetchSignupUserUseCase,
    private val patchSignupProfileImagesUseCase: PatchSignupProfileImagesUseCase,
) : BaseStateViewModel<ProfileImageUiState, ProfileImageSideEffect>() {

    override val _uiStateFlow: MutableStateFlow<ProfileImageUiState> =
        MutableStateFlow(ProfileImageUiState.Empty)

    private val _imageUrlList = MutableStateFlow(Array(IMAGE_MAX_SIZE){ "" })
    val imageUrlList = _imageUrlList.asStateFlow()

    fun imageClickEvent(idx: Int) {
        postSideEffect(ProfileImageSideEffect.RequestImageFromGallery(idx))
    }

    fun imageSelectEvent(url: String, idx: Int) {
        val urlList = _imageUrlList.value
        urlList[idx] = url
        _imageUrlList.value = urlList.copyOf()

        var inputCount = 0
        for (u in urlList) {
            if (u.isNotBlank()) inputCount++
        }
        _uiStateFlow.value = when (inputCount >= IMAGE_REQUIRE_SIZE) {
            true -> ProfileImageUiState.Accept
            else -> ProfileImageUiState.Empty
        }
    }

    fun nextEvent() {
        postSideEffect(ProfileImageSideEffect.NavigateNextView)
    }

    companion object {
        private const val IMAGE_MAX_SIZE = 3
        private const val IMAGE_REQUIRE_SIZE = 2
    }

}

sealed class ProfileImageUiState : UiState {
    object Empty : ProfileImageUiState()
    object Accept : ProfileImageUiState()
}
sealed class ProfileImageSideEffect : SideEffect {
    data class RequestImageFromGallery(
        val idx: Int
    ) : ProfileImageSideEffect()

    object NavigateNextView : ProfileImageSideEffect()
}
