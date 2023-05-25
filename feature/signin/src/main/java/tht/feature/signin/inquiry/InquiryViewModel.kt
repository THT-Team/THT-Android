package tht.feature.signin.inquiry

import android.util.Patterns
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import tht.core.ui.base.BaseStateViewModel
import tht.core.ui.base.SideEffect
import tht.core.ui.base.UiState

class InquiryViewModel : BaseStateViewModel<InquiryViewModel.InquiryUiState, InquiryViewModel.InquirySideEffect>() {

    override val _uiStateFlow: MutableStateFlow<InquiryUiState> =
        MutableStateFlow(InquiryUiState.Default)

    private val email = MutableStateFlow("").also { flow ->
        flow.onEach {
            setUiState(InquiryUiState.EmailDefault)
        }.debounce(500)
            .onEach {
                setUiState(
                    when {
                        it.isEmpty() -> InquiryUiState.Default
                        Patterns.EMAIL_ADDRESS.matcher(it).matches() -> InquiryUiState.EmailCorrect
                        else -> InquiryUiState.EmailError
                    }
                )
            }.launchIn(viewModelScope)
    }

    private val content = MutableStateFlow("")

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
                    if (
                        Patterns.EMAIL_ADDRESS.matcher(it.first).matches() &&
                        it.second.isNotEmpty() &&
                        it.third
                    ) InquiryUiState.ButtonValid
                    else InquiryUiState.ButtonInvalid
                )

            }.launchIn(viewModelScope)
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

    }
}
