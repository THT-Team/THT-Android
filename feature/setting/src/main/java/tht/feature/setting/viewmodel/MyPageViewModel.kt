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
        object NavigateSetting : MyPageSideEffect
    }

    @Immutable
    data class MyPageUiState(
        val myPageUserInfo: MyPageUserInfoUiModel?,
        val loading: Boolean,
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

    }

    fun onIntroduceClick() {

    }

    fun onPrimaryProfileEditClick(priority: Int) {

    }

    fun onNonePrimaryProfileAddClick() {

    }

    fun onNonePrimaryProfileRemoveClick() {

    }

    fun onPreferredGenderClick() {

    }

    fun onHeightClick() {

    }

    fun onDrinkClick() {

    }

    fun onReligionClick() {

    }

    fun onSmokeClick() {

    }

    fun onIdealTypeEditClick() {

    }

    fun onInterestEditClick() {

    }


}
