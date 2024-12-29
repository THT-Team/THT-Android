package com.tht.tht.domain.dailyusercard

data class DailyUserCardListModel(
    val selectTopicIdx: Int,
    val topicResetTimeMill: Long,
    val cards: List<DailyUserCardModel>
)
