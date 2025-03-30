package com.tht.tht.domain.chat.model

data class ChatDetailInformationModel(
    val chatRoomIdx: Long,
    val talkSubject: String,
    val talkIssue: String,
    val startDate: String,
    val isChatAble: Boolean
)
