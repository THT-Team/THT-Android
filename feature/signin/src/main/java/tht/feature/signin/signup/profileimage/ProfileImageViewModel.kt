package tht.feature.signin.signup.profileimage

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.tht.tht.domain.image.UploadImageUseCase
import com.tht.tht.domain.signup.usecase.FetchSignupUserUseCase
import com.tht.tht.domain.signup.usecase.PatchSignupProfileImagesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import tht.core.ui.base.BaseStateViewModel
import tht.core.ui.base.SideEffect
import tht.core.ui.base.UiState
import tht.feature.signin.StringProvider
import javax.inject.Inject

/**
 * TODO: Local에서 불러온 Image와 새로 Gallery에서 선택한 이미지가 둘 다 있을 경우 처리
 * TODO: URL, URI 가 모두 있을 경우에 대한 처리
 * TODO: Image Domain, Data Test Code
 */
@HiltViewModel
class ProfileImageViewModel @Inject constructor(
    private val fetchSignupUserUseCase: FetchSignupUserUseCase,
    private val patchSignupProfileImagesUseCase: PatchSignupProfileImagesUseCase,
    private val uploadImageUseCase: UploadImageUseCase,
    private val stringProvider: StringProvider
) : BaseStateViewModel<ProfileImageUiState, ProfileImageSideEffect>() {

    override val _uiStateFlow: MutableStateFlow<ProfileImageUiState> =
        MutableStateFlow(ProfileImageUiState.Empty)

    private val _imageUrlList = MutableStateFlow<List<String>>(emptyList())
    val imageUrlList = _imageUrlList.asStateFlow()

    private val _imageUriList = MutableStateFlow(Array(IMAGE_MAX_SIZE){ "" })
    val imageUriList = _imageUriList.asStateFlow()

    private val _dataLoading = MutableStateFlow(false)
    val dataLoading = _dataLoading.asStateFlow()

    fun fetchSavedData(phone: String) {
        if (phone.isBlank()) {
            _uiStateFlow.value = ProfileImageUiState.InvalidPhoneNumber(
                stringProvider.getString(StringProvider.ResId.InvalidatePhone)
            )
        }
        viewModelScope.launch {
            _dataLoading.value = true
            fetchSignupUserUseCase(phone)
                .onSuccess {
                    _imageUrlList.value = it.profileImgUrl
                }.onFailure {
                    emitMessage(stringProvider.getString(StringProvider.ResId.InvalidateSignupProcess))
                }.also {
                    _dataLoading.value = false
                }
        }
    }

    fun imageClickEvent(idx: Int) {
        postSideEffect(ProfileImageSideEffect.RequestImageFromGallery(idx))
    }

    fun imageSelectEvent(uri: String, idx: Int) {
        _imageUriList.value = _imageUriList.value.let {
            it[idx] = uri
            it.copyOf()
        }
        _uiStateFlow.value = when (_imageUriList.value.count { it.isNotBlank() } >= IMAGE_REQUIRE_SIZE) {
            true -> ProfileImageUiState.Accept
            else -> ProfileImageUiState.Empty
        }
    }

    private fun uploadProfileImageUris(phone: String, uriList: List<String>) {
        viewModelScope.launch {
            _dataLoading.value = true
            uploadImageUseCase(uriList)
                .onSuccess {
                    Log.d("TAG", "upload image => $it")
                    if (uriList.size != it.size) {
                        postSideEffect(ProfileImageSideEffect.ShowToast(
                            "${uriList.size - it.size}장 업로드 실패")
                        )
                    }
                    patchProfileImage(phone, it)
                }.onFailure {
                    it.printStackTrace()
                    Log.d("TAG", "image upload debug fail at viewModel => $it")
                    postSideEffect(ProfileImageSideEffect.ShowToast(it.toString()))
                }.also {
                    _dataLoading.value = false
                }
        }
    }

    private suspend fun patchProfileImage(phone: String, profileImageUrls: List<String>) {
        _dataLoading.value = true
        patchSignupProfileImagesUseCase(phone, profileImageUrls)
            .onSuccess {
                when (it) {
                    true -> postSideEffect(ProfileImageSideEffect.NavigateNextView)
                    else -> emitMessage("fail patch")
                }
            }.onFailure {
                it.printStackTrace()
                postSideEffect(ProfileImageSideEffect.ShowToast(it.toString()))
            }.also {
                _dataLoading.value = false
            }
    }

    fun nextEvent(phone: String) {
        doPatchInputTask(phone)
    }

    private fun doPatchInputTask(phone: String) {
        if (_dataLoading.value) {
            return
        }
        if (_imageUriList.value.count() < IMAGE_REQUIRE_SIZE) {
            return
        }
        uploadProfileImageUris(
            phone, _imageUriList.value.filter { it.isNotBlank() }
        )
    }

    private suspend fun emitMessage(message: String) {
        _sideEffectFlow.emit(ProfileImageSideEffect.ShowToast(message))
    }

    companion object {
        private const val IMAGE_MAX_SIZE = 3
        private const val IMAGE_REQUIRE_SIZE = 2
    }
}

sealed class ProfileImageUiState : UiState {
    data class InvalidPhoneNumber(val message: String) : ProfileImageUiState()
    object Empty : ProfileImageUiState()
    object Accept : ProfileImageUiState()
}
sealed class ProfileImageSideEffect : SideEffect {
    data class ShowToast(val message: String) :ProfileImageSideEffect()
    data class RequestImageFromGallery(
        val idx: Int
    ) : ProfileImageSideEffect()
    object NavigateNextView : ProfileImageSideEffect()
}
