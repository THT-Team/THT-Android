package com.tht.tht

import androidx.annotation.IdRes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import tht.core.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : BaseViewModel() {

    private val _navigationItemStateFlow = MutableStateFlow(MainNavigation(-1))
    val navigationItemStateFlow = _navigationItemStateFlow.asStateFlow()

    fun changeNavigation(mainNavigation: MainNavigation) {
        _navigationItemStateFlow.value = mainNavigation
    }
}

class MainNavigation(@IdRes val navigationMenuId: Int)
