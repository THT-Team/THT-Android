package com.tht.tht.data.repository

import com.tht.tht.data.remote.datasource.topic.DailyTopicDataSource
import com.tht.tht.data.remote.mapper.toModel
import com.tht.tht.domain.topic.DailyTopicListModel
import com.tht.tht.domain.topic.DailyTopicRepository
import javax.inject.Inject

class DailyTopicRepositoryImpl @Inject constructor(
    private val dataSource: DailyTopicDataSource
) : DailyTopicRepository {
    override suspend fun fetchDailyTopic(): DailyTopicListModel {
        return dataSource.fetchDailyTopic().toModel()
    }

    override suspend fun selectDailyTopic(topicIdx: Int): Boolean {
        return dataSource.selectDailyTopic(topicIdx)
    }
}
