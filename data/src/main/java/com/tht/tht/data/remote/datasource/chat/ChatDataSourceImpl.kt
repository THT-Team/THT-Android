package com.tht.tht.data.remote.datasource.chat

import com.tht.tht.data.di.IODispatcher
import com.tht.tht.data.remote.mapper.toUnwrap
import com.tht.tht.data.remote.response.chat.ChatListResponse
import com.tht.tht.data.remote.service.chat.ChatService
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class ChatDataSourceImpl @Inject constructor(
    private val chatService: ChatService
) : ChatDataSource {

    override suspend fun getChatList(): List<ChatListResponse> {
        return chatService.getChatList().toUnwrap { it }
    }
}
