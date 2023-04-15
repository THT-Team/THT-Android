package tht.feature.signin.signup.profileimage

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.tht.tht.domain.image.UploadImageUseCase
import com.tht.tht.domain.signup.usecase.FetchSignupUserUseCase
import com.tht.tht.domain.signup.usecase.PatchSignupProfileImagesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import tht.core.ui.base.BaseStateViewModel
import tht.core.ui.base.SideEffect
import tht.core.ui.base.UiState
import tht.feature.signin.StringProvider
import javax.inject.Inject

/**
 * https://full-growth-4d2.notion.site/Signup-Profile-Image-977abc7b8bdd4f0d9767e442ecd6cd1d
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

    private val _imageList = MutableStateFlow(Array(IMAGE_MAX_SIZE) { ImageUri(null, null) })
    val imageList = _imageList.asStateFlow()

    private val _dataLoading = MutableStateFlow(false)
    val dataLoading = _dataLoading.asStateFlow()

    fun fetchSavedData(phone: String) {
        if (phone.isBlank()) {
            _uiStateFlow.value = ProfileImageUiState.InvalidPhoneNumber(
                stringProvider.getString(StringProvider.ResId.InvalidatePhone)
            )
        }
        // Local에 저장된 이미지를 불러와 size가 IMAGE_MAX_SIZE인 _imageList에 저장
        viewModelScope.launch(Dispatchers.Default) {
            _dataLoading.value = true
            fetchSignupUserUseCase(phone)
                .onSuccess {
                    it.profileImgUrl
                        .map { u ->
                            ImageUri(null, u)
                        }.take(3)
                        .forEachIndexed { idx, imageUri ->
                            _imageList.value = _imageList.value.also { arr ->
                                arr[idx] = imageUri
                            }.copyOf()
                        }
                    checkRequireImageSelect()
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
        _imageList.value = _imageList.value.let {
            it[idx] = ImageUri(uri, it[idx].url)
            it.copyOf()
        }
        checkRequireImageSelect()
    }

    private fun checkRequireImageSelect() {
        val currentSelectImageCount = _imageList.value.count { !it.uri.isNullOrBlank() || !it.url.isNullOrBlank() }
        _uiStateFlow.value = when (currentSelectImageCount >= IMAGE_REQUIRE_SIZE) {
            true -> ProfileImageUiState.Accept
            else -> ProfileImageUiState.Empty
        }
    }

    private fun uploadProfileImageUris(phone: String, uriList: List<String?>, urlArray: Array<String?>) {
        viewModelScope.launch(Dispatchers.Default) {
            _dataLoading.value = true
            val uploadTryImages = uriList.mapIndexed { idx, uri ->
                uri to idx
            }.filter {
                !it.first.isNullOrBlank()
            }.map {
                it.first!! to it.second
            }
            uploadImageUseCase(uploadTryImages)
                .onSuccess { uploadUrlList ->
                    if (uploadTryImages.size != uploadUrlList.size) {
                        postSideEffect(
                            ProfileImageSideEffect.ShowToast(
                                "${uriList.size - uploadUrlList.size}장 업로드 실패"
                            )
                        )
                    }
                    uploadUrlList.forEach { urlPair ->
                        if (urlPair.second !in uriList.indices) return@forEach
                        urlArray[urlPair.second] = urlPair.first
                    }
                    patchProfileImage(phone, urlArray.filter { !it.isNullOrBlank() }.map { it!! })
                }.onFailure {
                    it.printStackTrace()
                    Log.e("TAG", "image upload debug fail at viewModel => $it")
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
        if (_dataLoading.value) return
        _imageList.value.count { !it.uri.isNullOrBlank() || !it.url.isNullOrBlank() }.let {
            if (it < IMAGE_REQUIRE_SIZE) return
        }
        uploadProfileImageUris(
            phone,
            _imageList.value.map {
                it.uri
            },
            _imageList.value.map {
                it.url
            }.toTypedArray()
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
    data class ShowToast(val message: String) : ProfileImageSideEffect()
    data class RequestImageFromGallery(
        val idx: Int
    ) : ProfileImageSideEffect()
    object NavigateNextView : ProfileImageSideEffect()
}
