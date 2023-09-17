package com.tht.tht.domain.tohot

import com.tht.tht.domain.dailyusercard.DailyUserCardModel
import com.tht.tht.domain.topic.DailyTopicListModel

data class ToHotStateModel(
    val topic: DailyTopicListModel,
    val topicResetTimeMill: Long,
    val selectTopicKey: Int,
    val cards: List<DailyUserCardModel>,
    val needSelectTopic: Boolean
)
