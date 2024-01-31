package tht.feature.chat.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import tht.feature.chat.chat.screen.ChatDetailScreen
import tht.feature.chat.chat.screen.ChatScreen

@Composable
fun ChatNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Chat.route
    ) {
        addChatNavGraph(navController)
    }
}

private fun NavGraphBuilder.addChatNavGraph(
    navController: NavHostController
) {
    composable(
        route = Chat.route
    ) {
        ChatScreen(
            navigateChatDetail = { navController.navigate(ChatDetail.route) }
        )
    }

    composable(
        route = ChatDetail.route
    ) {
        ChatDetailScreen()
    }
}
