package tht.feature.chat.navigation

interface ChatDestination {
    val route: String
}

object Chat : ChatDestination {
    override val route: String = "chat"
}

object ChatDetail : ChatDestination {
    override val route: String = "chat-detail"
}
