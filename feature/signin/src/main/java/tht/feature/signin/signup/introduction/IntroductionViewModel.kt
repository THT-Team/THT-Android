package tht.feature.signin.signup.introduction

import androidx.lifecycle.viewModelScope
import com.tht.tht.domain.signup.usecase.FetchSignupUserUseCase
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
class IntroductionViewModel @Inject constructor(
    private val fetchSignupUserUseCase: FetchSignupUserUseCase,
    private val patchSignupDataUseCase: PatchSignupDataUseCase,
    private val stringProvider: StringProvider
) : BaseStateViewModel<IntroductionViewModel.IntroductionUiState, IntroductionViewModel.IntroductionSideEffect>() {

    override val _uiStateFlow: MutableStateFlow<IntroductionUiState> =
        MutableStateFlow(IntroductionUiState.Empty)

    private val _inputLength = MutableStateFlow(0)
    val inputLength = _inputLength.asStateFlow()

    private val _dataLoading = MutableStateFlow(false)
    val dataLoading = _dataLoading.asStateFlow()

    fun fetchSavedData(phone: String) {
        if (phone.isBlank()) {
            _uiStateFlow.value = IntroductionUiState.InvalidPhoneNumber(
                stringProvider.getString(StringProvider.ResId.InvalidatePhone)
            )
        }
        viewModelScope.launch {
            _dataLoading.value = true
            fetchSignupUserUseCase(phone)
                .onSuccess {
                    _inputLength.value = it.introduce.length
                    _uiStateFlow.value = IntroductionUiState.ValidInput(it.introduce)
                }.onFailure {
                    _sideEffectFlow.emit(
                        IntroductionSideEffect.ShowToast(
                            stringProvider.getString(StringProvider.ResId.InvalidateSignupProcess) +
                                stringProvider.getString(StringProvider.ResId.CustomerService)
                        )
                    )
                }.also {
                    _dataLoading.value = false
                }
        }
    }

    fun textInputEvent(text: String?) {
        if (text.isNullOrBlank()) {
            _uiStateFlow.value = IntroductionUiState.Empty
            return
        }
        _inputLength.value = text.length
        _uiStateFlow.value = IntroductionUiState.ValidInput(text)
    }

    fun backgroundTouchEvent() {
        postSideEffect(IntroductionSideEffect.KeyboardVisible(false))
    }

    fun clickNextEvent(phone: String, text: String?) {
        if (phone.isBlank()) {
            _uiStateFlow.value = IntroductionUiState.InvalidPhoneNumber(
                stringProvider.getString(StringProvider.ResId.InvalidatePhone)
            )
        }
        if (text.isNullOrBlank()) {
            _inputLength.value = 0
            _uiStateFlow.value = IntroductionUiState.Empty
            return
        }
        postSideEffect(IntroductionSideEffect.KeyboardVisible(false))
        viewModelScope.launch {
            _dataLoading.value = true
            patchSignupDataUseCase(phone) {
                it.copy(introduce = text)
            }.onSuccess {
                _sideEffectFlow.emit(IntroductionSideEffect.NavigateNextView)
            }.onFailure {
                _sideEffectFlow.emit(
                    IntroductionSideEffect.ShowToast(
                        stringProvider.getString(StringProvider.ResId.SendAuthFail)
                    )
                )
            }.also {
                _dataLoading.value = false
            }
        }
    }

    sealed class IntroductionUiState : UiState {
        data class InvalidPhoneNumber(val message: String) : IntroductionUiState()
        object Empty : IntroductionUiState()
        data class ValidInput(val introduce: String) : IntroductionUiState()
    }
    sealed class IntroductionSideEffect : SideEffect {
        data class ShowToast(val message: String) : IntroductionSideEffect()
        data class KeyboardVisible(val visible: Boolean) : IntroductionSideEffect()
        object NavigateNextView : IntroductionSideEffect()
    }

    companion object {
        const val MAX_LENGTH = 200
    }
}
