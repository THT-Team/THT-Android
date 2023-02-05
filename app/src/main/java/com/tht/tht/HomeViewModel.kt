package com.tht.tht

import androidx.annotation.IdRes
import androidx.lifecycle.viewModelScope
import com.tht.tht.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : BaseViewModel() {

    private val _navigationItemStateFlow = MutableSharedFlow<MainNavigation?>()
    val navigationItemStateFlow: SharedFlow<MainNavigation?> = _navigationItemStateFlow

    fun changeNavigation(mainNavigation: MainNavigation) {
        viewModelScope.launch {
            _navigationItemStateFlow.emit(mainNavigation)
        }
    }
}

class MainNavigation(@IdRes val navigationMenuId: Int)
