package com.tht.tht.navigation

import android.content.Context
import com.tht.tht.HomeActivity
import tht.core.navigation.HomeNavigation
import javax.inject.Inject

class HomeNavigationImpl @Inject constructor() : HomeNavigation {

    override fun navigateHome(context: Context) {
        context.startActivity(HomeActivity.newIntent(context))
    }
}
