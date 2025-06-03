package tht.feature.setting.renewal

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import tht.core.navigation.renewal.Route
import tht.feature.setting.route.AccountManagerRoute

fun NavController.navigateToAccountManager(navOptions: NavOptions) = navigate(Route.AccountManager, navOptions)

fun NavGraphBuilder.accountManagerScreen(
    onBackPressed: () -> Unit,
    navigateIntro: () -> Unit
) {
    composable<Route.AccountManager> { navBackStackEntry ->
        AccountManagerRoute(
            onBackPressed = onBackPressed,
            navigateIntro = navigateIntro,
        )
    }
}
