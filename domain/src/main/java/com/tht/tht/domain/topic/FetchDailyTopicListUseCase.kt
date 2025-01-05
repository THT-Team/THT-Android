package com.tht.tht.domain.topic

import com.tht.tht.domain.tohot.DailyTopicListModel

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
