package com.tht.tht.data.remote.datasource.chat

import com.tht.tht.data.remote.response.chat.ChatListResponse

interface ChatDataSource {

    suspend fun getChatList(): List<ChatListResponse>
}
