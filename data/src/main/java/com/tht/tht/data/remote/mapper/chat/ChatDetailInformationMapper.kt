package com.tht.tht.data.remote.mapper.chat

import com.tht.tht.data.remote.response.chat.ChatDetailInformationResponse
import com.tht.tht.domain.chat.model.ChatDetailInformationModel

fun ChatDetailInformationResponse.toModel() = ChatDetailInformationModel(
    chatRoomIdx = chatRoomIdx,
    talkSubject = talkSubject,
    talkIssue = talkIssue,
    startDate = startDate,
    isChatAble = isChatAble
)
