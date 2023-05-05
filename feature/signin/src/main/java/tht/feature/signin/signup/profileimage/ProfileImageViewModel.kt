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

    private val _imageArray = MutableStateFlow(Array(IMAGE_MAX_SIZE) { ImageUri(null, null) })
    val imageArray = _imageArray.asStateFlow()

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
                            _imageArray.value = _imageArray.value.also { arr ->
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
        when (_imageArray.value[idx].uri.isNullOrBlank() && imageArray.value[idx].url.isNullOrBlank()) {
            true -> postSideEffect(ProfileImageSideEffect.RequestImageFromGallery(idx))
            else -> postSideEffect(ProfileImageSideEffect.ShowModifyDialog(idx))
        }
    }

    fun imageModifyEvent(idx: Int) {
        postSideEffect(ProfileImageSideEffect.RequestImageFromGallery(idx))
    }

    fun imageRemoveEvent(idx: Int) {
        var newImageInfo = _imageArray.value[idx]
        if (_imageArray.value[idx].uri != null) {
            newImageInfo = newImageInfo.copy(uri = null)
        }
        if (_imageArray.value[idx].url != null) {
            newImageInfo = newImageInfo.copy(url = null)
            //TODO: Remove from bucket
        }
        _imageArray.value = _imageArray.value.let {
            it[idx] = newImageInfo
            it.copyOf()
        }
        checkRequireImageSelect()
    }

    fun imageSelectEvent(uri: String, idx: Int) {
        _imageArray.value = _imageArray.value.let {
            it[idx] = ImageUri(uri, it[idx].url)
            it.copyOf()
        }
        checkRequireImageSelect()
    }

    // 현재 선택된 이미지 개수에 따라 UiState 를 변경
    private fun checkRequireImageSelect() {
        val currentSelectImageCount = _imageArray.value.count { !it.uri.isNullOrBlank() || !it.url.isNullOrBlank() }
        _uiStateFlow.value = when (currentSelectImageCount >= IMAGE_REQUIRE_SIZE) {
            true -> ProfileImageUiState.Accept
            else -> ProfileImageUiState.Empty
        }
    }

    // 이미지 업로드 Process. 완료되면 Patch Process 실행
    // uri가 존재 한다면 upload 후 리턴된 url를 url array에 적용
    // 이미 업로드된 url은 그대로 유지
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
                        emitMessage(
                            stringProvider.getString(
                                StringProvider.ResId.ProfileImagePartialUploadFail,
                                uriList.size - uploadUrlList.size
                            )
                        )
                    }
                    uploadUrlList.forEach { urlPair ->
                        if (urlPair.second !in urlArray.indices) return@forEach
                        urlArray[urlPair.second] = urlPair.first
                    }
                    patchProfileImage(phone, urlArray.filter { !it.isNullOrBlank() }.map { it!! })
                }.onFailure {
                    it.printStackTrace()
                    Log.e("TAG", "image upload debug fail at viewModel => $it")
                    emitMessage(
                        stringProvider.getString(StringProvider.ResId.ProfileImageUploadFail) +
                            it.toString()
                    )
                }.also {
                    _dataLoading.value = false
                }
        }
    }

    // Patch Process
    // 이미지 Url Array를 Local에 있는 SignupUserModel에 적용
    private suspend fun patchProfileImage(phone: String, profileImageUrls: List<String>) {
        _dataLoading.value = true
        patchSignupProfileImagesUseCase(phone, profileImageUrls)
            .onSuccess {
                when (it) {
                    true -> postSideEffect(ProfileImageSideEffect.NavigateNextView)
                    else -> emitMessage(
                        stringProvider.getString(StringProvider.ResId.ProfileImagePatchFail)
                    )
                }
            }.onFailure {
                it.printStackTrace()
                emitMessage(
                    stringProvider.getString(StringProvider.ResId.ProfileImagePatchFail) +
                        it.toString()
                )
            }.also {
                _dataLoading.value = false
            }
    }

    fun nextEvent(phone: String) {
        doPatchInputTask(phone)
    }

    private fun doPatchInputTask(phone: String) {
        if (_dataLoading.value) {
            emitMessage(
                stringProvider.getString(StringProvider.ResId.Loading)
            )
        }
        _imageArray.value.count { !it.uri.isNullOrBlank() || !it.url.isNullOrBlank() }.let {
            if (it < IMAGE_REQUIRE_SIZE) return
        }
        uploadProfileImageUris(
            phone,
            _imageArray.value.map {
                it.uri
            },
            _imageArray.value.map {
                it.url
            }.toTypedArray()
        )
    }

    private fun emitMessage(message: String) {
        postSideEffect(ProfileImageSideEffect.ShowToast(message))
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
    data class ShowModifyDialog(val idx: Int) : ProfileImageSideEffect()
    object NavigateNextView : ProfileImageSideEffect()
}
