package tht.feature.chat.viewmodel.state

import com.tht.tht.domain.chat.model.ChatListModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

sealed class ChatState {
    object Empty : ChatState()
    data class ChatList(
        val isLoading: Boolean,
        val chatList: ImmutableList<ChatListModel> = persistentListOf(),
    ) : ChatState()
}
