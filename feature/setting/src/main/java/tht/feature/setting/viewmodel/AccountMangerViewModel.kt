package tht.feature.setting.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tht.tht.domain.setting.usecase.FetchAccountManageItemsUseCase
import com.tht.tht.domain.user.LogoutUseCase
import com.tht.tht.domain.user.UserDisActiveUseCase
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
import tht.feature.setting.delegate.ParseAccountManagerEventDelegate
import tht.feature.setting.uimodel.SettingListItemUiModel
import tht.feature.setting.uimodel.SettingSectionUiModel
import tht.feature.setting.uimodel.event.AccountManagerEvent
import tht.feature.setting.uimodel.mapper.toUiModel
import javax.inject.Inject

@HiltViewModel
class AccountMangerViewModel @Inject constructor(
    parseAccountManagerEventDelegate: ParseAccountManagerEventDelegate,
    private val fetchAccountManageItemsUseCase: FetchAccountManageItemsUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val userDisActiveUseCase: UserDisActiveUseCase
) : ViewModel(), ParseAccountManagerEventDelegate by parseAccountManagerEventDelegate {

    data class State(
        val loading: Boolean,
        val items: ImmutableList<SettingSectionUiModel>
    )

    sealed interface SideEffect {
        object NavigateIntro : SideEffect

        data class ShowErrorMessage(
            val message: String
        ) : SideEffect
    }

    private val _state = MutableStateFlow(
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
            fetchAccountManageItemsUseCase()
                .onSuccess { items ->
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

    fun onSettingItemClick(item: SettingListItemUiModel) {
        kotlin.runCatching {
            parseEvent(item.key)
        }.onSuccess {
            when (it) {
                AccountManagerEvent.Logout -> onLogout()
                AccountManagerEvent.DisActive -> onDisActive()
            }
        }.onFailure {
            it.printStackTrace()
        }
    }

    private fun onLogout() {
        if (state.value.loading) return
        viewModelScope.launch {
            _state.update { it.copy(loading = true) }
            logoutUseCase()
                .onSuccess {
                    _sideEffect.emit(SideEffect.NavigateIntro)
                }.onFailure {
                    _sideEffect.emit(
                        SideEffect.ShowErrorMessage(
                            "Fail Logout"
                        )
                    )
                    _state.update { it.copy(loading = false) }
                }
        }
    }

    private fun onDisActive() {
        if (state.value.loading) return
        viewModelScope.launch {
            _state.update { it.copy(loading = true) }
            userDisActiveUseCase(
                "Test",
                "Test"
            ).onSuccess {
                _sideEffect.emit(SideEffect.NavigateIntro)
            }.onFailure {
                it.printStackTrace()
                _sideEffect.emit(
                    SideEffect.ShowErrorMessage(
                        "Fail DisActive"
                    )
                )
                _state.update { it.copy(loading = false) }
            }
        }
    }
}
