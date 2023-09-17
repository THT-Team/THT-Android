package com.tht.tht.domain.topic

interface DailyTopicRepository {
    suspend fun fetchDailyTopic(): DailyTopicListModel

    suspend fun selectDailyTopic(
        topicIdx: Int
    ): Boolean

    suspend fun fetchDailyTopicFromLocal(): DailyTopicListModel

    suspend fun saveDailyTopic(topic: DailyTopicListModel)
}
