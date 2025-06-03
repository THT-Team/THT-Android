package com.tht.tht.renewal

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import kotlinx.coroutines.CoroutineScope
import tht.core.navigation.renewal.TopLevelDestination
import tht.feature.chat.renewal.navigateToChat
import tht.feature.like.renewal.navigateToHeart
import tht.feature.setting.renewal.navigateToMy
import tht.feature.setting.renewal.navigateToSetting
import tht.feature.tohot.new.navigateToToHot

@Composable
fun rememberAppState(
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navController: NavHostController = rememberNavController(),
): AppState {
    return remember(
        navController,
        coroutineScope,
    ) {
        AppState(
            navController = navController,
            coroutineScope = coroutineScope,
        )
    }
}

@Stable
class AppState(
    val navController: NavHostController,
    coroutineScope: CoroutineScope,
) {
    val topLevelDestinations: List<TopLevelDestination> = TopLevelDestination.entries

    fun navigateToTopLevelDestination(topLevelDestination: TopLevelDestination) {
        val topLevelNavOptions = navOptions {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }

        when (topLevelDestination) {
            TopLevelDestination.TOHOT -> navController.navigateToToHot(topLevelNavOptions)
            TopLevelDestination.HEART -> navController.navigateToHeart(topLevelNavOptions)
            TopLevelDestination.CHAT -> navController.navigateToChat(topLevelNavOptions)
            TopLevelDestination.MY -> navController.navigateToMy(topLevelNavOptions)
        }
    }

    fun popNearestTopLevelDestination() {
        val topLevelRoutes = TopLevelDestination
            .entries
            .reversed()
            .map(TopLevelDestination::route)

        val destination = topLevelRoutes
            .firstNotNullOfOrNull { route ->
                try {
                    navController.getBackStackEntry(route)
                } catch (e: IllegalArgumentException) {
                    null
                }
            }

        destination?.let {
            navController.popBackStack(it.destination.id, false)
        }
    }
}
