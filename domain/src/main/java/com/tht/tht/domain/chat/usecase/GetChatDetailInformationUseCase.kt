package com.tht.tht.domain.chat.usecase

import com.tht.tht.domain.chat.repository.ChatRepository

class GetChatDetailInformationUseCase (
    private val repository: ChatRepository,
) {

    suspend operator fun invoke(roomIdx: Long) = kotlin.runCatching {
        repository.getChatDetailInformation(roomIdx)
    }
}
