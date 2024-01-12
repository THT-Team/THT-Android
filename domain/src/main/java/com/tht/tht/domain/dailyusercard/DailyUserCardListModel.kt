package com.tht.tht.domain.dailyusercard

data class DailyUserCardListModel(
    val selectTopicKey: Int,
    val topicResetTimeMill: Long,
    val cards: List<DailyUserCardModel>
)
