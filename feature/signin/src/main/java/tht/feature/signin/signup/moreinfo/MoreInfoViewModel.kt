package tht.feature.signin.signup.moreinfo

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
class MoreInfoViewModel @Inject constructor(
    private val fetchSignupUserUseCase: FetchSignupUserUseCase,
    private val patchSignupDataUseCase: PatchSignupDataUseCase,
    private val stringProvider: StringProvider
) : BaseStateViewModel<MoreInfoUiState, MoreInfoViewModel.MoreInfoSideEffect>() {

    private var phone: String? = null

    sealed interface MoreInfoSideEffect : SideEffect {
        data class ShowToast(val message: String) : MoreInfoSideEffect

        object NavigateNextView : MoreInfoSideEffect
    }


    override val _uiStateFlow: MutableStateFlow<MoreInfoUiState> = MutableStateFlow(MoreInfoUiState.default)

    fun fetchSavedData(phone: String) {
        viewModelScope.launch {
            if (phone.isBlank()) {
                _sideEffectFlow.emit(
                    MoreInfoSideEffect.ShowToast(
                        stringProvider.getString(StringProvider.ResId.InvalidatePhone) +
                            stringProvider.getString(StringProvider.ResId.CustomerService)
                    )
                )
                return@launch
            }
            this@MoreInfoViewModel.phone = phone

            _uiStateFlow.update { it.copy(loading = true) }
            fetchSignupUserUseCase(phone)
                .onSuccess { user ->
                    _uiStateFlow.update {
                        it.copy(
                            smoke = MoreInfoUiState.Smoke.from(user.smoke),
                            drink = MoreInfoUiState.Drink.from(user.drink)
                        )
                    }
                }.onFailure {
                    _sideEffectFlow.emit(
                        MoreInfoSideEffect.ShowToast(
                            stringProvider.getString(StringProvider.ResId.InvalidateSignupProcess) +
                                stringProvider.getString(StringProvider.ResId.CustomerService)
                        )
                    )
                }
            _uiStateFlow.update { it.copy(loading = false) }
        }
    }

    fun onSmokeClick(smoke: MoreInfoUiState.Smoke) {
        _uiStateFlow.update { it.copy(smoke = smoke) }
    }

    fun onDrinkClick(drink: MoreInfoUiState.Drink) {
        _uiStateFlow.update { it.copy(drink = drink) }
    }

    fun onNextClick() {
        viewModelScope.launch {
            val phone = runCatching {
                requireNotNull(phone)
            }.onFailure {
                _sideEffectFlow.emit(
                    MoreInfoSideEffect.ShowToast(
                        stringProvider.getString(StringProvider.ResId.InvalidatePhone) +
                            stringProvider.getString(StringProvider.ResId.CustomerService)
                    )
                )
            }.getOrNull() ?: return@launch

            val smoke = _uiStateFlow.value.smoke
            val drink = _uiStateFlow.value.drink
            if (smoke == null || drink == null) {
                _sideEffectFlow.emit(
                    MoreInfoSideEffect.ShowToast(
                        stringProvider.getString(StringProvider.ResId.RequireSelectMoreInfo)
                    )
                )
                return@launch
            }
            _uiStateFlow.update { it.copy(loading = true) }
            patchSignupDataUseCase(
                phone = phone,
                reduce = {
                    it.copy(
                        smoke = smoke.toDomain(),
                        drink = drink.toDomain()
                    )
                }
            ).onSuccess {
                _sideEffectFlow.emit(MoreInfoSideEffect.NavigateNextView)
            }.onFailure {
                it.printStackTrace()
                _sideEffectFlow.emit(
                    MoreInfoSideEffect.ShowToast(
                        stringProvider.getString(StringProvider.ResId.MoreInfoPatchFail)
                    )
                )
            }
            _uiStateFlow.update { it.copy(loading = false) }
        }
    }
}
