package tht.feature.signin.signup.height

import androidx.lifecycle.SavedStateHandle
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
class HeightViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val fetchSignupUserUseCase: FetchSignupUserUseCase,
    private val patchSignupDataUseCase: PatchSignupDataUseCase,
    private val stringProvider: StringProvider
) : BaseStateViewModel<HeightUiState, HeightViewModel.HeightSideEffect>() {

    private val phone: String = savedStateHandle[KEY_PHONE] ?: ""

    sealed interface HeightSideEffect : SideEffect {
        data class ShowToast(val message: String) : HeightSideEffect

        object NavigateNextView : HeightSideEffect
    }

    override val _uiStateFlow: MutableStateFlow<HeightUiState> = MutableStateFlow(HeightUiState.DEFAULT)

    init {
        viewModelScope.launch {
            _uiStateFlow.update { it.copy(loading = true) }
            fetchSignupUserUseCase(phone)
                .onSuccess { user ->
                    _uiStateFlow.update {
                        it.copy(
                            height = user.height.takeIf { h -> h >= 0 }
                        )
                    }
                }.onFailure {
                    _sideEffectFlow.emit(
                        HeightSideEffect.ShowToast(
                            stringProvider.getString(StringProvider.ResId.InvalidateSignupProcess) +
                                stringProvider.getString(StringProvider.ResId.CustomerService)
                        )
                    )
                }
            _uiStateFlow.update { it.copy(loading = false) }
        }
    }

    fun onClickHeightInput() {
        viewModelScope.launch {
            _uiStateFlow.update { it.copy(heightSelectModalShow = true) }
        }
    }

    fun onSelectHeight(height: Int) {
        _uiStateFlow.update { it.copy(height = height) }
    }

    fun onBackClick() {
        if (_uiStateFlow.value.heightSelectModalShow) {
            _uiStateFlow.update { it.copy(heightSelectModalShow = false) }
        }
    }

    fun onHeightSelectModalShow() {

    }

    fun onHeightSelectModalHide() {
        _uiStateFlow.update { it.copy(heightSelectModalShow = false) }
    }

    fun onNext() {
        viewModelScope.launch {
            val height = _uiStateFlow.value.height
            if (height == null) {
                _sideEffectFlow.emit(
                    HeightSideEffect.ShowToast(
                        stringProvider.getString(StringProvider.ResId.RequireSelectHeight)
                    )
                )
                return@launch
            }
            _uiStateFlow.update { it.copy(loading = true) }
            patchSignupDataUseCase(
                phone = phone,
                reduce = { it.copy(height = height) }
            ).onSuccess {
                _sideEffectFlow.emit(HeightSideEffect.NavigateNextView)
            }.onFailure {
                it.printStackTrace()
                _sideEffectFlow.emit(
                    HeightSideEffect.ShowToast(
                        stringProvider.getString(StringProvider.ResId.HeightPathFail)
                    )
                )
            }
            _uiStateFlow.update { it.copy(loading = false) }
        }
    }

    companion object {
        private const val KEY_PHONE = "phone"
    }
}
