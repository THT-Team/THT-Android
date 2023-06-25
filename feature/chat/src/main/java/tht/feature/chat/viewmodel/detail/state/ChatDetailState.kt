package tht.feature.chat.viewmodel.detail.state

import tht.feature.chat.viewmodel.state.ImmutableListWrapper

sealed class ChatDetailState {
    data class ChatList(
        val isLoading: Boolean,
        val chatList: ImmutableListWrapper<String> = ImmutableListWrapper(emptyList()),
    ) : ChatDetailState()
}
