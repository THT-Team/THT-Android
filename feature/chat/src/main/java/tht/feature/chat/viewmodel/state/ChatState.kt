package tht.feature.chat.viewmodel.state

import javax.annotation.concurrent.Immutable

sealed class ChatState {
    object Empty : ChatState()
    data class ChatList(
        val isLoading: Boolean,
        val chatList: ImmutableListWrapper<String> = ImmutableListWrapper(emptyList()),
    ) : ChatState()
}

@Immutable
data class ImmutableListWrapper<T>(val list: List<T>)
