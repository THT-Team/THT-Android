package tht.feature.chat.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
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
            navigateChatDetail = { roomIdx, partnerName ->
                navController.navigate("${ChatDetail.route}/${roomIdx}/${partnerName}")
            }
        )
    }

    composable(
        route = "${ChatDetail.route}/{roomIdx}/{partnerName}",
        arguments = listOf(
            navArgument("roomIdx") {
                type = NavType.LongType
            }
        )
    ) { entry ->
        val roomIdx = entry.arguments?.getLong("roomIdx")
        val partnerName = entry.arguments?.getString("partnerName")
        if (roomIdx == null || partnerName == null) return@composable
        ChatDetailScreen(
            roomIdx = roomIdx,
            partnerName = partnerName,
            onBack = { navController.navigateUp() }
        )
    }
}
