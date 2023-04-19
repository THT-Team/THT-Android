package tht.feature.signin.signup.location

import androidx.lifecycle.viewModelScope
import com.tht.tht.domain.signup.usecase.FetchCurrentLocationUseCase
import com.tht.tht.domain.signup.usecase.FetchLocationByAddressUseCase
import com.tht.tht.domain.signup.usecase.FetchSignupUserUseCase
import com.tht.tht.domain.signup.usecase.PatchSignupLocationUseCase
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
class LocationViewModel @Inject constructor(
    private val fetchSignupUserUseCase: FetchSignupUserUseCase,
    private val patchSignupLocationUseCase: PatchSignupLocationUseCase,
    private val fetchCurrentLocationUseCase: FetchCurrentLocationUseCase,
    private val fetchLocationByAddressUseCase: FetchLocationByAddressUseCase,
    private val stringProvider: StringProvider
) : BaseStateViewModel<LocationViewModel.LocationUiState, LocationViewModel.LocationSideEffect>() {

    override val _uiStateFlow: MutableStateFlow<LocationUiState> =
        MutableStateFlow(LocationUiState.InvalidInput)

    private val _location = MutableStateFlow("")
    val location = _location.asStateFlow()

    fun checkLocationEvent() {
        if(_location.value.isEmpty())
            postSideEffect(LocationSideEffect.CheckPermission)
        else
            postSideEffect(LocationSideEffect.ShowLocationDialog)
    }

    fun fetchCurrentLocation() {
        viewModelScope.launch {
            fetchCurrentLocationUseCase()
                .onSuccess { location ->
                    _location.value = location.address
                }.onFailure {
                    postSideEffect(LocationSideEffect.ShowToast(stringProvider.getString(StringProvider.ResId.InvalidateLocation)))
                }
        }
    }

    fun fetchLocationByAddress(address: String) {
        viewModelScope.launch {
            fetchLocationByAddressUseCase(address)
                .onSuccess { location ->
                    _location.value = location.address
                }.onFailure {
                    postSideEffect(LocationSideEffect.ShowToast(stringProvider.getString(StringProvider.ResId.InvalidateLocation)))
                }
        }
    }

    fun dialogEvent() {
        postSideEffect(LocationSideEffect.ShowLocationDialog)
    }

    fun checkValidInput(location: String) {
        if(location.isEmpty())
            setUiState(LocationUiState.InvalidInput)
        else
            setUiState(LocationUiState.ValidInput)
    }

    sealed class LocationUiState : UiState {
        object ValidInput : LocationUiState()
        object InvalidInput : LocationUiState()
    }

    sealed class LocationSideEffect : SideEffect {
        data class ShowToast(val message: String) : LocationSideEffect()
        object CheckPermission : LocationSideEffect()
        object ShowLocationDialog : LocationSideEffect()
        object NextEvent : LocationSideEffect()
    }
}
