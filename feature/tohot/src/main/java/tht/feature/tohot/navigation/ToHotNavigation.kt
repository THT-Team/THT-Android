package tht.feature.tohot.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import tht.feature.tohot.tohot.route.ToHotRoute

@Composable
fun ToHotNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = ToHot.route
    ) {
        addToHotNavGraph()
    }
}

private fun NavGraphBuilder.addToHotNavGraph() {
    composable(
        route = ToHot.route
    ) {
        ToHotRoute()
    }
}
