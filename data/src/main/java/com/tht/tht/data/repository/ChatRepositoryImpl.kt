package com.tht.tht.data.repository

import com.tht.tht.data.di.IODispatcher
import com.tht.tht.data.remote.datasource.chat.ChatDataSource
import com.tht.tht.data.remote.mapper.chat.toModel
import com.tht.tht.domain.chat.model.ChatListModel
import com.tht.tht.domain.chat.repository.ChatRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val chatDataSource: ChatDataSource,
    @IODispatcher private val dispatcher: CoroutineDispatcher,
) : ChatRepository {

    override suspend fun getChatList(): List<ChatListModel> {
        return withContext(dispatcher) {
            chatDataSource.getChatList().map { it.toModel() }
        }
    }
}
