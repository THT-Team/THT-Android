package com.tht.tht

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tht.tht.domain.token.token.FetchThtTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val fetchThtTokenUseCase: FetchThtTokenUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<SplashUiState>(SplashUiState.Splash)
    val uiState = _uiState.asStateFlow()

    fun splashFinishEvent() {
        viewModelScope.launch {
            fetchThtTokenUseCase()
                .onSuccess {
                    _uiState.value = SplashUiState.Home
                }.onFailure {
                    it.printStackTrace()
                    _uiState.value = SplashUiState.Signup
                }
        }
    }

    fun signupSuccessEvent() {
        _uiState.value = SplashUiState.Home
    }
}

sealed class SplashUiState {
    object Splash : SplashUiState()
    object Signup : SplashUiState()
    object Home : SplashUiState()
}
