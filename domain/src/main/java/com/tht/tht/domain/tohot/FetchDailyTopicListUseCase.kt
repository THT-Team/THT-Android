package com.tht.tht.domain.tohot

class FetchDailyTopicListUseCase(
    private val repository: DailyTopicRepository
) {
    suspend operator fun invoke(): Result<DailyTopicListModel> {
        return kotlin.runCatching {
            repository.fetchDailyTopic().also {
                repository.saveDailyTopic(it)
            }
        }
    }
}
