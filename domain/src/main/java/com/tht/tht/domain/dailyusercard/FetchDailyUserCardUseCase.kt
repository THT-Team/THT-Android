package com.tht.tht.domain.dailyusercard

//TODO: Topic Expire Check? 그리고 알잘딱깔센 List 내려주기? -> FetchToHotStateUseCase랑 차이점은?
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
