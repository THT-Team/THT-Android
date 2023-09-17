package com.tht.tht.domain.chat.model

data class ChatListModel(
    val chatRoomIdx: Long,
    val partnerName: String,
    val partnerProfileUrl: String,
    val currentMessage: String,
    val messageTime: String,
)
