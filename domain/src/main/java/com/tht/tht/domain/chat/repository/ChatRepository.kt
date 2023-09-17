package com.tht.tht.domain.chat.repository

import com.tht.tht.domain.chat.model.ChatListModel

interface ChatRepository {

    suspend fun getChatList(): List<ChatListModel>
}
