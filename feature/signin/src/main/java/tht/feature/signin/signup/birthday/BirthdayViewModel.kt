package tht.feature.signin.signup.birthday

import com.tht.tht.domain.signup.usecase.FetchSignupUserUseCase
import com.tht.tht.domain.signup.usecase.PatchSignupBirthdayUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import tht.core.ui.base.BaseStateViewModel
import tht.core.ui.base.SideEffect
import tht.core.ui.base.UiState
import javax.inject.Inject

@HiltViewModel
class BirthdayViewModel @Inject constructor(
    private val fetchSignupUserUseCase: FetchSignupUserUseCase,
    private val patchSignupBirthdayUseCase: PatchSignupBirthdayUseCase
) : BaseStateViewModel<BirthdayViewModel.BirthdayUiState, BirthdayViewModel.BirthdaySideEffect>() {

    override val _uiStateFlow: MutableStateFlow<BirthdayUiState> =
        MutableStateFlow(BirthdayUiState.Default)

    fun datePickerEvent() {
        postSideEffect(BirthdaySideEffect.ShowDatePicker)
    }

    sealed class BirthdayUiState : UiState {
        object Default : BirthdayUiState()
    }
    sealed class BirthdaySideEffect : SideEffect {
        object ShowDatePicker : BirthdaySideEffect()
    }
}
