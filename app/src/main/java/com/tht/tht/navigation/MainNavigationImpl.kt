package com.tht.tht.navigation

import android.content.Context
import com.tht.tht.HomeActivity
import com.tht.tht.renewal.MainActivity
import tht.core.navigation.HomeNavigation
import tht.core.navigation.MainNavigation
import javax.inject.Inject

class MainNavigationImpl @Inject constructor() : MainNavigation {

    override fun navigateMain(context: Context) {
        context.startActivity(MainActivity.newIntent(context))
    }
}
