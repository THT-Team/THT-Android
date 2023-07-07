package tht.feature.chat.viewmodel.detail.state

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

sealed class ChatDetailState {
    data class ChatList(
        val isLoading: Boolean,
        val chatList: ImmutableList<String> = persistentListOf(),
    ) : ChatDetailState()
}
