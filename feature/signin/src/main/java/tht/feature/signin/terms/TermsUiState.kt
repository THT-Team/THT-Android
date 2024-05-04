package tht.feature.signin.terms

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import tht.core.ui.base.UiState
import tht.feature.signin.terms.uimodel.TermsUiModel

data class TermsUiState(
    val loading: Boolean,
    val isAllSelect: Boolean,
    val isAllRequireTermsSelect: Boolean,
    val terms: ImmutableList<TermsUiModel>
) : UiState {
    companion object {
        val default: TermsUiState get() = TermsUiState(
            loading = false,
            isAllSelect = false,
            isAllRequireTermsSelect = false,
            terms = persistentListOf()
        )
    }
}
