package tht.feature.signin.auth

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.compose_ui.common.viewmodel.Container
import com.example.compose_ui.common.viewmodel.Store
import com.example.compose_ui.common.viewmodel.intent
import com.example.compose_ui.common.viewmodel.store
import com.tht.tht.domain.signup.usecase.RequestAuthenticationUseCase
import com.tht.tht.domain.type.SignInType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import tht.core.ui.base.SideEffect
import tht.feature.signin.StringProvider
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class PhoneAuthViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val requestAuthenticationUseCase: RequestAuthenticationUseCase,
    private val stringProvider: StringProvider
) : ViewModel(), Container<PhoneAuthUiState, PhoneAuthViewModel.PhoneAuthSideEffect> {

    override val store: Store<PhoneAuthUiState, PhoneAuthSideEffect> = store(initialState = PhoneAuthUiState.DEFAULT)

    private val signInType: SignInType = savedStateHandle[EXTRA_SIGN_IN_TYPE_KEY] ?: SignInType.NORMAL

    fun textInputEvent(text: String?) {
        val validation = if (text.isNullOrEmpty() || text.length < 11) {
            PhoneAuthUiState.PhoneValidation.IDLE
        } else {
            when (checkPhoneValidation(text)) {
                true -> PhoneAuthUiState.PhoneValidation.VALIDATE
                else -> PhoneAuthUiState.PhoneValidation.INVALIDATE
            }
        }
        intent {
            reduce {
                it.copy(
                    phone = text ?: "",
                    phoneValidation = validation
                )
            }
        }
    }
    private fun checkPhoneValidation(phone: String): Boolean {
        val phoneNumPattern = "^01([0|16789])(\\d{4})(\\d{4})$"
        return Pattern.matches(phoneNumPattern, phone)
    }

    fun backgroundTouchEvent() {
        intent {
            postSideEffect(PhoneAuthSideEffect.KeyboardVisible(false))
        }
    }

    fun backEvent() {
        intent {
            postSideEffect(PhoneAuthSideEffect.Back)
        }
    }

    fun clearEvent() {
        intent {
            reduce {
                it.copy(
                    phone = "",
                    phoneValidation = PhoneAuthUiState.PhoneValidation.IDLE
                )
            }
        }
    }

    fun authEvent() {
        val phone = store.state.value.phone
        if (phone.isEmpty() || !checkPhoneValidation(phone)) {
            intent {
                reduce { it.copy(phoneValidation = PhoneAuthUiState.PhoneValidation.INVALIDATE) }
            }
            return
        }
        viewModelScope.launch {
            intent { reduce { it.copy(loading = true) } }
            requestAuthenticationUseCase(phone)
                .onSuccess {
                    intent {
                        postSideEffect(PhoneAuthSideEffect.NavigateVerifyView(phone, it, signInType))
                    }
                }.onFailure {
                    intent {
                        postSideEffect(
                            PhoneAuthSideEffect.ShowToast(
                                (stringProvider.getString(StringProvider.ResId.SendAuthFail) + it.message)
                            )
                        )
                    }
                }
            intent {
                reduce { it.copy(loading = false) }
                postSideEffect(PhoneAuthSideEffect.KeyboardVisible(false))
            }
        }
    }

    sealed class PhoneAuthSideEffect : SideEffect {
        data class ShowToast(val message: String) : PhoneAuthSideEffect()
        data class NavigateVerifyView(
            val phone: String,
            val authNum: String,
            val signInType: SignInType
        ) : PhoneAuthSideEffect()
        data class KeyboardVisible(val visible: Boolean) : PhoneAuthSideEffect()
        object Back : PhoneAuthSideEffect()
    }

    companion object {
        const val EXTRA_SIGN_IN_TYPE_KEY = "extra_sign_in_type_key"
    }
}
