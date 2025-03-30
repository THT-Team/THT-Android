package com.tht.tht.domain.chat.usecase

import com.tht.tht.domain.chat.repository.ChatRepository

class GetChatHistoryUseCase(
    private val repository: ChatRepository,
) {

    suspend operator fun invoke(roomIdx: Long, chatIdx: String?, size: String) = kotlin.runCatching {
        repository.getChatHistory(roomIdx, chatIdx, size)
    }
}
