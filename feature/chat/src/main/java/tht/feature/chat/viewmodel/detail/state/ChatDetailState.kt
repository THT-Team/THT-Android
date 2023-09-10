package tht.feature.chat.viewmodel.detail.state

import com.tht.tht.domain.chat.model.ChatListModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

sealed class ChatDetailState {
    data class ChatList(
        val isLoading: Boolean,
        val chatList: ImmutableList<ChatListModel> = persistentListOf(),
    ) : ChatDetailState()
}
