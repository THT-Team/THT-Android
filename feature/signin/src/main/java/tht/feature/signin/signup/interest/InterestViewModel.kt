package tht.feature.signin.signup.interest

import androidx.lifecycle.viewModelScope
import com.tht.tht.domain.signup.model.InterestModel
import com.tht.tht.domain.signup.usecase.FetchInterestUseCase
import com.tht.tht.domain.signup.usecase.FetchSignupUserUseCase
import com.tht.tht.domain.signup.usecase.PatchSignupInterestUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
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
class InterestViewModel @Inject constructor(
    private val fetchSignupUserUseCase: FetchSignupUserUseCase,
    private val fetchInterestUseCase: FetchInterestUseCase,
    private val patchSignupInterestUseCase: PatchSignupInterestUseCase,
    private val stringProvider: StringProvider
) : BaseStateViewModel<InterestViewModel.InterestTypeUiState, InterestViewModel.InterestTypeSideEffect>() {

    override val _uiStateFlow: MutableStateFlow<InterestTypeUiState> =
        MutableStateFlow(InterestTypeUiState.LessRequireSelectCount)

    private val _interestMap = MutableStateFlow<Map<Long, InterestModel>>(emptyMap())
    private val _interestList = MutableStateFlow<List<InterestModel>>(emptyList())
        .also { flow ->
            flow.onEach { list ->
                _interestMap.value = list.groupBy { it.key }
                    .filter { it.value.isNotEmpty() }
                    .mapValues { it.value.first() }
            }.launchIn(viewModelScope)
        }
    val interestList = _interestList.asStateFlow()

    private val _selectInterest = MutableStateFlow<Set<InterestModel>>(emptySet())
        .also { flow ->
            flow.onEach { set ->
                _uiStateFlow.value = when (set.size >= MAX_REQUIRE_SELECT_COUNT) {
                    true -> InterestTypeUiState.FullRequireSelectCount
                    else -> InterestTypeUiState.LessRequireSelectCount
                }
            }.launchIn(viewModelScope)
        }
    val selectInterest = _selectInterest.asStateFlow()

    private val _dataLoading = MutableStateFlow(false)
    val dataLoading = _dataLoading.asStateFlow()

    private var fetchInterestListJob: Job = viewModelScope.launch {
        _dataLoading.value = true
        fetchInterestUseCase()
            .onSuccess {
                _interestList.value = it.toList()
            }.onFailure {
                _uiStateFlow.value = InterestTypeUiState.FetchListFail(
                    stringProvider.getString(StringProvider.ResId.InterestPatchFail)
                )
            }.also {
                _dataLoading.value = false
            }
    }

    fun fetchSavedData(phone: String) {
        viewModelScope.launch {
            fetchInterestListJob.join()
            fetchSignupUser(phone)
        }
    }

    private suspend fun fetchSignupUser(phone: String) {
        if (phone.isBlank()) {
            _uiStateFlow.value = InterestTypeUiState.InvalidPhoneNumber(
                stringProvider.getString(StringProvider.ResId.InvalidatePhone)
            )
            return
        }
        _dataLoading.value = true
        fetchSignupUserUseCase(phone)
            .onSuccess { user ->
                val set = _selectInterest.value.toMutableSet()
                user.interestKeys.forEach { key ->
                    _interestMap.value[key]?.let { set.add(it) }
                }
                if (set.isNotEmpty()) _selectInterest.value = set
            }.onFailure {
                _sideEffectFlow.emit(
                    InterestTypeSideEffect.ShowToast(
                        stringProvider.getString(StringProvider.ResId.InvalidateSignupProcess) +
                            stringProvider.getString(StringProvider.ResId.CustomerService)
                    )
                )
            }.also {
                _dataLoading.value = false
            }
    }

    fun interestChipClickEvent(idx: Int) {
        val selectInterest = _interestList.value[idx]
        _selectInterest.value = _selectInterest.value.let {
            when (it.contains(selectInterest)) {
                true -> it.toMutableSet().apply {
                    remove(selectInterest)
                }

                else -> it.toMutableSet().apply {
                    if (it.size == MAX_REQUIRE_SELECT_COUNT) {
                        remove(it.iterator().next())
                    }
                    add(selectInterest)
                }
            }
        }
    }

    fun nextEvent(phone: String) {
        if (_selectInterest.value.size < MAX_REQUIRE_SELECT_COUNT) {
            return // show Toast
        }
        viewModelScope.launch {
            _dataLoading.value = true
            patchSignupInterestUseCase(
                phone,
                selectInterest.value.map { it.key }
            ).onSuccess {
                _sideEffectFlow.emit(InterestTypeSideEffect.NavigateNextView)
            }.onFailure {
                _sideEffectFlow.emit(
                    InterestTypeSideEffect.ShowToast(
                        stringProvider.getString(StringProvider.ResId.InterestPatchFail)
                    )
                )
            }.also {
                _dataLoading.value = false
            }
        }
    }

    sealed class InterestTypeUiState : UiState {
        data class InvalidPhoneNumber(val message: String) : InterestTypeUiState()
        data class FetchListFail(val message: String) : InterestTypeUiState()
        object LessRequireSelectCount : InterestTypeUiState()
        object FullRequireSelectCount : InterestTypeUiState()
    }

    sealed class InterestTypeSideEffect : SideEffect {
        data class ShowToast(val message: String) : InterestTypeSideEffect()
        object NavigateNextView : InterestTypeSideEffect()
    }

    companion object {
        const val MAX_REQUIRE_SELECT_COUNT = 3
    }
}
