package tht.feature.tohot.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import tht.core.navigation.SignupNavigation
import tht.feature.tohot.tohot.route.ToHotRoute

@Composable
fun ToHotNavigation(signupNavigation: SignupNavigation) {
    val navController = rememberNavController()
    val context = LocalContext.current
    NavHost(
        navController = navController,
        startDestination = ToHot.route
    ) {
        addToHotNavGraph(
            navigateIntro = {
                signupNavigation::navigatePreLogin.invoke(context)
            }
        )
    }
}

private fun NavGraphBuilder.addToHotNavGraph(
    navigateIntro: () -> Unit
) {
    composable(
        route = ToHot.route
    ) {
        ToHotRoute(
            navigateLogout = navigateIntro
        )
    }
}
