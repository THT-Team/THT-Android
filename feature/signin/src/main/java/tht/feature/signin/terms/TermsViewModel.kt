package tht.feature.signin.terms

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.tht.tht.domain.signup.usecase.FetchSignupUserUseCase
import com.tht.tht.domain.signup.usecase.FetchTermsUseCase
import com.tht.tht.domain.signup.usecase.PatchSignupDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import tht.core.ui.base.BaseStateViewModel
import tht.core.ui.base.SideEffect
import tht.feature.signin.StringProvider
import tht.feature.signin.terms.mapper.toModel
import tht.feature.signin.terms.mapper.toUiModel
import tht.feature.signin.terms.uimodel.TermsUiModel
import javax.inject.Inject

@HiltViewModel
class TermsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val fetchSignupUserUseCase: FetchSignupUserUseCase,
    private val fetchTermsUseCase: FetchTermsUseCase,
    private val patchSignupDataUseCase: PatchSignupDataUseCase,
    private val stringProvider: StringProvider
) : BaseStateViewModel<TermsUiState, TermsViewModel.TermsSideEffect>() {

    private val phone = savedStateHandle.getStateFlow(EXTRA_PHONE_KEY, "")

    override val _uiStateFlow: MutableStateFlow<TermsUiState> = MutableStateFlow(TermsUiState.default)

    init {
        if (phone.value.isBlank()) {
            postSideEffect(
                TermsSideEffect.ShowToast(
                    stringProvider.getString(StringProvider.ResId.InvalidatePhone)
                )
            )
        } else {
            viewModelScope.launch {
                _uiStateFlow.update { it.copy(loading = true) }
                fetchTermsUseCase()
                    .onSuccess { termsList ->
                        _uiStateFlow.update {
                            it.copy(
                                terms = termsList.map { t -> t.toUiModel() }.toPersistentList()
                            )
                        }
                    }.onFailure {
                        _sideEffectFlow.emit(
                            TermsSideEffect.ShowToast(
                                stringProvider.getString(StringProvider.ResId.TermsFetchFail)
                            )
                        )
                    }

                fetchSignupUserUseCase(phone.value)
                    .onSuccess { user ->
                        _uiStateFlow.update {
                            it.copy(
                                terms = _uiStateFlow.value.terms
                                    .map { t -> t.copy(isSelect = user.termsAgreement.containsKey(t.toModel())) }
                                    .toPersistentList()
                            )
                        }
                        updateTermsAllSelectState()
                    }.onFailure {
                        it.printStackTrace()
                        _sideEffectFlow.emit(TermsSideEffect.ShowToast(it.toString()))
                    }
                _uiStateFlow.update { it.copy(loading = false) }
            }
        }
    }

    fun onTermsCheckClick(terms: TermsUiModel, idx: Int) {
        val updatedTermsList = _uiStateFlow.value.terms.toMutableList().apply {
            this[idx] = this[idx].copy(isSelect = !terms.isSelect)
        }.toPersistentList()
        _uiStateFlow.update { it.copy(terms = updatedTermsList) }
        updateTermsAllSelectState()
    }

    fun onTermsLinkClick(link: String?) {
        if (link.isNullOrBlank()) return
        postSideEffect(TermsSideEffect.NavigateTermsDetail(link))
    }

    fun onAllSelectClick() {
        val updatedTermsList = _uiStateFlow.value.terms
            .toMutableList()
            .map { it.copy(isSelect = !_uiStateFlow.value.isAllSelect) }
            .toPersistentList()
        _uiStateFlow.update { it.copy(terms = updatedTermsList) }
        updateTermsAllSelectState()
    }

    fun onStartClick() {
        if (!checkRequireTermsAllSelect()) {
            postSideEffect(
                TermsSideEffect.ShowToast(
                    stringProvider.getString(StringProvider.ResId.RequireTermsNeedSelect)
                )
            )
            return
        }
        viewModelScope.launch {
            _uiStateFlow.update { it.copy(loading = true) }
            val termsAgreement = _uiStateFlow.value
                .terms
                .associate { it.toModel() to it.isSelect }
            patchSignupDataUseCase(phone.value) {
                it.copy(
                    termsAgreement = termsAgreement
                )
            }.onSuccess {
                when (it) {
                    true -> _sideEffectFlow.emit(TermsSideEffect.NavigateNextView(phone.value))
                    else -> TermsSideEffect.ShowToast(
                        stringProvider.getString(StringProvider.ResId.TermsPatchFail)
                    )
                }
            }.onFailure {
                it.printStackTrace()
                _sideEffectFlow.emit(
                    TermsSideEffect.ShowToast(
                        stringProvider.getString(StringProvider.ResId.TermsPatchFail) + it
                    )
                )
            }
            _uiStateFlow.update { it.copy(loading = false) }
        }
    }

    fun onBackClick() {
        postSideEffect(TermsSideEffect.Back)
    }

    private fun checkRequireTermsAllSelect(): Boolean {
        return _uiStateFlow.value.isAllRequireTermsSelect
    }

    private fun updateTermsAllSelectState() {
        _uiStateFlow.value.terms.let { termsList ->
            _uiStateFlow.update {
                it.copy(
                    isAllSelect = termsList.filter { t -> t.isSelect }.size == termsList.size,
                    isAllRequireTermsSelect = termsList.all { !it.require || it.isSelect }
                )
            }
        }
    }

    sealed class TermsSideEffect : SideEffect {
        data class ShowToast(val message: String) : TermsSideEffect()

        data class NavigateTermsDetail(val link: String) : TermsSideEffect()

        data class NavigateNextView(val phone: String) : TermsSideEffect()

        object Back : TermsSideEffect()
    }

    companion object {
        const val EXTRA_PHONE_KEY = "extra_phone"
    }
}
