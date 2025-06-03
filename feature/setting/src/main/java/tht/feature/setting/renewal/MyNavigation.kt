package tht.feature.setting.renewal

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import tht.core.navigation.renewal.MainTabRoute
import tht.feature.setting.route.MyPageRoute

fun NavController.navigateToMy(navOptions: NavOptions) = navigate(MainTabRoute.My, navOptions)

fun NavGraphBuilder.myScreen(
    navigateSetting: () -> Unit,
) {
    composable<MainTabRoute.My> { navBackStackEntry ->
        MyPageRoute(
            navigateSetting = navigateSetting,
        )
    }
}
