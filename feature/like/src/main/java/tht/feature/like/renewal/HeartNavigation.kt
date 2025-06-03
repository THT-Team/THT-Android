package tht.feature.like.renewal

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import tht.core.navigation.renewal.MainTabRoute

fun NavController.navigateToHeart(navOptions: NavOptions) = navigate(MainTabRoute.Heart, navOptions)

fun NavGraphBuilder.heartScreen(
    navigateMain: () -> Unit,
) {
    composable<MainTabRoute.Heart> { navBackStackEntry ->
        HeartScreen(
            navigateMain = navigateMain,
        )
    }
}
