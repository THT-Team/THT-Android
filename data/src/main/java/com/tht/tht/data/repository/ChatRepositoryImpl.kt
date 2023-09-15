package com.tht.tht.data.repository

import com.tht.tht.data.remote.datasource.chat.ChatDataSource
import com.tht.tht.data.remote.mapper.chat.toModel
import com.tht.tht.domain.chat.model.ChatListModel
import com.tht.tht.domain.chat.repository.ChatRepository
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val chatDataSource: ChatDataSource,
) : ChatRepository {

    override suspend fun getChatList(): List<ChatListModel> {
        return chatDataSource.getChatList().map { it.toModel() }
    }
}
