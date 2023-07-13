package com.tht.tht.domain.dailyusercard

class FetchDailyUserCardUseCase(
    private val repository: DailyUserCardRepository
) {
    suspend operator fun invoke(
        passedUserIdList: List<String>,
        lastUserDailyFallingCourserIdx: Int?,
        size: Int = 10
    ): Result<DailyUserCardListModel> {
        return kotlin.runCatching {
            repository.fetchDailyUserCard(
                passedUserIdList = passedUserIdList,
                lastUserDailyFallingCourserIdx = lastUserDailyFallingCourserIdx,
                size = size
            )
        }
    }
}
