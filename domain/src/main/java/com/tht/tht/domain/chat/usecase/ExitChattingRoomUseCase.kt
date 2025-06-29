package com.tht.tht.domain.chat.usecase

import com.tht.tht.domain.chat.repository.ChatRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExitChattingRoomUseCase @Inject constructor(
    private val repository: ChatRepository,
) {

    suspend operator fun invoke(roomIdx: Long) = kotlin.runCatching {
        repository.exitChattingRoom(roomIdx)
    }
}
