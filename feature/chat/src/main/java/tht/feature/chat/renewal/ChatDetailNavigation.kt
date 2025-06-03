package tht.feature.chat.renewal

import android.content.Context
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import tht.core.navigation.renewal.MainTabRoute
import tht.core.navigation.renewal.Route
import tht.feature.chat.chat.screen.ChatDetailScreen

fun NavController.navigateToChatDetail(navOptions: NavOptions) = navigate(MainTabRoute.Chat, navOptions)

fun NavGraphBuilder.chatDetailScreen(
    onBack: () -> Unit,
) {
    composable<Route.ChatDetail> { navBackStackEntry ->
        val (roomIdx, partnerName) = navBackStackEntry.toRoute<Route.ChatDetail>()
        ChatDetailScreen(
            onBack = onBack,
            roomIdx = roomIdx,
            partnerName = partnerName,
        )
    }
}
