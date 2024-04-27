package tht.feature.signin.religion

import androidx.lifecycle.viewModelScope
import com.tht.tht.domain.signup.usecase.FetchSignupUserUseCase
import com.tht.tht.domain.signup.usecase.PatchSignupDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import tht.core.ui.base.BaseStateViewModel
import tht.core.ui.base.SideEffect
import tht.feature.signin.StringProvider
import javax.inject.Inject

@HiltViewModel
class ReligionViewModel @Inject constructor(
    private val fetchSignupUserUseCase: FetchSignupUserUseCase,
    private val patchSignupDataUseCase: PatchSignupDataUseCase,
    private val stringProvider: StringProvider
) : BaseStateViewModel<ReligionUiState, ReligionViewModel.ReligionSideEffect>() {

    private var phone: String? = null

    sealed interface ReligionSideEffect : SideEffect {
        data class ShowToast(val message: String) : ReligionSideEffect

        object NavigateNextView : ReligionSideEffect
    }

    override val _uiStateFlow: MutableStateFlow<ReligionUiState> = MutableStateFlow(ReligionUiState.default)

    fun fetchSavedData(phone: String) {
        viewModelScope.launch {
            if (phone.isBlank()) {
                _sideEffectFlow.emit(
                    ReligionSideEffect.ShowToast(
                        stringProvider.getString(StringProvider.ResId.InvalidatePhone) +
                            stringProvider.getString(StringProvider.ResId.CustomerService)
                    )
                )
                return@launch
            }
            this@ReligionViewModel.phone = phone

            _uiStateFlow.update { it.copy(loading = true) }
            fetchSignupUserUseCase(phone)
                .onSuccess { user ->
                    _uiStateFlow.update {
                        it.copy(
                            religion = ReligionUiState.Religion.from(user.religion)
                        )
                    }
                }.onFailure {
                    _sideEffectFlow.emit(
                        ReligionSideEffect.ShowToast(
                            stringProvider.getString(StringProvider.ResId.InvalidateSignupProcess) +
                                stringProvider.getString(StringProvider.ResId.CustomerService)
                        )
                    )
                }
            _uiStateFlow.update { it.copy(loading = false) }
        }
    }

    fun onReligionClick(religion: ReligionUiState.Religion) {
        _uiStateFlow.update { it.copy(religion = religion) }
    }

    fun onNextClick() {
        viewModelScope.launch {
            val phone = runCatching {
                requireNotNull(phone)
            }.onFailure {
                _sideEffectFlow.emit(
                    ReligionSideEffect.ShowToast(
                        stringProvider.getString(StringProvider.ResId.InvalidatePhone) +
                            stringProvider.getString(StringProvider.ResId.CustomerService)
                    )
                )
            }.getOrNull() ?: return@launch

            val religion = _uiStateFlow.value.religion
            if (religion == null) {
                _sideEffectFlow.emit(
                    ReligionSideEffect.ShowToast(
                        stringProvider.getString(StringProvider.ResId.RequireSelectReligion)
                    )
                )
                return@launch
            }
            _uiStateFlow.update { it.copy(loading = true) }
            patchSignupDataUseCase(
                phone = phone,
                reduce = {
                    it.copy(religion = religion.name)
                }
            ).onSuccess {
                _sideEffectFlow.emit(ReligionSideEffect.NavigateNextView)
            }.onFailure {
                it.printStackTrace()
                _sideEffectFlow.emit(
                    ReligionSideEffect.ShowToast(
                        stringProvider.getString(StringProvider.ResId.ReligionPatchFail)
                    )
                )
            }
            _uiStateFlow.update { it.copy(loading = false) }
        }
    }
}
