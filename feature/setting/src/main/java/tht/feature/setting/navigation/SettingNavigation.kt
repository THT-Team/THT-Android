package tht.feature.setting.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import tht.core.navigation.SignupNavigation
import tht.feature.setting.route.AccountManagerRoute
import tht.feature.setting.route.MyPageRoute
import tht.feature.setting.route.SettingRoute

@Composable
fun SettingNavigation(signupNavigation: SignupNavigation) {
    val navController = rememberNavController()
    val context = LocalContext.current
    NavHost(
        navController = navController,
        startDestination = MyPage.route
    ) {
        addToHotNavGraph(
            navController = navController,
            navigateIntro = {
                signupNavigation::navigatePreLogin.invoke(context)
            }
        )
    }
}

private fun NavGraphBuilder.addToHotNavGraph(
    navController: NavHostController,
    navigateIntro: () -> Unit
) {
    composable(
        route = MyPage.route
    ) {
        MyPageRoute(
            navigateSetting = {
                navController.navigate(Setting.route)
            }
        )
    }

    composable(
        route = Setting.route
    ) {
        SettingRoute(
            navigateMyPage = {
                navController.popBackStack(
                    route = MyPage.route,
                    inclusive = false
                )
            },
            navigateAccountManage = {
                navController.navigate(AccountManager.route)
            }
        )
    }

    composable(
        route = AccountManager.route
    ) {
        AccountManagerRoute(
            onBackPressed = {
                navController.popBackStack(
                    route = Setting.route,
                    inclusive = false
                )
            },
            navigateIntro = navigateIntro
        )
    }
}
