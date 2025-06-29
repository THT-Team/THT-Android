package com.tht.tht.renewal

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.navOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import tht.core.navigation.NavBackStackEntryTopic
import tht.core.navigation.SignupNavigation
import tht.core.navigation.renewal.MainTabRoute
import tht.core.navigation.renewal.Route
import tht.core.navigation.resultKey
import tht.feature.chat.renewal.chatDetailScreen
import tht.feature.chat.renewal.chatScreen
import tht.feature.like.renewal.heartScreen
import tht.feature.setting.MyPageFragment
import tht.feature.setting.renewal.accountManagerScreen
import tht.feature.setting.renewal.myScreen
import tht.feature.setting.renewal.settingScreen
import tht.feature.signin.renewal.signInScreen
import tht.feature.tohot.new.navigateToToHot
import tht.feature.tohot.new.toHotScreen

@Composable
fun FallingNavHost(
    appState: AppState,
    modifier: Modifier = Modifier,
    startDestination: Route = MainTabRoute.ToHot,
    signupNavigation: SignupNavigation,
) {
    val navController = appState.navController
    val context = LocalContext.current
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        signInScreen()
        toHotScreen(navigateToLogout = { signupNavigation::navigatePreLogin.invoke(context) })
        heartScreen(
            navigateMain = {
                navController.navigateToToHot(
                    navOptions {
                        popUpTo<MainTabRoute.ToHot>() {
                            inclusive = true
                        }
                    }
                )
            }
        )
        chatScreen(
            navigateChatDetail = { roomIdx, partnerName ->
                navController.navigate(Route.ChatDetail(roomIdx, partnerName))
            },
            navigateMain = {
                navController.navigateToToHot(
                    navOptions {
                        popUpTo<MainTabRoute.ToHot>() {
                            inclusive = true
                        }
                    }
                )
            }
        )
        chatDetailScreen(
            onBack = {
                Log.d("test", "asdf $it")
                val backStackEntry = navController.previousBackStackEntry
                backStackEntry?.savedStateHandle?.set(resultKey, it)
                CoroutineScope(Dispatchers.Main).launch {
                    Log.d("test", "asdfqwerwqer $it")
                    backStackEntry?.let {
                        Log.d("test", "asdfff $it")
                        NavBackStackEntryTopic.state.emit(value = it)
                    }
                }
                navController.navigateUp()
            },
        )
        myScreen(
            navigateSetting = { navController.navigate(Route.Setting) }
        )
        settingScreen(
            navigateMyPage = { navController.popBackStack<MainTabRoute.My>(inclusive = false) },
            navigateAccountManage = { navController.navigate(Route.AccountManager) }
        )
        accountManagerScreen(
            onBackPressed = { navController.popBackStack<Route.Setting>(inclusive = false) },
            navigateIntro = { signupNavigation::navigatePreLogin.invoke(context) }
        )
    }
}
