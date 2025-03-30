package com.tht.tht.data.remote.datasource.chat

import com.tht.tht.data.remote.response.chat.ChatDetailInformationResponse
import com.tht.tht.data.remote.response.chat.ChatHistoryResponse
import com.tht.tht.data.remote.response.chat.ChatListResponse

interface ChatDataSource {

    suspend fun getChatList(): List<ChatListResponse>

    suspend fun getChatDetailInformation(roomIdx: Long): ChatDetailInformationResponse

    suspend fun getChatHistory(roomIdx: Long, chatIdx: String?, size: String): List<ChatHistoryResponse>
}
