package com.tht.tht.renewal

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import tht.core.navigation.SignupNavigation
import tht.core.navigation.renewal.MainTabRoute
import tht.core.navigation.renewal.Route
import tht.core.navigation.renewal.TopLevelDestination

@Composable
fun FallingApp(
    appState: AppState,
    signInNeedIf: Boolean,
    signupNavigation: SignupNavigation,
    modifier: Modifier = Modifier,
) {
    val currentBackStackEntry by appState.navController.currentBackStackEntryAsState()
    Scaffold(
        bottomBar = {
            val isTopLevelDestination =
                currentBackStackEntry?.destination?.route?.let {
                    listOf(
                        MainTabRoute.ToHot::class.qualifiedName,
                        MainTabRoute.Heart::class.qualifiedName,
                        MainTabRoute.Chat::class.qualifiedName,
                        MainTabRoute.My::class.qualifiedName
                    )
                        .contains(it)
                }
            if(isTopLevelDestination == true) {
                FallingBottomBar(
                    modifier = Modifier
                        .fillMaxWidth(),
                    destinations = appState.topLevelDestinations,
                    onNavigateToDestination = appState::navigateToTopLevelDestination,
                    currentDestination = currentBackStackEntry?.destination,
                )
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            Column(modifier = Modifier) {
                FallingNavHost(
                    appState = appState,
                    modifier = Modifier
                        .weight(1f)
                        .background(Color(0xFF161616)),
                    startDestination = if (signInNeedIf) Route.SignIn else MainTabRoute.ToHot,
                    signupNavigation = signupNavigation,
                )
            }
        }
    }
}

@Composable
private fun FallingBottomBar(
    destinations: List<TopLevelDestination>,
    onNavigateToDestination: (TopLevelDestination) -> Unit,
    currentDestination: NavDestination?,
    modifier: Modifier = Modifier,
) {
    Column {
        Divider(modifier = Modifier.fillMaxWidth(), color = Color(0xFF222222))
        FallingNavigationBar(
            modifier = modifier
                .then(Modifier)
        ) {
            destinations.forEach { destination ->
                val selected = currentDestination.isTopLevelDestinationInHierarchy(destination)
                FallingNavigationBarItem(
                    selected = selected,
                    iconOffRes = destination.iconOffRes,
                    iconOnRes = destination.iconOnRes,
                    label = destination.title,
                    onClick = { onNavigateToDestination(destination) },
                    modifier = Modifier
                )
            }
        }
    }
}


private fun NavDestination?.isTopLevelDestinationInHierarchy(destination: TopLevelDestination) =
    this?.hierarchy?.any {
        it.route?.contains(destination.name, true) == true
    } == true
