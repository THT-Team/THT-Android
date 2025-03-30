package com.tht.tht.data.remote.mapper.chat

import com.tht.tht.data.remote.response.chat.ChatHistoryResponse
import com.tht.tht.domain.chat.model.ChatHistoryModel

fun ChatHistoryResponse.toModel() = ChatHistoryModel(
    chatIdx = chatIdx,
    sender = sender,
    senderUuid = senderUuid,
    msg = msg,
    imgUrl = imgUrl,
    dateTime = dateTime,
)
