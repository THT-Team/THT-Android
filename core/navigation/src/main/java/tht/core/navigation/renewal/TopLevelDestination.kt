package tht.core.navigation.renewal

import androidx.compose.runtime.Composable
import tht.core.navigation.R

enum class TopLevelDestination(
    val title: String,
    val iconOnRes: Int,
    val iconOffRes: Int,
    val route: MainTabRoute
) {
    TOHOT(
        "투핫",
        R.drawable.ic_tohot_fill,
        R.drawable.ic_tohot,
        MainTabRoute.ToHot
    ),
    HEART(
        "하트",
        R.drawable.ic_heart_fill,
        R.drawable.ic_heart,
        MainTabRoute.Heart
    ),
    CHAT(
        "채팅",
        R.drawable.ic_chat_fill,
        R.drawable.ic_chat,
        MainTabRoute.Chat
    ),
    MY(
        "MY",
        R.drawable.ic_my_fill,
        R.drawable.ic_my,
        MainTabRoute.My
    );

    companion object {
        @Composable
        fun find(predicate: @Composable (MainTabRoute) -> Boolean): TopLevelDestination? {
            return entries.find { predicate(it.route) }
        }

        @Composable
        fun contains(predicate: @Composable (Route) -> Boolean): Boolean {
            return entries.map { it.route }.any { predicate(it) }
        }
    }
}
