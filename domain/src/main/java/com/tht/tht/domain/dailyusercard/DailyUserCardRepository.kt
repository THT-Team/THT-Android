package com.tht.tht.domain.dailyusercard

interface DailyUserCardRepository {
    suspend fun fetchDailyUserCard(
        passedUserIdList: List<String>,
        selectTopicIdx: Int,
        size: Int
    ): DailyUserCardListModel
}
