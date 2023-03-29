package tht.feature.signin.signup.preferredgender

import androidx.lifecycle.viewModelScope
import com.tht.tht.domain.signup.usecase.FetchSignupUserUseCase
import com.tht.tht.domain.signup.usecase.PatchSignupPreferredGenderUseCase
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
class PreferredGenderViewModel @Inject constructor(
    private val fetchSignupUserUseCase: FetchSignupUserUseCase,
    private val patchSignupPreferredGenderUseCase: PatchSignupPreferredGenderUseCase,
    private val stringProvider: StringProvider
) : BaseStateViewModel<PreferredGenderViewModel.PreferredGenderUiState,
    PreferredGenderViewModel.PreferredGenderSideEffect>() {

    override val _uiStateFlow: MutableStateFlow<PreferredGenderUiState> =
        MutableStateFlow(PreferredGenderUiState.Default)

    private val _dataLoading = MutableStateFlow(false)
    val dataLoading = _dataLoading.asStateFlow()

    fun fetchSavedData(phone: String) {
        if (phone.isBlank()) {
            _uiStateFlow.value = PreferredGenderUiState.InvalidPhoneNumber(
                stringProvider.getString(StringProvider.ResId.InvalidatePhone)
            )
        }
        viewModelScope.launch {
            _dataLoading.value = true
            fetchSignupUserUseCase(phone)
                .onSuccess {
                    println("saved data => ${it.preferredGender}")
                    val selectIdx = when (it.preferredGender) {
                        female.first -> female.second
                        male.first -> male.second
                        all.first -> all.second
                        else -> return@onSuccess
                    }
                    _uiStateFlow.value = PreferredGenderUiState.SelectedGender(selectIdx)
                }.onFailure {
                    emitMessage(stringProvider.getString(StringProvider.ResId.InvalidateSignupProcess))
                }.also {
                    _dataLoading.value = false
                }
        }
    }

    fun selectGenderEvent(idx: Int) {
        _uiStateFlow.value = PreferredGenderUiState.SelectedGender(idx)
    }

    fun nextEvent(phone: String) {
        val gender = getGenderKey(
            (_uiStateFlow.value as? PreferredGenderUiState.SelectedGender)?.idx
        ) ?: return // TODO: ShowToast?
        viewModelScope.launch {
            _dataLoading.value = true
            patchSignupPreferredGenderUseCase(phone, gender)
                .onSuccess {
                    println("patch data => $it")
                    when (it) {
                        true -> postSideEffect(PreferredGenderSideEffect.NavigateNextView)
                        else ->
                            emitMessage(stringProvider.getString(StringProvider.ResId.PreferredGenderPatchFail))
                    }
                }.onFailure {
                    emitMessage(stringProvider.getString(StringProvider.ResId.PreferredGenderPatchFail) + it)
                }.also {
                    _dataLoading.value = false
                }
        }
    }

    private suspend fun emitMessage(message: String) {
        _sideEffectFlow.emit(PreferredGenderSideEffect.ShowToast(message))
    }

    private fun getGenderKey(idx: Int?): String? {
        return when (idx) {
            female.second -> female.first
            male.second -> male.first
            all.second -> all.first
            else -> null
        }
    }

    sealed class PreferredGenderUiState : UiState {
        data class InvalidPhoneNumber(val message: String) : PreferredGenderUiState()
        object Default : PreferredGenderUiState()
        data class SelectedGender(val idx: Int) : PreferredGenderUiState()
    }

    sealed class PreferredGenderSideEffect : SideEffect {
        data class ShowToast(val message: String) : PreferredGenderSideEffect()
        object NavigateNextView : PreferredGenderSideEffect()
    }

    companion object {
        private val female = "female" to 0
        private val male = "male" to 1
        private val all = "all" to 2
    }
}
