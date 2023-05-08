package tht.feature.signin.signup.birthday

import androidx.lifecycle.viewModelScope
import com.tht.tht.domain.signup.usecase.FetchSignupUserUseCase
import com.tht.tht.domain.signup.usecase.PatchSignupBirthdayUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import tht.core.ui.base.BaseStateViewModel
import tht.core.ui.base.SideEffect
import tht.core.ui.base.UiState
import tht.feature.signin.StringProvider
import javax.inject.Inject

@HiltViewModel
class BirthdayViewModel @Inject constructor(
    private val fetchSignupUserUseCase: FetchSignupUserUseCase,
    private val patchSignupBirthdayUseCase: PatchSignupBirthdayUseCase,
    private val stringProvider: StringProvider
) : BaseStateViewModel<BirthdayViewModel.BirthdayUiState, BirthdayViewModel.BirthdaySideEffect>() {

    override val _uiStateFlow: MutableStateFlow<BirthdayUiState> =
        MutableStateFlow(BirthdayUiState.Default)

    private val _dataLoading = MutableStateFlow(false)
    val dataLoading = _dataLoading.asStateFlow()

    fun fetchSavedData(phone: String) {
        if (phone.isBlank()) {
            _uiStateFlow.value = BirthdayUiState.InvalidPhoneNumber(
                stringProvider.getString(StringProvider.ResId.InvalidatePhone)
            )
            return
        }

        viewModelScope.launch {
            _dataLoading.value = true
            fetchSignupUserUseCase(phone)
                .onSuccess {
                    setUiState(
                        BirthdayUiState.ValidBirthday(
                            if (it.birthday.length < 12) addSpaceAfterPeriod(it.birthday) else it.birthday
                        )
                    )
                }.onFailure {
                    _sideEffectFlow.emit(
                        BirthdaySideEffect.ShowToast(
                            stringProvider.getString(StringProvider.ResId.InvalidateSignupProcess) +
                                stringProvider.getString(StringProvider.ResId.CustomerService)
                        )
                    )
                }.also {
                    _dataLoading.value = false
                }
        }
    }

    fun nextEvent(phone: String, birthday: String) {
        viewModelScope.launch {
            _dataLoading.value = true
            patchSignupBirthdayUseCase(
                phone,
                removeSpaceAfterPeriod(birthday)
            ).onSuccess {
                _sideEffectFlow.emit(BirthdaySideEffect.NavigateNextView)
            }.onFailure {
                BirthdaySideEffect.ShowToast(
                    stringProvider.getString(StringProvider.ResId.BirthdayPatchFail)
                )
            }.also {
                _dataLoading.value = false
            }
        }
    }

    fun datePickerEvent() {
        postSideEffect(BirthdaySideEffect.ShowDatePicker)
    }

    fun setBirthdayEvent(birthday: String) {
        setUiState(
            BirthdayUiState.ValidBirthday(
                if (birthday.length < 12) addSpaceAfterPeriod(birthday) else birthday
            )
        )
    }

    private fun addSpaceAfterPeriod(str: String): String =
        StringBuilder(str).insert(5, ' ').insert(9, ' ').toString()

    private fun removeSpaceAfterPeriod(str: String): String =
        StringBuilder(str).deleteCharAt(0).deleteCharAt(8).toString()


    sealed class BirthdayUiState : UiState {
        data class InvalidPhoneNumber(val message: String) : BirthdayUiState()
        object Default : BirthdayUiState()
        object InvalidBirthday : BirthdayUiState()
        data class ValidBirthday(val birthday: String) : BirthdayUiState()
    }

    sealed class BirthdaySideEffect : SideEffect {
        object ShowDatePicker : BirthdaySideEffect()
        object NavigateNextView : BirthdaySideEffect()
        data class ShowToast(val message: String) : BirthdaySideEffect()
    }
}
