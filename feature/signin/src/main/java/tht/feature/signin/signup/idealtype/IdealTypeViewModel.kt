package tht.feature.signin.signup.idealtype

import androidx.lifecycle.viewModelScope
import com.tht.tht.domain.signup.constant.SignupConstant
import com.tht.tht.domain.signup.model.IdealTypeModel
import com.tht.tht.domain.signup.usecase.FetchIdealTypeUseCase
import com.tht.tht.domain.signup.usecase.FetchSignupUserUseCase
import com.tht.tht.domain.signup.usecase.PatchSignupDataUseCase
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
class IdealTypeViewModel @Inject constructor(
    private val fetchSignupUserUseCase: FetchSignupUserUseCase,
    private val fetchIdealTypeUseCase: FetchIdealTypeUseCase,
    private val patchSignupDataUseCase: PatchSignupDataUseCase,
    private val stringProvider: StringProvider
) : BaseStateViewModel<IdealTypeViewModel.IdealTypeUiState, IdealTypeViewModel.IdealTypeSideEffect>() {

    override val _uiStateFlow: MutableStateFlow<IdealTypeUiState> =
        MutableStateFlow(IdealTypeUiState.LessRequireSelectCount)

    private val _idealTypesMap = MutableStateFlow<Map<Long, IdealTypeModel>>(emptyMap())
    private val _idealTypeList = MutableStateFlow<List<IdealTypeModel>>(emptyList())
        .also { flow ->
            flow.onEach { list ->
                _idealTypesMap.value = list.groupBy { it.key }
                    .filter { it.value.isNotEmpty() }
                    .mapValues { it.value.first() }
            }.launchIn(viewModelScope)
        }
    val idealTypeList = _idealTypeList.asStateFlow()

    private val _selectIdealTypes = MutableStateFlow<Set<IdealTypeModel>>(emptySet())
        .also { flow ->
            flow.onEach { set ->
                _uiStateFlow.value = when (set.size >= MAX_REQUIRE_SELECT_COUNT) {
                    true -> IdealTypeUiState.FullRequireSelectCount
                    else -> IdealTypeUiState.LessRequireSelectCount
                }
            }.launchIn(viewModelScope)
        }
    val selectIdealTypes = _selectIdealTypes.asStateFlow()

    private val _dataLoading = MutableStateFlow(false)
    val dataLoading = _dataLoading.asStateFlow()

    private var fetchIdealListJob: Job = viewModelScope.launch {
        _dataLoading.value = true
        fetchIdealTypeUseCase()
            .onSuccess {
                _idealTypeList.value = it.toList()
            }.onFailure {
                _uiStateFlow.value = IdealTypeUiState.FetchListFail(
                    stringProvider.getString(StringProvider.ResId.IdealFetchFail)
                )
            }.also {
                _dataLoading.value = false
            }
    }

    fun fetchSavedData(phone: String) {
        viewModelScope.launch {
            fetchIdealListJob.join()
            fetchSignupUser(phone)
        }
    }

    private suspend fun fetchSignupUser(phone: String) {
        if (phone.isBlank()) {
            _uiStateFlow.value = IdealTypeUiState.InvalidPhoneNumber(
                stringProvider.getString(StringProvider.ResId.InvalidatePhone)
            )
            return
        }
        _dataLoading.value = true
        fetchSignupUserUseCase(phone)
            .onSuccess { user ->
                val set = _selectIdealTypes.value.toMutableSet()
                user.idealTypeKeys.forEach { key ->
                    _idealTypesMap.value[key]?.let { set.add(it) }
                }
                if (set.isNotEmpty()) _selectIdealTypes.value = set
            }.onFailure {
                _sideEffectFlow.emit(
                    IdealTypeSideEffect.ShowToast(
                        stringProvider.getString(StringProvider.ResId.InvalidateSignupProcess) +
                            stringProvider.getString(StringProvider.ResId.CustomerService)
                    )
                )
            }.also {
                _dataLoading.value = false
            }
    }

    fun idealChipClickEvent(idx: Int) {
        val selectIdeal = _idealTypeList.value[idx]
        _selectIdealTypes.value = _selectIdealTypes.value.let {
            when (it.contains(selectIdeal)) {
                true -> it.toMutableSet().apply {
                    remove(selectIdeal)
                }
                else -> it.toMutableSet().apply {
                    if (it.size == MAX_REQUIRE_SELECT_COUNT) {
                        remove(it.iterator().next())
                    }
                    add(selectIdeal)
                }
            }
        }
    }

    fun nextEvent(phone: String) {
        if (_selectIdealTypes.value.size < MAX_REQUIRE_SELECT_COUNT) {
            return // show Toast
        }
        viewModelScope.launch {
            _dataLoading.value = true
            patchSignupDataUseCase(phone) {
                it.copy(idealTypeKeys = selectIdealTypes.value.map { it.key })
            }.onSuccess {
                _sideEffectFlow.emit(IdealTypeSideEffect.NavigateNextView)
            }.onFailure {
                _sideEffectFlow.emit(
                    IdealTypeSideEffect.ShowToast(
                        stringProvider.getString(StringProvider.ResId.IdealPatchFail)
                    )
                )
            }.also {
                _dataLoading.value = false
            }
        }
    }

    sealed class IdealTypeUiState : UiState {
        data class InvalidPhoneNumber(val message: String) : IdealTypeUiState()
        data class FetchListFail(val message: String) : IdealTypeUiState()
        object LessRequireSelectCount : IdealTypeUiState()
        object FullRequireSelectCount : IdealTypeUiState()
    }

    sealed class IdealTypeSideEffect : SideEffect {
        data class ShowToast(val message: String) : IdealTypeSideEffect()
        object NavigateNextView : IdealTypeSideEffect()
    }

    companion object {
        const val MAX_REQUIRE_SELECT_COUNT = SignupConstant.IDEAL_TYPE_REQUIRE_SIZE
    }
}
