package tht.feature.signin.terms

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tht.tht.domain.signup.model.TermsModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import tht.core.ui.base.SideEffect
import javax.inject.Inject

@HiltViewModel
class TermsContentViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _sideEffectFlow = MutableSharedFlow<TermsContentSideEffect>()
    val sideEffectFlow = _sideEffectFlow.asSharedFlow()

    val terms: StateFlow<TermsModel> =
        savedStateHandle.getStateFlow(EXTRA_TERMS, TermsModel("", emptyList(), "", false))

    fun backEvent() {
        viewModelScope.launch {
            _sideEffectFlow.emit(TermsContentSideEffect.Back)
        }
    }

    sealed class TermsContentSideEffect : SideEffect {
        object Back : TermsContentSideEffect()
    }

    companion object {
        const val EXTRA_TERMS = "extra_terms_key"
    }
}
