package com.tht.tht.data.remote.mapper.chat

import com.tht.tht.data.remote.response.chat.ChatListResponse
import com.tht.tht.domain.chat.model.ChatListModel

fun ChatListResponse.toModel() = ChatListModel(
    chatRoomIdx = chatRoomIdx,
    partnerName = partnerName,
    partnerProfileUrl = partnerProfileUrl,
    currentMessage = currentMessage,
    messageTime = messageTime
)
