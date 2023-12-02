package com.tht.tht

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tht.tht.domain.token.token.CheckAndRefreshThtAccessTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val checkAndRefreshThtAccessTokenUseCase: CheckAndRefreshThtAccessTokenUseCase
) : ViewModel() {

    private val _sideEffect = MutableSharedFlow<SplashSideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    fun splashFinishEvent() {
        checkAutoLogin()
    }

    private fun checkAutoLogin() {
        viewModelScope.launch {
            launch {
                delay(500)
                _loading.value = true
            }
            checkAndRefreshThtAccessTokenUseCase()
                .onSuccess {
                    when (it) {
                        true -> _sideEffect.emit(SplashSideEffect.Home)
                        else -> _sideEffect.emit(SplashSideEffect.Signup)
                    }
                }.onFailure {
                    it.printStackTrace()
                    _sideEffect.emit(SplashSideEffect.Signup)
                }
            _loading.value = false
        }
    }
}

sealed class SplashSideEffect {
    object Signup : SplashSideEffect()
    object Home : SplashSideEffect()
}
