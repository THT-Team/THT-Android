package com.tht.tht.domain.tohot

import com.tht.tht.domain.dailyusercard.DailyUserCardRepository
import com.tht.tht.domain.topic.DailyTopicRepository
import com.tht.tht.domain.topic.FetchDailyTopicListUseCase

/**
 *
 *  DailyTopic FetchTopicList 인 Model 을 리턴 하는 UseCase?
 *  -> 해당 Model 의 selectIdx 가 음수 라면 Topic Open
 *  1. Local Topic State Fetch
 *  2. 유효 하지 않다면 FetchTopicList
 *  3. 유효 하다면 FetchUserList
 *  4. UserList 의 리턴 값 중 selectTopicIdx 가 유효 하지 않다면 2번으로 이동
 *  TODO: 왜 매번 Remote 를 불러 오는거 같지 topicResetTimeMill 체크 ㄱㄱ
 */
class FetchToHotStateUseCase(
    private val topicRepository: DailyTopicRepository,
    private val userCardRepository: DailyUserCardRepository,
    private val fetchDailyTopicListUseCase: FetchDailyTopicListUseCase
) {
    suspend operator fun invoke(
        currentTimeMill: Long,
        size: Int = 10
    ): Result<ToHotStateModel> {
        return kotlin.runCatching {
            val userCards = userCardRepository.fetchDailyUserCard(
                passedUserIdList = emptyList(),
                lastUserDailyFallingCourserIdx = null,
                size = size
            )
            val topic = kotlin.runCatching {
                val localTopic = topicRepository.fetchDailyTopicFromLocal()
                when {
                    currentTimeMill > localTopic.topicResetTimeMill -> throw Exception("Topic Expired")
                    userCards.selectTopicKey < 0 -> throw Exception("None Select Topic")
                }
                localTopic
            }.getOrNull() ?: kotlin.run {
                fetchDailyTopicListUseCase().getOrThrow()
            }
            ToHotStateModel(
                topic = topic,
                selectTopicKey = userCards.selectTopicKey,
                topicResetTimeMill = userCards.topicResetTimeMill,
                cards = userCards.cards,
                needSelectTopic = userCards.selectTopicKey < 0
            )
        }
    }
}
