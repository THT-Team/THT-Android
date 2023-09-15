package tht.feature.chat.mapper

import com.tht.tht.domain.chat.model.ChatListModel
import tht.feature.chat.model.ChatListUiModel


fun ChatListModel.toModel() = ChatListUiModel(
    chatRoomIdx = chatRoomIdx,
    partnerName = partnerName,
    partnerProfileUrl = partnerProfileUrl,
    currentMessage = currentMessage,
    messageTime = messageTime,
)