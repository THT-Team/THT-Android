package com.tht.tht.domain.tohot

data class DailyUserCardListModel(
    val selectTopicIdx: Int,
    val topicResetTimeMill: Long,
    val cards: List<DailyUserCardModel>
)
