package tht.feature.setting.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import tht.feature.setting.route.MyPageRoute
import tht.feature.setting.route.SettingRoute

@Composable
fun SettingNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = MyPage.route
    ) {
        addToHotNavGraph(navController = navController)
    }
}

private fun NavGraphBuilder.addToHotNavGraph(
    navController: NavHostController,
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
            }
        )
    }
}

