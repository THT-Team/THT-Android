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
import tht.feature.signin.constant.GenderConstant
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale
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
                        when (it.birthday.isEmpty()) {
                            true -> BirthdayUiState.Default
                            else -> BirthdayUiState.ValidBirthday(
                                if (it.gender == female.first) female.second else male.second,
                                if (it.birthday.length < 12) addSpaceAfterPeriod(it.birthday) else it.birthday
                            )
                        }
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

    fun nextEvent(phone: String, gender: Int, birthday: String) {
        viewModelScope.launch {
            _dataLoading.value = true
            patchSignupBirthdayUseCase(
                phone,
                if (gender == female.second) female.first else male.first,
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

    fun setBirthdayEvent(gender: Int, birthday: String) {
        when(checkValidDate(birthday)) {
            true -> {
                setUiState(
                    BirthdayUiState.ValidBirthday(gender, birthday)
                )
            }
            false -> {
                postSideEffect(
                    BirthdaySideEffect.ShowToast(
                        stringProvider.getString(StringProvider.ResId.InvalidDate)
                    )
                )
            }
        }
    }

    private fun checkValidDate(date: String): Boolean {
        try {
            val dateFormat = SimpleDateFormat("yyyy. MM. dd", Locale.getDefault())
            dateFormat.isLenient = false
            dateFormat.parse(date)
        } catch (e: ParseException) {
            return false
        }
        return true
    }

    private fun addSpaceAfterPeriod(str: String): String =
        StringBuilder(str).insert(5, ' ').insert(9, ' ').toString()

    private fun removeSpaceAfterPeriod(str: String): String =
        StringBuilder(str).deleteCharAt(5).deleteCharAt(8).toString()

    sealed class BirthdayUiState : UiState {
        object Default : BirthdayUiState()
        data class ValidBirthday(val gender: Int, val birthday: String) : BirthdayUiState()
        data class InvalidPhoneNumber(val message: String) : BirthdayUiState()
    }

    sealed class BirthdaySideEffect : SideEffect {
        object ShowDatePicker : BirthdaySideEffect()
        object NavigateNextView : BirthdaySideEffect()
        data class ShowToast(val message: String) : BirthdaySideEffect()
    }

    companion object {
        val female = GenderConstant.FEMALE_KEY to 0
        val male = GenderConstant.MALE_KEY to 1
    }
}
