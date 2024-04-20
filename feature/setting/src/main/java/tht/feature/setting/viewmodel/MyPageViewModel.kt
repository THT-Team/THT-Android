package tht.feature.setting.viewmodel

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.compose_ui.common.viewmodel.Container
import com.example.compose_ui.common.viewmodel.Store
import com.example.compose_ui.common.viewmodel.intent
import com.example.compose_ui.common.viewmodel.store
import com.tht.tht.domain.setting.usecase.FetchMyPageUserInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import tht.feature.setting.uimodel.MyPageUserInfoUiModel
import tht.feature.setting.uimodel.mapper.toUiModel
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val fetchMyPageUserInfoUseCase: FetchMyPageUserInfoUseCase
) : ViewModel(), Container<MyPageViewModel.MyPageUiState, MyPageViewModel.MyPageSideEffect> {

    sealed interface MyPageSideEffect {
        object ShowProfileImageModifyToast : MyPageSideEffect
        object NavigateSetting : MyPageSideEffect
        object NavigateModifyNickName : MyPageSideEffect
        object NavigateModifyIntroduce : MyPageSideEffect
        object NavigateModifyPreferredGender : MyPageSideEffect
        object NavigateModifyHeight : MyPageSideEffect
        object NavigateModifySmoke : MyPageSideEffect
        object NavigateModifyNickDrink : MyPageSideEffect
        object NavigateModifyNickReligion : MyPageSideEffect
        object NavigateModifyNickInterest : MyPageSideEffect
        object NavigateModifyNickIdealType : MyPageSideEffect
    }

    @Immutable
    data class MyPageUiState(
        val myPageUserInfo: MyPageUserInfoUiModel?,
        val loading: Boolean
    ) {
        val showSkeletonView: Boolean
            get() = myPageUserInfo == null && loading
        companion object {
            val DEFAULT: MyPageUiState get() = MyPageUiState(
                myPageUserInfo = null,
                loading = false
            )
        }
    }

    override val store: Store<MyPageUiState, MyPageSideEffect> = store(initialState = MyPageUiState.DEFAULT)

    init {
        viewModelScope.launch {
            intent { reduce { it.copy(loading = true) } }
            fetchMyPageUserInfoUseCase()
                .onSuccess { info ->
                    intent { reduce { it.copy(myPageUserInfo = info.toUiModel()) } }
                }.onFailure {
                    it.printStackTrace()
                }
            intent { reduce { it.copy(loading = true) } }
        }
    }

    fun onSettingClick() {
        intent { postSideEffect(MyPageSideEffect.NavigateSetting) }
    }

    fun onNicknameEditClick() {
        intent { postSideEffect(MyPageSideEffect.NavigateModifyNickName) }
    }

    fun onIntroduceClick() {
        intent { postSideEffect(MyPageSideEffect.NavigateModifyIntroduce) }
    }

    fun onPrimaryProfileEditClick(priority: Int) {
        intent { postSideEffect(MyPageSideEffect.ShowProfileImageModifyToast) }
    }

    fun onNonePrimaryProfileAddClick() {
        intent { postSideEffect(MyPageSideEffect.ShowProfileImageModifyToast) }
    }

    fun onNonePrimaryProfileRemoveClick() {
        intent { postSideEffect(MyPageSideEffect.ShowProfileImageModifyToast) }
    }

    fun onPreferredGenderClick() {
        intent { postSideEffect(MyPageSideEffect.NavigateModifyPreferredGender) }
    }

    fun onHeightClick() {
        intent { postSideEffect(MyPageSideEffect.NavigateModifyHeight) }
    }

    fun onDrinkClick() {
        intent { postSideEffect(MyPageSideEffect.NavigateModifyNickDrink) }
    }

    fun onReligionClick() {
        intent { postSideEffect(MyPageSideEffect.NavigateModifyNickReligion) }
    }

    fun onSmokeClick() {
        intent { postSideEffect(MyPageSideEffect.NavigateModifySmoke) }
    }

    fun onIdealTypeEditClick() {
        intent { postSideEffect(MyPageSideEffect.NavigateModifyNickIdealType) }
    }

    fun onInterestEditClick() {
        intent { postSideEffect(MyPageSideEffect.NavigateModifyNickInterest) }
    }
}
