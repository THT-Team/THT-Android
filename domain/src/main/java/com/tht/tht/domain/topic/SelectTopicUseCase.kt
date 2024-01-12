package com.tht.tht.domain.topic

/**
 * Topic 의 idx 값을 보내야 함(keywordIdx 가 아닌 그냥 idx)
 */
class SelectTopicUseCase(
    private val repository: DailyTopicRepository
) {

    suspend operator fun invoke(topicIdx: Int): Result<Boolean> {
        return kotlin.runCatching {
            repository.selectDailyTopic(topicIdx)
        }
    }
}
