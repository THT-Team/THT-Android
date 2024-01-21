package tht.feature.setting.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
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
        addToHotNavGraph(
            navigateSetting = {
                navController.navigate(Setting.route)
            }
        )
    }
}

private fun NavGraphBuilder.addToHotNavGraph(
    navigateSetting: () -> Unit
) {
    composable(
        route = MyPage.route
    ) {
        MyPageRoute(
            navigateSetting = navigateSetting
        )
    }

    composable(
        route = Setting.route
    ) {
        SettingRoute()
    }
}

