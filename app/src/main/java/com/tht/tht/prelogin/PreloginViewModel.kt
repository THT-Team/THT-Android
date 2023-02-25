package com.tht.tht.prelogin

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
class PreloginViewModel @Inject constructor(): BaseStateViewModel<PreloginState, PreloginSideEffect>() {

    override val _uiStateFlow: MutableStateFlow<PreloginState> = MutableStateFlow(PreloginState.Uninitialized)

    fun requestKakaoLogin() {
        setUiState(PreloginState.Loading)
        postSideEffect(PreloginSideEffect.RequestKakaoLogin)
    }

    fun requestSignIn(signInType: SignInType) = viewModelScope.launch {
        Log.d("test", "requestSignIn: ")
        runCatching {
            setUiState(PreloginState.Loading)
            // TODO("로그인 요청 로직")
        }.onSuccess {
            Log.d("test", "로그인 요청성공")
            // TODO("로그인 성공 후 로직")
        }.onFailure { error ->
            when {
                isApiNotFoundException(error) -> {
                    postSideEffect(PreloginSideEffect.StartSignUp)
                }
                else -> {
                    val message = error.message ?: return@launch
                    postSideEffect(PreloginSideEffect.ShowToast(message))
                }
            }
        }
    }
}
