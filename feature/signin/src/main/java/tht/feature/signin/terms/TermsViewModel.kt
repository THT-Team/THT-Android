package tht.feature.signin.terms

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.tht.tht.domain.signup.model.TermsModel
import com.tht.tht.domain.signup.usecase.FetchSignupUserUseCase
import com.tht.tht.domain.signup.usecase.FetchTermsUseCase
import com.tht.tht.domain.signup.usecase.PatchSignupDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import tht.core.ui.base.BaseStateViewModel
import tht.core.ui.base.SideEffect
import tht.core.ui.base.UiState
import tht.feature.signin.StringProvider
import javax.inject.Inject

@HiltViewModel
class TermsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val fetchSignupUserUseCase: FetchSignupUserUseCase,
    private val fetchTermsUseCase: FetchTermsUseCase,
    private val patchSignupDataUseCase: PatchSignupDataUseCase,
    private val stringProvider: StringProvider
) : BaseStateViewModel<TermsViewModel.TermsUiState, TermsViewModel.TermsSideEffect>() {

    override val _uiStateFlow: MutableStateFlow<TermsUiState> = MutableStateFlow(TermsUiState.SelectNone)

    private val phone = savedStateHandle.getStateFlow(EXTRA_PHONE_KEY, "")

    private val _termsList: MutableStateFlow<List<TermsModel>> = MutableStateFlow(emptyList())
    val termsList = _termsList.asStateFlow()

    private val termsAgreement = mutableMapOf<TermsModel, Boolean>()

    private val _dataLoading = MutableStateFlow(false)
    val dataLoading = _dataLoading.asStateFlow()

    init {
        if (phone.value.isBlank()) {
            _uiStateFlow.value = TermsUiState.InvalidatePhone(
                stringProvider.getString(StringProvider.ResId.InvalidatePhone)
            )
        } else {
            viewModelScope.launch {
                _dataLoading.value = true
                fetchTermsUseCase()
                    .onSuccess {
                        it.forEach { terms ->
                            termsAgreement[terms] = false
                        }
                        _termsList.value = it
                    }.onFailure {
                        _sideEffectFlow.emit(
                            TermsSideEffect.ShowToast(
                                stringProvider.getString(StringProvider.ResId.TermsFetchFail)
                            )
                        )
                    }

                fetchSignupUserUseCase(phone.value)
                    .onSuccess {
                        it.termsAgreement.forEach { entry ->
                            termsAgreement[entry.key] = entry.value
                        }
                        notifyTermsSelectState()
                    }.onFailure {
                        it.printStackTrace()
                        _sideEffectFlow.emit(TermsSideEffect.ShowToast(it.toString()))
                    }.also {
                        _dataLoading.value = false
                    }
            }
        }
    }

    fun termsCheckEvent(terms: TermsModel) {
        termsAgreement[terms] = !termsAgreement.getOrDefault(terms, false)
        notifyTermsSelectState()
    }

    private fun notifyTermsSelectState() {
        val agreementSet = termsAgreement.keys.filter { termsAgreement[it] == true }.toSet()
        if (agreementSet.size == termsAgreement.values.size) {
            _uiStateFlow.value = TermsUiState.SelectAll
        } else {
            _uiStateFlow.value = TermsUiState.Select(agreementSet, checkRequireTerms())
        }
    }

    private fun checkRequireTerms(): Boolean {
        termsAgreement.forEach { (t, select) ->
            if (t.require && !select) return false
        }
        return true
    }

    fun termsClickEvent(terms: TermsModel) {
        postSideEffect(TermsSideEffect.NavigateTermsDetail(terms))
    }

    fun toggleAllSelect() {
        when (_uiStateFlow.value) {
            TermsUiState.SelectAll -> {
                _uiStateFlow.value = TermsUiState.SelectNone
                termsAgreement.keys.forEach {
                    termsAgreement[it] = false
                }
            }
            else -> {
                _uiStateFlow.value = TermsUiState.SelectAll
                termsAgreement.keys.forEach {
                    termsAgreement[it] = true
                }
            }
        }
    }

    fun startEvent() {
        if (!checkRequireTerms()) {
            postSideEffect(
                TermsSideEffect.ShowToast(
                    stringProvider.getString(StringProvider.ResId.RequireTermsNeedSelect)
                )
            )
            return
        }
        viewModelScope.launch {
            _dataLoading.value = true
            patchSignupDataUseCase(phone.value) {
                it.copy(termsAgreement = termsAgreement)
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
            }.also {
                _dataLoading.value = false
            }
        }
    }

    fun backEvent() {
        postSideEffect(TermsSideEffect.Back)
    }

    sealed class TermsUiState : UiState {
        object SelectNone : TermsUiState()
        data class Select(
            val selectTermsSet: Set<TermsModel>,
            val isRequireTermsAllSelect: Boolean
        ) : TermsUiState()
        object SelectAll : TermsUiState()
        data class InvalidatePhone(val message: String) : TermsUiState()
    }

    sealed class TermsSideEffect : SideEffect {
        data class ShowToast(val message: String) : TermsSideEffect()

        data class NavigateTermsDetail(val terms: TermsModel) : TermsSideEffect()

        data class NavigateNextView(val phone: String) : TermsSideEffect()

        object Back : TermsSideEffect()
    }

    companion object {
        const val EXTRA_PHONE_KEY = "extra_phone"
    }
}
