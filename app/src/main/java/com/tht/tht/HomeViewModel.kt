package com.tht.tht

import androidx.annotation.IdRes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import tht.core.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : BaseViewModel() {

    private val _navigationItemStateFlow = MutableStateFlow(MainNavigation(NONE_SELECT_ITEM))
    val navigationItemStateFlow = _navigationItemStateFlow.asStateFlow()

    fun changeNavigation(mainNavigation: MainNavigation) {
        _navigationItemStateFlow.value = mainNavigation
    }

    companion object {
        private const val NONE_SELECT_ITEM = -1
    }
}

class MainNavigation(@IdRes val navigationMenuId: Int)
