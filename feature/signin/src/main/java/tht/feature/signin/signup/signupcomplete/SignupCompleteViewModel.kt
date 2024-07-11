package tht.feature.signin.signup.signupcomplete

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.tht.tht.domain.signup.usecase.FetchSignupUserUseCase
import com.tht.tht.domain.signup.usecase.RemoveSignupUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import tht.core.ui.base.BaseStateViewModel
import tht.core.ui.base.SideEffect
import tht.core.ui.base.UiState
import javax.inject.Inject

@HiltViewModel
class SignupCompleteViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    fetchSignupUserUseCase: FetchSignupUserUseCase,
    private val removeSignupUserUseCase: RemoveSignupUserUseCase
) : BaseStateViewModel<SignupCompleteViewModel.SignupCompleteState, SignupCompleteViewModel.SignupSideEffect>() {

    private val phone: String? = savedStateHandle[EXTRA_KEY_PHONE]

    sealed interface SignupSideEffect : SideEffect {
        object NavigateMain : SignupSideEffect
    }

    data class SignupCompleteState(
        val loading: Boolean,
        val profileImage: String?,
        val error: Throwable? = null
    ) : UiState {
        companion object {
            val default: SignupCompleteState get() = SignupCompleteState(
                loading = false,
                profileImage = null
            )
        }
    }

    override val _uiStateFlow: MutableStateFlow<SignupCompleteState> = MutableStateFlow(SignupCompleteState.default)

    init {
        viewModelScope.launch {
            _uiStateFlow.update { it.copy(loading = true) }
            if (phone.isNullOrBlank()) {
                _uiStateFlow.update { s -> s.copy(error = Exception("None Signup User Data")) }
                _uiStateFlow.update { it.copy(loading = false) }
                return@launch
            }
            fetchSignupUserUseCase(phone)
                .onSuccess { user ->
                    val profileUrl = user.profileImgUrl.firstOrNull()
                    if (profileUrl.isNullOrBlank()) {
                        _uiStateFlow.update { s -> s.copy(error = Exception("None Profile Image")) }
                        return@onSuccess
                    }
                    _uiStateFlow.update {
                        it.copy(
                            profileImage = user.profileImgUrl.firstOrNull()
                        )
                    }
                }.onFailure {
                    it.printStackTrace()
                    _uiStateFlow.update { s -> s.copy(error = it) }
                }
            _uiStateFlow.update { it.copy(loading = false) }
        }
    }

    fun onCompleteEvent() {
        viewModelScope.launch {
            _uiStateFlow.update { it.copy(loading = true) }
            if (phone.isNullOrBlank()) {
                _uiStateFlow.update { s -> s.copy(error = Exception("None Signup User Data")) }
                return@launch
            }
            removeSignupUserUseCase(phone)
            postSideEffect(SignupSideEffect.NavigateMain)
            _uiStateFlow.update { it.copy(loading = false) }
        }
    }

    companion object {
        const val EXTRA_KEY_PHONE = "extra-key_phone"
    }
}
