package com.tht.tht.domain.chat.usecase

import com.tht.tht.domain.chat.repository.ChatRepository

class GetChatListUseCase(
    private val repository: ChatRepository,
) {

    suspend operator fun invoke() = kotlin.runCatching {
        repository.getChatList()
    }
}
