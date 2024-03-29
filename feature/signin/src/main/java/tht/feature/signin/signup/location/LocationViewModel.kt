package tht.feature.signin.signup.location

import androidx.lifecycle.viewModelScope
import com.tht.tht.domain.signup.model.LocationModel
import com.tht.tht.domain.signup.model.SignupException
import com.tht.tht.domain.signup.usecase.FetchCurrentLocationUseCase
import com.tht.tht.domain.signup.usecase.FetchLocationByAddressUseCase
import com.tht.tht.domain.signup.usecase.PatchLocationUseCase
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
    private val fetchCurrentLocationUseCase: FetchCurrentLocationUseCase,
    private val fetchLocationByAddressUseCase: FetchLocationByAddressUseCase,
    private val patchLocationUseCase: PatchLocationUseCase,
    private val stringProvider: StringProvider
) : BaseStateViewModel<LocationViewModel.LocationUiState, LocationViewModel.LocationSideEffect>() {

    override val _uiStateFlow: MutableStateFlow<LocationUiState> =
        MutableStateFlow(LocationUiState.InvalidInput)

    private val fullLocation = MutableStateFlow(LocationModel(0.0, 0.0, "")).also { flow ->
        flow.onEach {
            checkValidInput(it.address)
            setLocationValue(it.address)
        }.launchIn(viewModelScope)
    }

    private val _location = MutableStateFlow("")
    val location = _location.asStateFlow()

    private val _dataLoading = MutableStateFlow(false)
    val dataLoading = _dataLoading.asStateFlow()

    fun checkLocationEvent() {
        if (fullLocation.value.address.isEmpty()) {
            postSideEffect(LocationSideEffect.CheckPermission)
        } else {
            postSideEffect(LocationSideEffect.ShowLocationDialog)
        }
    }

    fun fetchCurrentLocation() {
        viewModelScope.launch {
            _dataLoading.value = true
            fetchCurrentLocationUseCase()
                .onSuccess { location ->
                    fullLocation.value = location
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
                    fullLocation.value = location
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
            _dataLoading.value = true
            patchLocationUseCase(
                phone,
                fullLocation.value.lat,
                fullLocation.value.lng,
                fullLocation.value.address
            ).onSuccess {
                _sideEffectFlow.emit(LocationSideEffect.NavigateNextView)
            }.onFailure {
                _sideEffectFlow.emit(
                    LocationSideEffect.ShowToast(
                        when (it) {
                            is SignupException.InvalidateLocationInfo -> {
                                stringProvider.getString(StringProvider.ResId.InvalidateLocation)
                            }

                            is SignupException.SignupUserInvalidateException -> {
                                stringProvider.getString(StringProvider.ResId.SignupUserInvalidate) + " $it"
                            }

                            else -> {
                                stringProvider.getString(StringProvider.ResId.LocationPatchFail) + " $it"
                            }
                        }
                    )
                )
            }
            _dataLoading.value = false
        }
    }

    private fun setLocationValue(address: String) {
        _location.value = if (checkProvince(address)) {
            address.split(" ").run {
                takeLast(size - 1)
            }.joinToString(" ")
        } else {
            address
        }
    }

    private fun checkValidInput(location: String) {
        if (location.isEmpty()) {
            setUiState(LocationUiState.InvalidInput)
        } else {
            setUiState(LocationUiState.ValidInput)
        }
    }

    private fun checkProvince(address: String): Boolean =
        when (address.split(" ")[0]) {
            "부산광역시", "대구광역시", "인천광역시", "광주광역시",
            "대전광역시", "울산광역시", "서울특별시" -> false

            else -> true
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
