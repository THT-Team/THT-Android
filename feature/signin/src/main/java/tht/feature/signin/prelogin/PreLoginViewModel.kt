package tht.feature.signin.prelogin

import androidx.lifecycle.viewModelScope
import com.tht.tht.domain.type.SignInType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import tht.core.ui.base.BaseStateViewModel
import javax.inject.Inject

@HiltViewModel
class PreLoginViewModel @Inject constructor() : BaseStateViewModel<PreLoginState, PreLoginSideEffect>() {

    override val _uiStateFlow = MutableStateFlow(PreLoginState(loading = false))
    private val isLoading: Boolean
        get() = _uiStateFlow.value.loading

    fun onCancel() {
        setUiState(PreLoginState(false))
    }

    fun onError(errorMessage: String) {
        setUiState(PreLoginState(false))
        postSideEffect(PreLoginSideEffect.ShowToast(errorMessage))
    }

    fun requestNumberLogin() {
        if (isLoading) return
        setUiState(PreLoginState(true))
        postSideEffect(PreLoginSideEffect.NavigatePhoneAuth(token = null, signInType = SignInType.NORMAL))
        setUiState(PreLoginState(false))
    }

    fun requestNaverLogin() {
        if (isLoading) return
        setUiState(PreLoginState(true))
        postSideEffect(PreLoginSideEffect.RequestNaverLogin)
    }

    fun requestKakaoLogin() {
        if (isLoading) return
        setUiState(PreLoginState(true))
        postSideEffect(PreLoginSideEffect.RequestKakaoLogin)
    }

    fun requestGoogleLogin() {
        if (isLoading) return
        postSideEffect(PreLoginSideEffect.ShowToast("UnSupport"))
    }

    fun requestSignIn(signInType: SignInType, token: String) {
        setUiState(PreLoginState(false))
        viewModelScope.launch {
            postSideEffect(PreLoginSideEffect.NavigatePhoneAuth(token = token, signInType = signInType))
        }
    }
    fun navigateInquiry() {
        postSideEffect(PreLoginSideEffect.NavigateInquiry)
    }
}
