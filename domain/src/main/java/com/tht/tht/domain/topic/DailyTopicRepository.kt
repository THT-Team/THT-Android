package com.tht.tht.domain.topic

interface DailyTopicRepository {
    suspend fun fetchDailyTopic(): DailyTopicListModel

    suspend fun selectDailyTopic(
        topicIdx: Int
    ): Boolean
}
