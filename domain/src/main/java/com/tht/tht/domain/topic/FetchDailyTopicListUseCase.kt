package com.tht.tht.domain.topic

class FetchDailyTopicListUseCase(
    private val repository: DailyTopicRepository
) {
    suspend operator fun invoke(): Result<DailyTopicListModel> {
        return kotlin.runCatching {
            repository.fetchDailyTopic()
        }
    }
}
