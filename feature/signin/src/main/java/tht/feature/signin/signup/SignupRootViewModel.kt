package tht.feature.signin.signup

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import tht.core.ui.base.BaseStateViewModel
import tht.core.ui.base.SideEffect
import tht.core.ui.base.UiState
import javax.inject.Inject

@HiltViewModel
class SignupRootViewModel @Inject constructor() :
    BaseStateViewModel<SignupRootViewModel.SignupRootUiState, SignupRootViewModel.SignupRootSideEffect>() {

    override val _uiStateFlow: MutableStateFlow<SignupRootUiState> =
        MutableStateFlow(SignupRootUiState.Progress(Step.EMPTY))

    fun progressEvent(step: Step) {
        setUiState(SignupRootUiState.Progress(step))
    }

    fun backEvent() {
        postSideEffect(SignupRootSideEffect.Back)
    }

    fun nextEvent(step: Step) {
        postSideEffect(SignupRootSideEffect.NavigateNextView(step))
    }

    sealed class SignupRootUiState : UiState {
        data class Progress(val step: Step) : SignupRootUiState()
    }

    sealed class SignupRootSideEffect : SideEffect {
        object Back : SignupRootSideEffect()
        data class NavigateNextView(val step: Step) : SignupRootSideEffect()
    }

    enum class Step {
        EMPTY,
        NICKNAME,
        BIRTHDAY,
        GENDER,
        PROFILE_IMAGE,
        INTEREST,
        IDEAL_TYPE,
        INTRODUCTION,
        LOCATION
    }
}
