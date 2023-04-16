package tht.feature.signin.signup.location

import androidx.lifecycle.viewModelScope
import com.tht.tht.domain.signup.usecase.FetchLocationUseCase
import com.tht.tht.domain.signup.usecase.FetchSignupUserUseCase
import com.tht.tht.domain.signup.usecase.PatchSignupLocationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import tht.core.ui.base.BaseStateViewModel
import tht.core.ui.base.SideEffect
import tht.core.ui.base.UiState
import tht.feature.signin.StringProvider
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
    private val fetchSignupUserUseCase: FetchSignupUserUseCase,
    private val patchSignupLocationUseCase: PatchSignupLocationUseCase,
    private val fetchLocationUseCase: FetchLocationUseCase,
    private val stringProvider: StringProvider
) : BaseStateViewModel<LocationViewModel.LocationUiState, LocationViewModel.LocationSideEffect>() {

    override val _uiStateFlow: MutableStateFlow<LocationUiState> =
        MutableStateFlow(LocationUiState.Default)

    fun checkPermissionEvent() {
        postSideEffect(LocationSideEffect.CheckPermission)
    }

    fun getCurrentLocation() {
        viewModelScope.launch {
            fetchLocationUseCase()
                .onSuccess { location ->
                    setUiState(LocationUiState.CurrentLocation(location.address))
                }.onFailure {
                    emitMessage(stringProvider.getString(StringProvider.ResId.InvalidateLocation))
                }
        }
    }

    fun showLocationDialog() {

    }

    private suspend fun emitMessage(message: String) {
        _sideEffectFlow.emit(LocationSideEffect.ShowToast(message))
    }

    sealed class LocationUiState : UiState {
        data class CurrentLocation(val location: String) : LocationUiState()
        object Default : LocationUiState()
    }
    sealed class LocationSideEffect : SideEffect {
        data class ShowToast(val message: String) : LocationSideEffect()
        object CheckPermission : LocationSideEffect()
        object GetCurrentLocation : LocationSideEffect()
        object ShowLocationDialog : LocationSideEffect()
    }
}
