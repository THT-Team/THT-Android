package tht.core.navigation.renewal

import kotlinx.serialization.Serializable

sealed interface Route {
    @Serializable
    data object SignIn : Route

    @Serializable
    data object Setting: Route

    @Serializable
    data object AccountManager : Route

    @Serializable
    data class ChatDetail(val roomIdx: Long, val partnerName: String): Route
}

sealed interface MainTabRoute : Route {

    @Serializable
    data object ToHot : MainTabRoute

    @Serializable
    data object Heart : MainTabRoute

    @Serializable
    data object Chat : MainTabRoute

    @Serializable
    data object My : MainTabRoute
}
