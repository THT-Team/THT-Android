package tht.feature.signin.signup.birthday

import com.tht.tht.domain.signup.usecase.FetchSignupUserUseCase
import com.tht.tht.domain.signup.usecase.PatchSignupBirthdayUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import tht.core.ui.base.BaseStateViewModel
import tht.core.ui.base.SideEffect
import tht.core.ui.base.UiState
import javax.inject.Inject
import kotlin.text.StringBuilder

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

    fun setBirthdayEvent(birthday: String) {
        setUiState(BirthdayUiState.ValidBirthday(
            if(birthday.length < 12) addSpaceAfterPeriod(birthday) else birthday
        ))
    }

    private fun addSpaceAfterPeriod(str: String): String =
        StringBuilder(str).insert(5, ' ').insert(9, ' ').toString()

    private fun removeSpaceAfterPeriod(str: String): String =
        StringBuilder(str).deleteCharAt(0).deleteCharAt(8).toString()


    sealed class BirthdayUiState : UiState {
        object Default : BirthdayUiState()
        object InvalidBirthday : BirthdayUiState()
        data class ValidBirthday(val birthday: String) : BirthdayUiState()
    }

    sealed class BirthdaySideEffect : SideEffect {
        object ShowDatePicker : BirthdaySideEffect()
    }
}
