package com.tht.tht.domain.user

import com.tht.tht.domain.token.repository.TokenRepository
import com.tht.tht.domain.topic.DailyTopicRepository

/**
 * SP.Editor().clear() 로 한번에 싹 clear?
 */
class LogoutUseCase(
    private val tokenRepository: TokenRepository,
    private val topicRepository: DailyTopicRepository
){
    suspend operator fun invoke(): Result<Unit> {
        return kotlin.runCatching {
            tokenRepository.clearSavedToken()
            topicRepository.clearSavedTopic()
        }
    }
}
