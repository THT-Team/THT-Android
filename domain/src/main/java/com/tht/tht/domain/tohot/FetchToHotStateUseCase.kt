package com.tht.tht.domain.tohot

import com.tht.tht.domain.dailyusercard.DailyUserCardRepository
import com.tht.tht.domain.topic.DailyTopicRepository
import com.tht.tht.domain.topic.FetchDailyTopicListUseCase

/**
 *  1. DailyUserCardRepository 조회
 *   - 금일 동일 토픽을 선택한 User 목록 조회
 *   - 금일 내가 선택한 주제어 idx 조회 -> 주제어 목록에서 selectTopicKey로 금일 내가 선택한 주제어 정보 확인 가능
 *
 *  2. Local에 캐싱한 주제어 목록 정보 조회
 *   - 만료되었다면 Remote에서 새로 불러옴
 *   - selectTopicKey가 음수면 Remote에서 새로 불러옴
 *  3.
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
            ).copy(
                selectTopicKey = -1 //TODO: Remove -> TestCode
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
                selectTopic = topic.topics.firstOrNull { it.key == userCards.selectTopicKey },
                topicResetTimeMill = userCards.topicResetTimeMill,
                cards = userCards.cards
            )
        }
    }
}
