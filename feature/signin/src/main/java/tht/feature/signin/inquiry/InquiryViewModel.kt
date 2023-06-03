package tht.feature.signin.inquiry

import android.util.Patterns
import androidx.lifecycle.viewModelScope
import com.tht.tht.domain.email.SendEmailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import tht.core.ui.base.BaseStateViewModel
import tht.core.ui.base.SideEffect
import tht.core.ui.base.UiState
import tht.feature.signin.StringProvider
import javax.inject.Inject

@HiltViewModel
class InquiryViewModel @Inject constructor(
    private val sendEmailUseCase: SendEmailUseCase,
    private val stringProvider: StringProvider
) : BaseStateViewModel<InquiryViewModel.InquiryUiState, InquiryViewModel.InquirySideEffect>() {

    override val _uiStateFlow: MutableStateFlow<InquiryUiState> =
        MutableStateFlow(InquiryUiState.Default)

    private val _dataLoading = MutableStateFlow(false)
    val dataLoading = _dataLoading.asStateFlow()

    private val content = MutableStateFlow("")

    private val email = MutableStateFlow("").also { flow ->
        flow.onEach {
            setUiState(InquiryUiState.EmailDefault)
        }.debounce(500)
            .onEach {
                setUiState(
                    when {
                        it.isEmpty() -> InquiryUiState.EmailDefault
                        Patterns.EMAIL_ADDRESS.matcher(it).matches() -> InquiryUiState.EmailCorrect
                        else -> InquiryUiState.EmailError
                    }
                )
            }.launchIn(viewModelScope)
    }

    private val checkState = MutableStateFlow(false).also { flow ->
        flow.onEach {
            setUiState(if (it) InquiryUiState.Checked else InquiryUiState.Unchecked)
        }.launchIn(viewModelScope)
    }

    init {
        combine(email, content, checkState) { emailValue, contentValue, checkStateValue ->
            Triple(emailValue, contentValue, checkStateValue)
        }.also { flow ->
            flow.onEach {
                setUiState(
                    when {
                        Patterns.EMAIL_ADDRESS.matcher(it.first).matches() && it.second.isNotEmpty() && it.third -> {
                            InquiryUiState.ButtonValid
                        }
                        else -> {
                            InquiryUiState.ButtonInvalid
                        }
                    }
                )
            }.launchIn(viewModelScope)
        }
    }

    fun sendEmail(email: String, content: String) {
        viewModelScope.launch {
            _dataLoading.value = true
            sendEmailUseCase(email, content).onSuccess {
                postSideEffect(InquirySideEffect.ShowCompleteDialog)
            }.onFailure {
                postSideEffect(
                    InquirySideEffect.ShowToast(
                        stringProvider.getString(
                            StringProvider.ResId.EmailSendFail
                        )
                    )
                )
            }.also {
                _dataLoading.value = false
            }
        }
    }

    fun setEmail(text: String) {
        email.value = text
    }

    fun setContent(text: String) {
        content.value = text
    }

    fun setCheckState(isChecked: Boolean) {
        checkState.value = isChecked
    }

    sealed class InquiryUiState : UiState {
        object Default : InquiryUiState()
        object EmailDefault : InquiryUiState()
        object EmailError : InquiryUiState()
        object EmailCorrect : InquiryUiState()
        object Unchecked : InquiryUiState()
        object Checked : InquiryUiState()
        object ButtonValid : InquiryUiState()
        object ButtonInvalid : InquiryUiState()
    }

    sealed class InquirySideEffect : SideEffect {
        data class ShowToast(val message: String) : InquirySideEffect()
        object ShowCompleteDialog : InquirySideEffect()
    }
}
