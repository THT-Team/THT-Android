package com.tht.tht.domain.topic

data class DailyTopicListModel(
    val topicResetTimeMill: Long,
    val topics: List<DailyTopicModel>
)
