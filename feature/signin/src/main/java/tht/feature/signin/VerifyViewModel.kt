package tht.feature.signin

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tht.tht.domain.signup.usecase.RequestAuthenticationUseCase
import com.tht.tht.domain.signup.usecase.RequestVerifyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import tht.core.ui.base.BaseStateViewModel
import tht.core.ui.base.SideEffect
import tht.core.ui.base.UiState
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class VerifyViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val requestAuthenticationUseCase: RequestAuthenticationUseCase,
    private val requestVerifyUseCase: RequestVerifyUseCase
) : BaseStateViewModel<VerifyViewModel.VerifyUiState, VerifyViewModel.VerifySideEffect>() {
    companion object {
        const val EXTRA_PHONE_KEY = "extra_phone_key"
    }

    override val _uiStateFlow: MutableStateFlow<VerifyUiState> = MutableStateFlow(VerifyUiState.ErrorView(false, null))

    val phone = savedStateHandle.getStateFlow(EXTRA_PHONE_KEY, "")

    private val defaultTimeMill: Long = 60 * 3 * 1000
    private val _time = MutableStateFlow(defaultTimeMill)
    val time = _time.map {
        var timeMill = it
        val min = TimeUnit.MILLISECONDS.toMinutes(timeMill)
        timeMill -= min * 60 * 1000
        val sec = TimeUnit.MILLISECONDS.toSeconds(timeMill)
        timeMill -= sec * 1000
        "${"%02d".format(min)}:${"%02d".format(sec)}"
    }

    private val _dataLoading = MutableStateFlow(false)
    val dataLoading = _dataLoading.asStateFlow()

    private val verifySize = 6
    private var verify = Array<Char?>(verifySize) { null }

    private var timerJob: Job? = null

    init {
        if (phone.value.isBlank()) {
            viewModelScope.launch {
                _sideEffectFlow.emit(VerifySideEffect.FinishView("phone is blank"))
            }
        } else {
            startTimer()
        }
    }

    private fun startTimer() {
        stopTimer()
        _time.value = defaultTimeMill
        timerJob = viewModelScope.launch(Dispatchers.IO) {
            while (isActive && _time.value > 0L) {
                delay(1000)
                _time.value = _time.value - 1000
            }
            delay(1000)
            _sideEffectFlow.emit(VerifySideEffect.ShowToast("time over"))
            resendAuth()
        }
    }

    private fun stopTimer() {
        timerJob?.cancel()
        timerJob = null
    }

    fun backgroundTouchEvent() = viewModelScope.launch {
        _sideEffectFlow.emit(VerifySideEffect.KeyboardVisible(false))
    }

    fun backEvent() = viewModelScope.launch {
        _sideEffectFlow.emit(VerifySideEffect.Back)
    }

    fun verifyInputEvent(input: Char?, idx: Int) {
        if (idx in 0 until verifySize)
            verify[idx] = input

        StringBuilder().let { sb ->
            verify.forEach { v ->
                if (v != null) sb.append(v)
            }
            when (sb.length != verifySize) {
                true -> viewModelScope.launch {
                    _uiStateFlow.emit(VerifyUiState.ErrorView(false, null))
                }
                else -> requestVerify(sb.toString())
            }
        }
    }

    private fun requestVerify(verify: String) = viewModelScope.launch {
        _dataLoading.value = true
        requestVerifyUseCase(phone.value, verify)
            .onSuccess {
                when (it) {
                    true -> {
                        stopTimer()
                        _sideEffectFlow.emit(VerifySideEffect.NavigateNextView(phone.value))
                    }
                    false -> _uiStateFlow.emit(VerifyUiState.ErrorView(true, null))
                }
            }.onFailure {
                _uiStateFlow.emit(VerifyUiState.ErrorView(true, it))
            }.also {
                _dataLoading.value = false
            }
    }

    fun resendAuth() = viewModelScope.launch {
        _dataLoading.value = true
        requestAuthenticationUseCase(phone.value)
            .onSuccess {
                _sideEffectFlow.emit(VerifySideEffect.ShowToast("인증 번호 재전송"))
                startTimer()
            }.onFailure {
                _sideEffectFlow.emit(VerifySideEffect.FinishView(it.message ?: "$it"))
            }.also {
                _dataLoading.value = false
            }
    }

    sealed class VerifyUiState : UiState {
        data class ErrorView(val visible: Boolean, val cause: Throwable?) : VerifyUiState()
    }
    sealed class VerifySideEffect : SideEffect {
        object Back : VerifySideEffect()
        data class ShowToast(val message: String) : VerifySideEffect()
        data class FinishView(val message: String?) : VerifySideEffect()
        data class KeyboardVisible(val visible: Boolean) : VerifySideEffect()
        data class NavigateNextView(val phone: String) : VerifySideEffect()
    }
}
