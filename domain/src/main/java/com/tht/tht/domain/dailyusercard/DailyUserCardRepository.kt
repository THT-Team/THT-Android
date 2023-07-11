package com.tht.tht.domain.dailyusercard

interface DailyUserCardRepository {
    suspend fun fetchDailyUserCard(
        passedUserIdList: List<String>,
        lastUserDailyFallingCourserIdx: Int,
        size: Int
    ): DailyUserCardListModel
}
