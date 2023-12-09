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

    override val _uiStateFlow: MutableStateFlow<PreLoginState> = MutableStateFlow(PreLoginState.Uninitialized)

    fun requestNumberLogin() {
        postSideEffect(PreLoginSideEffect.NavigatePhoneAuth(token = null, signInType = SignInType.NORMAL))
    }

    fun requestNaverLogin() {
        setUiState(PreLoginState.Loading)
        postSideEffect(PreLoginSideEffect.RequestNaverLogin)
    }

    fun requestKakaoLogin() {
        setUiState(PreLoginState.Loading)
        postSideEffect(PreLoginSideEffect.RequestKakaoLogin)
    }

    fun requestSignIn(signInType: SignInType, token: String) = viewModelScope.launch {
        setUiState(PreLoginState.Loading)
        postSideEffect(PreLoginSideEffect.NavigatePhoneAuth(token = token, signInType = signInType))
        setUiState(PreLoginState.Uninitialized)
    }

    fun navigateInquiry() {
        postSideEffect(PreLoginSideEffect.NavigateInquiry)
    }
}
