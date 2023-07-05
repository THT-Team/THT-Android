package com.tht.tht

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tht.tht.domain.token.token.FetchThtTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val fetchThtTokenUseCase: FetchThtTokenUseCase
) : ViewModel() {

    private val _sideEffect = MutableSharedFlow<SplashSideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    fun splashFinishEvent() {
        viewModelScope.launch {
            fetchThtTokenUseCase()
                .onSuccess {
                    _sideEffect.emit(SplashSideEffect.Home)
                }.onFailure {
                    it.printStackTrace()
                    _sideEffect.emit(SplashSideEffect.Signup)
                }
        }
    }

    fun signupSuccessEvent() {
        viewModelScope.launch {
            _sideEffect.emit(SplashSideEffect.Home)
        }
    }

    fun signupCancelEvent() {
        viewModelScope.launch {
            _sideEffect.emit(SplashSideEffect.Cancel)
        }
    }
}

sealed class SplashSideEffect {
    object Signup : SplashSideEffect()
    object Home : SplashSideEffect()
    object Cancel : SplashSideEffect()
}
