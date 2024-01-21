package tht.feature.setting.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tht.tht.domain.setting.usecase.FetchSettingManageItemsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import tht.feature.setting.uimodel.SettingListItemUiModel
import tht.feature.setting.uimodel.SettingSectionUiModel
import tht.feature.setting.uimodel.mapper.toUiModel
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val fetchSettingSectionModel: FetchSettingManageItemsUseCase
) : ViewModel() {

    data class State(
        val loading: Boolean,
        val items: ImmutableList<SettingSectionUiModel>
    )

    sealed interface SideEffect {
        object NavigateAccountManage : SideEffect
    }

    private val _state = MutableStateFlow<State>(
        State(
            loading = false,
            items = persistentListOf()
        )
    )
    val state = _state.asStateFlow()

    private val _sideEffect = MutableSharedFlow<SideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(loading = true) }
            fetchSettingSectionModel().collect { res ->
                res.onSuccess { items ->
                    _state.update { state ->
                        state.copy(
                            items = items.map { it.toUiModel() }.toImmutableList()
                        )
                    }
                }.onFailure {
                    it.printStackTrace()
                }
                _state.update { it.copy(loading = false) }
            }
        }
    }

    fun onSettingItemClick(item: SettingListItemUiModel) {
        // key 를 enum 으로 받아서 처리?
        // key 를 화면 별로 구분 가능?
        when (item.title) {
            "계정 관리" -> viewModelScope.launch {
                _sideEffect.emit(SideEffect.NavigateAccountManage)
            }
        }
    }
}
