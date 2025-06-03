package tht.feature.chat.renewal

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import tht.core.navigation.renewal.MainTabRoute
import tht.feature.chat.chat.screen.ChatScreen

fun NavController.navigateToChat(navOptions: NavOptions) = navigate(MainTabRoute.Chat, navOptions)

fun NavGraphBuilder.chatScreen(
    navigateChatDetail: (Long, String) -> Unit,
    navigateMain: () -> Unit,
) {
    composable<MainTabRoute.Chat> { navBackStackEntry ->
        ChatScreen(
            navigateChatDetail = navigateChatDetail,
            navigateMain = navigateMain
        )
    }
}
