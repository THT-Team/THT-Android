package tht.feature.signin.navigation

import android.content.Context
import tht.core.navigation.SignupNavigation
import tht.feature.signin.prelogin.PreloginActivity
import javax.inject.Inject

class SignupNavigationImpl @Inject constructor() : SignupNavigation {
    override fun navigatePreLogin(context: Context) {
        context.startActivity(PreloginActivity.getIntent(context))
    }
}
