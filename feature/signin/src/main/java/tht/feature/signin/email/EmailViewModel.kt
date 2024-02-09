package tht.feature.signin.email

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.tht.tht.domain.signup.model.SignupException
import com.tht.tht.domain.signup.usecase.FetchSignupUserUseCase
import com.tht.tht.domain.signup.usecase.PatchSignupDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import tht.core.ui.base.BaseStateViewModel
import tht.core.ui.base.SideEffect
import tht.feature.signin.StringProvider
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class EmailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val fetchSignupUserUseCase: FetchSignupUserUseCase,
    private val patchSignupDataUseCase: PatchSignupDataUseCase,
    private val stringProvider: StringProvider
) : BaseStateViewModel<EmailUiState, EmailViewModel.EmailSideEffect>() {

    override val _uiStateFlow: MutableStateFlow<EmailUiState> = MutableStateFlow(
        EmailUiState.DEFAULT.copy(
            email = savedStateHandle[KEY_EMAIL] ?: ""
        )
    )

    private val phone = savedStateHandle.getStateFlow(EXTRA_PHONE_KEY, "")

    private val emailTemplate = listOf(
        "@naver.com",
        "@gmail.com",
        "@kakao.com"
    )
    private val inputFlow = MutableStateFlow("").also { flow ->
        flow.debounce(500)
            .filter { it.isNotEmpty() && !it.contains('@') }
            .onEach { input ->
                _emailAutoComplete.value = emailTemplate.map {
                    "$input$it"
                }
            }.launchIn(viewModelScope)

        flow.debounce(500)
            .filter { it.isEmpty() || it.contains('@') }
            .onEach {
                _emailAutoComplete.value = emptyList()
            }.launchIn(viewModelScope)
    }

    // 자동 완성 기능은 임시 비활성화
    private val _emailAutoComplete = MutableStateFlow<List<String>>(emptyList())
    val emailAutoComplete = _emailAutoComplete.asStateFlow()

    init {
        if (phone.value.isBlank()) {
            _uiStateFlow.update { it.copy(invalidatePhone = true) }
        } else {
            viewModelScope.launch {
                _uiStateFlow.update { it.copy(loading = true) }
                fetchSignupUserUseCase(phone.value)
                    .onSuccess {
                        _uiStateFlow.update { it.copy(loading = true) }
                    }.onFailure {
                        when (it) {
                            is SignupException.SignupUserInvalidateException ->
                                _sideEffectFlow.emit(
                                    EmailSideEffect.ShowToast(
                                        stringProvider.getString(StringProvider.ResId.InvalidateSignupProcess) +
                                            stringProvider.getString(StringProvider.ResId.CustomerService)
                                    )
                                )
                            else -> _sideEffectFlow.emit(
                                EmailSideEffect.ShowToast(
                                    stringProvider.getString(StringProvider.ResId.InvalidateSignupProcess) +
                                        it.message
                                )
                            )
                        }
                    }.also {
                        _uiStateFlow.update { it.copy(loading = false) }
                    }
            }
        }
    }

    private fun checkEmailValidation(email: String): Boolean {
        val emailPattern = "[a-zA-Z\\d+._%\\-]{1,256}" +
            "@" +
            "[a-zA-Z\\d][a-zA-Z\\d\\-]{0,64}" +
            "(" +
            "\\." +
            "[a-zA-Z\\d][a-zA-Z\\d\\-]{0,25}" +
            ")+" // Patterns.EMAIL_ADDRESS
        return Pattern.matches(emailPattern, email)
    }

    fun onEmailInputEvent(text: String?) {
        inputFlow.value = text ?: ""
        if (text.isNullOrBlank()) {
            _uiStateFlow.update {
                it.copy(
                    email = "",
                    emailValidation = EmailUiState.EmailValidation.IDLE
                )
            }
            return
        }
        _uiStateFlow.update {
            it.copy(
                email = text,
                emailValidation = when (checkEmailValidation(text)) {
                    true -> EmailUiState.EmailValidation.VALIDATE
                    else -> EmailUiState.EmailValidation.INVALIDATE
                }
            )
        }
    }

    fun backgroundTouchEvent() {
        postSideEffect(EmailSideEffect.KeyboardVisible(false))
    }

    fun onBackEvent() {
        postSideEffect(EmailSideEffect.Back)
    }

    fun onClear() {
        _uiStateFlow.update {
            it.copy(
                email = "",
                emailValidation = EmailUiState.EmailValidation.IDLE
            )
        }
    }

    fun onNextEvent() {
        val email = _uiStateFlow.value.email
        viewModelScope.launch {
            if (email.isBlank() || !checkEmailValidation(email)) {
                _uiStateFlow.update { it.copy(emailValidation = EmailUiState.EmailValidation.INVALIDATE) }
                return@launch
            }
           _uiStateFlow.update { it.copy(loading = true) }
            patchSignupDataUseCase(phone.value) {
                it.copy(email = email)
            }.onSuccess {
                if (it) {
                    savedStateHandle[KEY_EMAIL] = email
                    _sideEffectFlow.emit(EmailSideEffect.NavigateNextView(phone.value))
                } else {
                    _sideEffectFlow.emit(
                        EmailSideEffect.ShowToast(
                            stringProvider.getString(StringProvider.ResId.EmailPatchFail)
                        )
                    )
                }
            }.onFailure {
                _sideEffectFlow.emit(
                    EmailSideEffect.ShowToast(
                        stringProvider.getString(StringProvider.ResId.EmailPatchFail) + it
                    )
                )
            }.also {
                _uiStateFlow.update { it.copy(loading = false) }
            }
        }
    }


    sealed class EmailSideEffect : SideEffect {
        data class ShowToast(val message: String) : EmailSideEffect()

        data class NavigateNextView(val phone: String) : EmailSideEffect()

        data class KeyboardVisible(val visible: Boolean) : EmailSideEffect()

        object Back : EmailSideEffect()
    }

    companion object {
        const val EXTRA_PHONE_KEY = "extra_phone_key"
        private const val KEY_EMAIL = "key_email"
    }
}
