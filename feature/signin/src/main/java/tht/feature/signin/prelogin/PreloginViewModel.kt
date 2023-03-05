package tht.feature.signin.prelogin

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.tht.tht.domain.type.SignInType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import tht.core.ui.base.BaseStateViewModel
import tht.core.ui.exception.isApiNotFoundException
import javax.inject.Inject

@HiltViewModel
class PreloginViewModel @Inject constructor() : BaseStateViewModel<PreloginState, PreloginSideEffect>() {

    override val _uiStateFlow: MutableStateFlow<PreloginState> = MutableStateFlow(PreloginState.Uninitialized)

    fun requestNumberLogin() {
        postSideEffect(PreloginSideEffect.NavigatePhoneAuth(token = null, signInType = SignInType.Normal))
    }

    fun requestKakaoLogin() {
        setUiState(PreloginState.Loading)
        postSideEffect(PreloginSideEffect.RequestKakaoLogin)
    }

    fun requestSignIn(signInType: SignInType, token: String) = viewModelScope.launch {
        setUiState(PreloginState.Loading)
        postSideEffect(PreloginSideEffect.NavigatePhoneAuth(token = token, signInType = signInType))
        setUiState(PreloginState.Uninitialized)
    }
}
