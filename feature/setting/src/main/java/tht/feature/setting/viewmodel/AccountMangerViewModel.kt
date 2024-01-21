package tht.feature.setting.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tht.tht.domain.user.LogoutUseCase
import com.tht.tht.domain.user.UserDisActiveUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountMangerViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase,
    private val userDisActiveUseCase: UserDisActiveUseCase
) : ViewModel() {

    data class State(
        val loading: Boolean
    )

    sealed interface SideEffect {
        object NavigateIntro : SideEffect

        data class ShowErrorMessage(
            val message: String
        ) : SideEffect
    }

    private val _state = MutableStateFlow(State(false))
    val state = _state.asStateFlow()

    private val _sideEffect = MutableSharedFlow<SideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    fun onLogout() {
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

    fun onDisActive() {
        if (state.value.loading) return
        viewModelScope.launch {
            _state.update { it.copy(loading = true) }
            userDisActiveUseCase(
                "Test",
                "Test"
            ).onSuccess {
                _sideEffect.emit(SideEffect.NavigateIntro)
            }.onFailure {
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
