package tht.feature.signin.terms.uimodel

import androidx.compose.runtime.Immutable

@Immutable
data class TermsUiModel(
    val title: String,
    val key: String,
    val description: String?,
    val require: Boolean,
    val link: String?,
    val isSelect: Boolean
)
