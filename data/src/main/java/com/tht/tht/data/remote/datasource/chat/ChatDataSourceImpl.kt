package com.tht.tht.data.remote.datasource.chat

import com.tht.tht.data.remote.mapper.toUnwrap
import com.tht.tht.data.remote.response.chat.ChatDetailInformationResponse
import com.tht.tht.data.remote.response.chat.ChatHistoryResponse
import com.tht.tht.data.remote.response.chat.ChatListResponse
import com.tht.tht.data.remote.service.chat.ChatService
import javax.inject.Inject

class ChatDataSourceImpl @Inject constructor(
    private val chatService: ChatService
) : ChatDataSource {

    override suspend fun getChatList(): List<ChatListResponse> {
        return chatService.getChatList().toUnwrap { it }
    }

    override suspend fun getChatDetailInformation(roomIdx: Long): ChatDetailInformationResponse {
        return chatService.getChatDetailInformation(roomIdx).toUnwrap { it }
    }

    override suspend fun getChatHistory(roomIdx: Long, chatIdx: String?, size: String): List<ChatHistoryResponse> {
        return chatService.getChatHistory(roomIdx.toString(), chatIdx, size = size).toUnwrap { it }
    }
}
