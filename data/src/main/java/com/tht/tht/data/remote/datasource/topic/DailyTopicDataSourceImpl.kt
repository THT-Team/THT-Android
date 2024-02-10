package com.tht.tht.data.remote.datasource.topic

import com.tht.tht.data.di.IODispatcher
import com.tht.tht.data.local.dao.topic.DailyTopicDao
import com.tht.tht.data.remote.mapper.toUnwrap
import com.tht.tht.data.remote.response.topic.DailyTopicResponse
import com.tht.tht.data.remote.service.topic.DailyTopicApiService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DailyTopicDataSourceImpl @Inject constructor(
    private val service: DailyTopicApiService,
    private val dao: DailyTopicDao,
    @IODispatcher private val dispatcher: CoroutineDispatcher
) : DailyTopicDataSource {
    override suspend fun fetchDailyTopic(): DailyTopicResponse {
        return service.fetchDailyTopic().toUnwrap { it }
    }

    override suspend fun selectDailyTopic(topicIdx: Int): Boolean {
        return service.selectDailyTopic(topicIdx).toUnwrap { true }
    }

    override suspend fun fetchDailyTopicFromLocal(): DailyTopicResponse {
        return withContext(dispatcher) {
            dao.fetchDailyTopics()
        }
    }

    override suspend fun saveDailyTopic(topic: DailyTopicResponse) {
        withContext(dispatcher) {
            dao.saveDailyTopic(topic)
        }
    }

    override suspend fun clear() {
        return withContext(dispatcher) {
            dao.clear()
        }
    }
}
