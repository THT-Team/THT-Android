package com.tht.tht.data.local.dao.topic

import com.tht.tht.data.remote.response.topic.DailyTopicResponse

interface DailyTopicDao {
    fun fetchDailyTopics(): DailyTopicResponse

    fun saveDailyTopic(topic: DailyTopicResponse)

    fun clear()
}
