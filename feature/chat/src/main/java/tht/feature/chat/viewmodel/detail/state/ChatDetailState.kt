package tht.feature.chat.viewmodel.detail.state

sealed class ChatDetailState {
    data class ChatList(
        val isLoading: Boolean,
        val chatList: List<String> = emptyList(),
    ) : ChatDetailState()
}
