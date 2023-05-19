package tht.feature.chat.viewmodel.state

sealed class ChatState {
    object Empty : ChatState()
    data class ChatList(val isLoading: Boolean, val chatList: List<String> = emptyList()) : ChatState()
}
