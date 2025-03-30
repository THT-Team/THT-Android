package tht.core.navigation

import android.content.Context

interface BottomNavigationProvider {
    fun show(context: Context)
    fun hide(context: Context)
}
