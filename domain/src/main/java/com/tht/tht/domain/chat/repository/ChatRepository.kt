package com.tht.tht.domain.chat.repository

import com.tht.tht.domain.chat.model.ChatDetailInformationModel
import com.tht.tht.domain.chat.model.ChatHistoryModel
import com.tht.tht.domain.chat.model.ChatListModel

interface ChatRepository {

    suspend fun getChatList(): List<ChatListModel>

    suspend fun getChatDetailInformation(roomIdx: Long): ChatDetailInformationModel

    suspend fun getChatHistory(roomIdx: Long, chatIdx: String?, size: String): List<ChatHistoryModel>

    suspend fun exitChattingRoom(roomIdx: Long)
}
