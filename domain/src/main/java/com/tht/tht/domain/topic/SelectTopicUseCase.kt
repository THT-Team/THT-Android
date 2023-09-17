package com.tht.tht.domain.topic

class SelectTopicUseCase(
    private val repository: DailyTopicRepository
) {

    suspend operator fun invoke(topicIdx: Int): Result<Boolean> {
        return kotlin.runCatching {
            repository.selectDailyTopic(topicIdx)
        }
    }
}
