package com.tht.tht.domain.tohot

import com.tht.tht.domain.dailyusercard.DailyUserCardModel
import com.tht.tht.domain.topic.DailyTopicListModel
import com.tht.tht.domain.topic.DailyTopicModel

data class ToHotStateModel(
    val topic: DailyTopicListModel,
    val selectTopic: DailyTopicModel?,
    val topicResetTimeMill: Long,
    val cards: List<DailyUserCardModel>,
) {
    val needSelectTopic: Boolean get() = selectTopic == null
}
