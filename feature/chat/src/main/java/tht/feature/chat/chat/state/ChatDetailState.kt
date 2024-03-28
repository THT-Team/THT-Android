package tht.feature.chat.chat.state

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import tht.feature.chat.model.ChatListUiModel

sealed class ChatDetailState {
    data class ChatList(
        val isLoading: Boolean,
        val chatList: ImmutableList<ChatListUiModel> = persistentListOf()
    ) : ChatDetailState()
}
