package tht.feature.tohot.new

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import tht.core.navigation.SignupNavigation
import tht.core.navigation.renewal.MainTabRoute
import tht.feature.tohot.tohot.route.ToHotRoute

fun NavController.navigateToToHot(navOptions: NavOptions) = navigate(MainTabRoute.ToHot, navOptions)

fun NavGraphBuilder.toHotScreen(
    navigateToLogout: () -> Unit,
) {
    composable<MainTabRoute.ToHot> { navBackStackEntry ->
        ToHotRoute(navigateLogout = navigateToLogout)
    }
}
