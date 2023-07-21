package com.tht.tht.data.remote.datasource.topic

import com.tht.tht.data.local.dao.topic.DailyTopicDao
import com.tht.tht.data.remote.mapper.toUnwrap
import com.tht.tht.data.remote.response.topic.DailyTopicResponse
import com.tht.tht.data.remote.service.topic.DailyTopicApiService
import javax.inject.Inject

class DailyTopicDataSourceImpl @Inject constructor(
    private val service: DailyTopicApiService,
    private val dao: DailyTopicDao
) : DailyTopicDataSource {
    override suspend fun fetchDailyTopic(): DailyTopicResponse {
        return service.fetchDailyTopic().toUnwrap { it }
    }

    override suspend fun selectDailyTopic(topicIdx: Int): Boolean {
        return service.selectDailyTopic(topicIdx).toUnwrap { true }
    }

    override suspend fun fetchDailyTopicFromLocal(): DailyTopicResponse {
        return dao.fetchDailyTopics()
    }

    override suspend fun saveDailyTopic(topic: DailyTopicResponse) {
        dao.saveDailyTopic(topic)
    }
}
