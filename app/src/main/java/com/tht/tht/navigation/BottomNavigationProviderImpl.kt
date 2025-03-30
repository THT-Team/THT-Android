package com.tht.tht.navigation

import android.content.Context
import com.tht.tht.HomeActivity
import tht.core.navigation.BottomNavigationProvider

class BottomNavigationProviderImpl : BottomNavigationProvider {
    override fun show(context: Context) {
        (context as HomeActivity).showBottomNav()
    }

    override fun hide(context: Context) {
        (context as HomeActivity).hideBottomNav()
    }
}
