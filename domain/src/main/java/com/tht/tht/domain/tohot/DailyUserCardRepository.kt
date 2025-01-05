package com.tht.tht.domain.tohot

interface DailyUserCardRepository {
    suspend fun fetchDailyUserCard(
        passedUserIdList: List<String>,
        lastUserDailyFallingCourserIdx: Int?,
        size: Int
    ): DailyUserCardListModel
}
