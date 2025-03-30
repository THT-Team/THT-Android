package com.tht.tht.data.repository

import com.tht.tht.data.remote.datasource.chat.ChatDataSource
import com.tht.tht.data.remote.mapper.chat.toModel
import com.tht.tht.domain.chat.model.ChatDetailInformationModel
import com.tht.tht.domain.chat.model.ChatHistoryModel
import com.tht.tht.domain.chat.model.ChatListModel
import com.tht.tht.domain.chat.repository.ChatRepository
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val chatDataSource: ChatDataSource
) : ChatRepository {

    override suspend fun getChatList(): List<ChatListModel> {
        return chatDataSource.getChatList().map { it.toModel() }
    }

    override suspend fun getChatDetailInformation(roomIdx: Long): ChatDetailInformationModel {
        return chatDataSource.getChatDetailInformation(roomIdx).toModel()
    }

    override suspend fun getChatHistory(roomIdx: Long, chatIdx: String?, size: String): List<ChatHistoryModel> {
        return chatDataSource.getChatHistory(roomIdx, chatIdx, size).map { it.toModel() }
    }
}
