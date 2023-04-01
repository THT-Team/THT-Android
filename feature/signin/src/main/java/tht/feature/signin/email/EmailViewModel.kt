package tht.feature.signin.email

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.tht.tht.domain.signup.model.SignupException
import com.tht.tht.domain.signup.usecase.FetchSignupUserUseCase
import com.tht.tht.domain.signup.usecase.PatchSignupEmailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import tht.core.ui.base.BaseStateViewModel
import tht.core.ui.base.SideEffect
import tht.core.ui.base.UiState
import tht.feature.signin.StringProvider
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class EmailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val fetchSignupUserUseCase: FetchSignupUserUseCase,
    private val patchSignupEmailUseCase: PatchSignupEmailUseCase,
    private val stringProvider: StringProvider
) : BaseStateViewModel<EmailViewModel.EmailUiState, EmailViewModel.EmailSideEffect>() {

    override val _uiStateFlow: MutableStateFlow<EmailUiState> = MutableStateFlow(EmailUiState.InputEmailEmpty)

    private val phone = savedStateHandle.getStateFlow(EXTRA_PHONE_KEY, "")
    val email = savedStateHandle.getStateFlow(KEY_EMAIL, "")

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

    private val _emailAutoComplete = MutableStateFlow<List<String>>(emptyList())
    val emailAutoComplete = _emailAutoComplete.asStateFlow()

    private val _dataLoading = MutableStateFlow(false)
    val dataLoading = _dataLoading.asStateFlow()

    init {
        if (phone.value.isBlank()) {
            _uiStateFlow.value =
                EmailUiState.InvalidatePhone(
                    stringProvider.getString(StringProvider.ResId.InvalidatePhone)
                )
        } else {
            viewModelScope.launch {
                _dataLoading.value = true
                fetchSignupUserUseCase(phone.value)
                    .onSuccess {
                        savedStateHandle[KEY_EMAIL] = it.email
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
                        _dataLoading.value = false
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

    fun textInputEvent(text: String?) {
        inputFlow.value = text ?: ""
        if (text.isNullOrBlank()) {
            setUiState(EmailUiState.InputEmailEmpty)
            return
        }
        when (checkEmailValidation(text)) {
            true -> setUiState(EmailUiState.InputEmailCorrect)
            else -> setUiState(EmailUiState.InputEmailError)
        }
    }

    fun backgroundTouchEvent() {
        postSideEffect(EmailSideEffect.KeyboardVisible(false))
    }

    fun backEvent() {
        postSideEffect(EmailSideEffect.Back)
    }

    fun nextEvent(email: String?) {
        viewModelScope.launch {
            if (email.isNullOrBlank() || !checkEmailValidation(email)) {
                setUiState(EmailUiState.InputEmailError)
                return@launch
            }
            _dataLoading.value = true
            patchSignupEmailUseCase(phone.value, email)
                .onSuccess {
                    _sideEffectFlow.emit(EmailSideEffect.NavigateNextView(phone.value))
                }.onFailure {
                    _sideEffectFlow.emit(
                        EmailSideEffect.ShowToast(
                            stringProvider.getString(StringProvider.ResId.EmailPatchFail)
                        )
                    )
                }.also {
                    _dataLoading.value = false
                }
        }
    }

    sealed class EmailUiState : UiState {
        object InputEmailEmpty : EmailUiState()

        object InputEmailError : EmailUiState()

        object InputEmailCorrect : EmailUiState()

        data class InvalidatePhone(val message: String) : EmailUiState()
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
