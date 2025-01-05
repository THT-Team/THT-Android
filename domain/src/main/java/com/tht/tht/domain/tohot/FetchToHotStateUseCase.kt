package com.tht.tht.domain.tohot

import com.tht.tht.domain.topic.DailyTopicRepository
import com.tht.tht.domain.topic.FetchDailyTopicListUseCase

/**
 * 1. Topic 정보 조회 + 오늘 선택한 Topic 상태 확인
 *  - FetchDailyUserCardUseCase 에서 selectTopicIdx 를 확인 가능
 *  - FetchDailyTopicListUseCase 에서 DailyTopic 정보 확인 가능
 *
 * 2. SelectTopicState 에 따라서 다음 Card 데이터 리턴
 * - 유효 하다면 UserListCard
 * - 유효 하지 않다면 TopicSelectCard
 */
class FetchToHotStateUseCase(
    private val topicRepository: DailyTopicRepository,
    private val fetchDailyUserCardUseCase: FetchDailyUserCardUseCase,
    private val fetchDailyTopicListUseCase: FetchDailyTopicListUseCase
) {
    suspend operator fun invoke(
        passedUserIdList: List<String>,
        lastUserDailyFallingCourserIdx: Int?,
        now: Long = System.currentTimeMillis(),
        size: Int = 10
    ): Result<ToHotStateModel> {
        return kotlin.runCatching {
            val topicCachedFromLocal = topicRepository.fetchDailyTopicFromLocal()
            val topic = if (now > topicCachedFromLocal.topicResetTimeMill) {
                fetchDailyTopicListUseCase().getOrThrow()
            } else {
                topicCachedFromLocal
            }

            val userCards = fetchDailyUserCardUseCase.invoke(
                passedUserIdList = passedUserIdList,
                lastUserDailyFallingCourserIdx = lastUserDailyFallingCourserIdx,
                size = size
            ).getOrThrow()
//                .copy(selectTopicIdx = -1) //TODO: Remove -> TestCode

            val topicInfo = ToHotStateModel.TopicInfo(
                selectTopic = topic.topics.firstOrNull { it.idx == userCards.selectTopicIdx },
                topicResetTimeMill = userCards.topicResetTimeMill,
            )

            val cards: List<ToHotCardModel> = if (topicInfo.isAvailableTopic(now)) {
                userCards.cards
            } else {
                listOf(topic)
            }

            ToHotStateModel(
                topicInfo = topicInfo,
                cards = cards
            )
        }
    }
}
