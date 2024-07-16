package tht.feature.chat.chat.state

import tht.feature.chat.model.ChatDetailInformationUiModel
import tht.feature.chat.model.ChatHistoryUiModel

sealed class ChatDetailState {
    data class ChatList(
        val isLoading: Boolean,
        val chatDetailInformation: ChatDetailInformationUiModel? = null,
        val chatList: List<ChatHistoryUiModel> = emptyList(),
        val chatIdx: String? = null,
        val userUuid: String? = null,
    ) : ChatDetailState()
}
