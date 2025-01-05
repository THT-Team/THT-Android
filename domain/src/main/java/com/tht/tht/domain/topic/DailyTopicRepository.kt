package com.tht.tht.domain.topic

import com.tht.tht.domain.tohot.DailyTopicListModel

interface DailyTopicRepository {
    suspend fun fetchDailyTopic(): DailyTopicListModel

    suspend fun selectDailyTopic(
        topicIdx: Int
    ): Boolean

    suspend fun fetchDailyTopicFromLocal(): DailyTopicListModel

    suspend fun saveDailyTopic(topic: DailyTopicListModel)

    suspend fun clearSavedTopic()
}
