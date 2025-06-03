package tht.feature.setting.renewal

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import tht.core.navigation.renewal.MainTabRoute
import tht.core.navigation.renewal.Route
import tht.feature.setting.route.SettingRoute

fun NavController.navigateToSetting(navOptions: NavOptions) = navigate(Route.Setting, navOptions)

fun NavGraphBuilder.settingScreen(
    navigateMyPage: () -> Unit,
    navigateAccountManage: () -> Unit
) {
    composable<Route.Setting> { navBackStackEntry ->
        SettingRoute(
            navigateMyPage = navigateMyPage,
            navigateAccountManage = navigateAccountManage
        )
    }
}
