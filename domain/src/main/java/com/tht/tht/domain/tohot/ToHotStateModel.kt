package com.tht.tht.domain.tohot

import com.tht.tht.domain.topic.DailyTopicModel

data class ToHotStateModel(
    val topicInfo: TopicInfo,
    val cards: List<ToHotCardModel>,
) {
    data class TopicInfo(
        val selectTopic: DailyTopicModel?,
        val topicResetTimeMill: Long,
    ) {
        fun isAvailableTopic(now: Long = System.currentTimeMillis()): Boolean {
            return selectTopic != null && now <= topicResetTimeMill
        }
    }
}
