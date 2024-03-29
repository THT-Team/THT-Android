package tht.feature.signin.auth

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.tht.tht.domain.signup.usecase.CheckLoginEnableUseCase
import com.tht.tht.domain.signup.usecase.RequestAuthenticationUseCase
import com.tht.tht.domain.signup.usecase.RequestPhoneVerifyUseCase
import com.tht.tht.domain.type.SignInType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import tht.core.ui.base.BaseStateViewModel
import tht.core.ui.base.SideEffect
import tht.core.ui.base.UiState
import tht.feature.signin.BuildConfig
import tht.feature.signin.StringProvider
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class PhoneVerifyViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val requestAuthenticationUseCase: RequestAuthenticationUseCase,
    private val requestPhoneVerifyUseCase: RequestPhoneVerifyUseCase,
    private val checkLoginEnableUseCase: CheckLoginEnableUseCase,
    private val stringProvider: StringProvider
) : BaseStateViewModel<PhoneVerifyViewModel.VerifyUiState, PhoneVerifyViewModel.VerifySideEffect>() {

    override val _uiStateFlow: MutableStateFlow<VerifyUiState> = MutableStateFlow(VerifyUiState.ErrorViewHide)

    val phone = savedStateHandle.getStateFlow(EXTRA_PHONE_KEY, "")
    private val authNum = savedStateHandle.getStateFlow(EXTRA_AUTH_NUM_KEY, "")

    private val signInType: SignInType = savedStateHandle[EXTRA_SIGN_IN_TYPE_KEY] ?: SignInType.NORMAL

    private var timerJob: Job? = null
    private val _time = MutableStateFlow(DEFAULT_TIME_MILL)
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

    private var verify = Array<Char?>(VERIFY_SIZE) { null }

    init {
        if (phone.value.isBlank() || authNum.value.isBlank()) {
            postSideEffect(
                VerifySideEffect.FinishView(
                    stringProvider.getString(StringProvider.ResId.InvalidatePhone)
                )
            )
        } else {
            startTimer()
        }
    }

    private fun startTimer() {
        stopTimer()
        _time.value = DEFAULT_TIME_MILL
        timerJob = viewModelScope.launch(Dispatchers.IO) {
            while (isActive && _time.value > 0L) {
                delay(1000)
                _time.value = _time.value - 1000
            }
            delay(1000)
            _sideEffectFlow.emit(
                VerifySideEffect.ShowToast(
                    stringProvider.getString(StringProvider.ResId.AuthTimeout)
                )
            )
            resendAuth()
        }
    }

    private fun stopTimer() {
        timerJob?.cancel()
        timerJob = null
    }

    fun backgroundTouchEvent() {
        postSideEffect(VerifySideEffect.KeyboardVisible(false))
    }

    fun backEvent() {
        postSideEffect(VerifySideEffect.Back)
    }

    fun verifyInputEvent(input: Char?, idx: Int) {
        if (idx in 0 until VERIFY_SIZE) {
            verify[idx] = input
        }

        StringBuilder().let { sb ->
            verify.forEach { v ->
                if (v != null) sb.append(v)
            }
            when (sb.length != VERIFY_SIZE) {
                true -> viewModelScope.launch {
                    setUiState(VerifyUiState.ErrorViewHide)
                }
                else -> requestVerify(sb.toString())
            }
        }
    }

    private fun requestVerify(verify: String) {
        require(authNum.value.isNotBlank())
        postSideEffect(VerifySideEffect.KeyboardVisible(false))
        viewModelScope.launch {
            _dataLoading.value = true
            requestPhoneVerifyUseCase(
                apiAuthNum = authNum.value,
                phone = phone.value,
                inputAuthNum = verify,
                signInType = signInType,
                forceTrue = BuildConfig.DEBUG
            ).onSuccess {
                when (it) {
                    true -> {
                        stopTimer()
                        checkSignupState(phone.value)
                    }
                    false -> setUiState(
                        VerifyUiState.ErrorViewShow(stringProvider.getString(StringProvider.ResId.VerifyFail))
                    )
                }
            }.onFailure {
                it.printStackTrace()
                setUiState(
                    VerifyUiState.ErrorViewShow(
                        stringProvider.getString(StringProvider.ResId.VerifyFail) + "\n$it"
                    )
                )
            }.also {
                _dataLoading.value = false
            }
        }
    }

    private suspend fun checkSignupState(phone: String) {
        checkLoginEnableUseCase(phone)
            .onSuccess {
                when (it) {
                    true -> _sideEffectFlow.emit(VerifySideEffect.NavigateMainView)
                    else -> _sideEffectFlow.emit(VerifySideEffect.NavigateNextView(phone))
                }
            }.onFailure {
                it.printStackTrace()
                setUiState(
                    VerifyUiState.ErrorViewShow(
                        stringProvider.getString(StringProvider.ResId.VerifyFail) + "\n$it"
                    )
                )
            }
    }

    fun resendAuth() {
        viewModelScope.launch {
            _dataLoading.value = true
            requestAuthenticationUseCase(phone.value)
                .onSuccess {
                    _sideEffectFlow.emit(
                        VerifySideEffect.ShowToast(stringProvider.getString(StringProvider.ResId.ResendAuthSuccess))
                    )
                    savedStateHandle[EXTRA_AUTH_NUM_KEY] = it
                    startTimer()
                }.onFailure {
                    it.printStackTrace()
                    _sideEffectFlow.emit(
                        VerifySideEffect.FinishView(
                            (stringProvider.getString(StringProvider.ResId.CheckSignupStateFail) + it.message)
                        )
                    )
                }.also {
                    _dataLoading.value = false
                }
        }
    }

    sealed class VerifyUiState : UiState {
        object ErrorViewHide : VerifyUiState()
        data class ErrorViewShow(val errorMessage: String) : VerifyUiState()

        data class InvalidatePhone(val message: String) : VerifyUiState()
    }
    sealed class VerifySideEffect : SideEffect {
        object Back : VerifySideEffect()
        data class ShowToast(val message: String) : VerifySideEffect()
        data class FinishView(val message: String?) : VerifySideEffect()
        data class KeyboardVisible(val visible: Boolean) : VerifySideEffect()
        data class NavigateNextView(val phone: String) : VerifySideEffect()
        object NavigateMainView : VerifySideEffect()
    }
    companion object {
        const val EXTRA_PHONE_KEY = "extra_phone_key"

        const val EXTRA_AUTH_NUM_KEY = "extra_auth_num_key"

        const val EXTRA_SIGN_IN_TYPE_KEY = "extra_login_type_key"

        private const val VERIFY_SIZE = 6

        private const val DEFAULT_TIME_MILL: Long = 60 * 3 * 1000
    }
}
