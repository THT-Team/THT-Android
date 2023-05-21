package tht.feature.signin.signup.location

import androidx.lifecycle.viewModelScope
import com.tht.tht.domain.signup.model.LocationModel
import com.tht.tht.domain.signup.usecase.FetchCurrentLocationUseCase
import com.tht.tht.domain.signup.usecase.FetchLocationByAddressUseCase
import com.tht.tht.domain.signup.usecase.PatchSignupDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import tht.core.ui.base.BaseStateViewModel
import tht.core.ui.base.SideEffect
import tht.core.ui.base.UiState
import tht.feature.signin.StringProvider
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
    private val patchSignupDataUseCase: PatchSignupDataUseCase,
    private val fetchCurrentLocationUseCase: FetchCurrentLocationUseCase,
    private val fetchLocationByAddressUseCase: FetchLocationByAddressUseCase,
    private val stringProvider: StringProvider
) : BaseStateViewModel<LocationViewModel.LocationUiState, LocationViewModel.LocationSideEffect>() {

    override val _uiStateFlow: MutableStateFlow<LocationUiState> =
        MutableStateFlow(LocationUiState.InvalidInput)

    private val _location = MutableStateFlow(LocationModel(0.0, 0.0, "")).also { flow ->
        flow.onEach {
            checkValidInput(it.address)
        }.launchIn(viewModelScope)
    }
    val location = _location.asStateFlow()

    private val _dataLoading = MutableStateFlow(false)
    val dataLoading = _dataLoading.asStateFlow()

    fun checkLocationEvent() {
        if (_location.value.address.isEmpty())
            postSideEffect(LocationSideEffect.CheckPermission)
        else
            postSideEffect(LocationSideEffect.ShowLocationDialog)
    }

    fun fetchCurrentLocation() {
        viewModelScope.launch {
            _dataLoading.value = true
            fetchCurrentLocationUseCase()
                .onSuccess { location ->
                    _location.value = location
                }.onFailure {
                    _sideEffectFlow.emit(
                        LocationSideEffect.ShowToast(
                            stringProvider.getString(StringProvider.ResId.AutoLocationFetchFail)
                        )
                    )
                    postSideEffect(LocationSideEffect.ShowLocationDialog)
                }.also {
                    _dataLoading.value = false
                }
        }
    }

    fun fetchLocationByAddress(address: String) {
        viewModelScope.launch {
            _dataLoading.value = true
            fetchLocationByAddressUseCase(address)
                .onSuccess { location ->
                    _location.value = location
                }.onFailure {
                    _sideEffectFlow.emit(
                        LocationSideEffect.ShowToast(
                            stringProvider.getString(StringProvider.ResId.FetchLocationFail)
                        )
                    )
                }.also {
                    _dataLoading.value = false
                }
        }
    }

    fun dialogEvent() {
        postSideEffect(LocationSideEffect.ShowLocationDialog)
    }

    fun nextEvent(phone: String) {
        viewModelScope.launch {
            location.value.run {
                if (lat < 0.0 || lng < 0.0 || address.isBlank()) {
                    LocationSideEffect.ShowToast(
                        stringProvider.getString(StringProvider.ResId.InvalidateLocation)
                    )
                    return@launch
                }
            }
            _dataLoading.value = true
            patchSignupDataUseCase(phone) {
                it.copy(
                    lat = location.value.lat,
                    lng = location.value.lng,
                    address = location.value.address
                )
            }.onSuccess {
                _sideEffectFlow.emit(LocationSideEffect.NavigateNextView)
            }.onFailure {
                _sideEffectFlow.emit(
                    LocationSideEffect.ShowToast(
                        stringProvider.getString(StringProvider.ResId.LocationPatchFail)
                    )
                )
            }.also {
                _dataLoading.value = false
            }
        }
    }

    private fun checkValidInput(location: String) {
        if (location.isEmpty())
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
        object NavigateNextView : LocationSideEffect()
    }
}
