package com.tht.tht.data.remote.datasource.topic

import com.tht.tht.data.remote.response.topic.DailyTopicResponse

interface DailyTopicDataSource {
    suspend fun fetchDailyTopic(): DailyTopicResponse

    suspend fun selectDailyTopic(
        topicIdx: Int
    ): Boolean

    suspend fun fetchDailyTopicFromLocal(): DailyTopicResponse

    suspend fun saveDailyTopic(topic: DailyTopicResponse)

    suspend fun clear()
}
