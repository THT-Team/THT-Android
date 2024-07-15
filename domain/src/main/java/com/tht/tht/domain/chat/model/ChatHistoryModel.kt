package com.tht.tht.domain.chat.model

data class ChatHistoryModel(
    val chatIdx: String,
    val sender: String,
    val senderUuid: String,
    val msg: String,
    val imgUrl: String,
    val dateTime: String,
)
