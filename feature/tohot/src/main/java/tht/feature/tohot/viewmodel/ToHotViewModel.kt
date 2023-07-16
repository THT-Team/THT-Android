package tht.feature.tohot.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.compose_ui.common.viewmodel.Container
import com.example.compose_ui.common.viewmodel.Store
import com.example.compose_ui.common.viewmodel.intent
import com.example.compose_ui.common.viewmodel.store
import com.tht.tht.domain.dailyusercard.FetchDailyUserCardUseCase
import com.tht.tht.domain.tohot.FetchToHotStateUseCase
import com.tht.tht.domain.topic.FetchDailyTopicListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import tht.feature.tohot.StringProvider
import tht.feature.tohot.mapper.toUiModel
import tht.feature.tohot.model.CardTimerUiModel
import tht.feature.tohot.model.ImmutableListWrapper
import tht.feature.tohot.model.ToHotUserUiModel
import tht.feature.tohot.state.ToHotSideEffect
import tht.feature.tohot.state.ToHotState
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Stack
import javax.inject.Inject

/**
 * TODO: UserInfo 디자인 이상한 문제
 * TODO: Topic Remain Time 파싱, 타이머
 * TODO: API 예외 처리
 * TODO: Paging List Empty 처리
 * TODO: UseCase Test Code 작성
 */
@HiltViewModel
class ToHotViewModel @Inject constructor(
    private val fetchToHotStateUseCase: FetchToHotStateUseCase,
    private val fetchDailyTopicListUseCase: FetchDailyTopicListUseCase,
    private val fetchDailyUserCardUseCase: FetchDailyUserCardUseCase,
    private val stringProvider: StringProvider
) : ViewModel(), Container<ToHotState, ToHotSideEffect> {
    override val store: Store<ToHotState, ToHotSideEffect> =
        store(
            initialState = ToHotState(
                userList = ImmutableListWrapper(emptyList()),
                timers = ImmutableListWrapper(emptyList()),
                enableTimerIdx = 0,
                cardMoveAllow = true,
                cardLoading = false,
                topicLoading = false,
                selectTopicKey = -1,
                currentTopic = null,
                topicModalShow = false,
                topicList = ImmutableListWrapper(emptyList()),
                topicSelectRemainingTime = "00:00:00",
                topicSelectRemainingTimeMill = 0,
                hasUnReadAlarm = false
            )
        )
    private var passedUserCardStack = Stack<ToHotUserUiModel>()
    private var passedCardCountBetweenTouch = 0
    private val passedCardIdSet = mutableSetOf<String>()

    private var pagingLoading = false

    private val fetchUserListPagingResultChannel = Channel<Unit>()

    private val currentUserListRange: IntRange
        get() = store.state.value.userList.list.indices

    init {
        fetchToHotState()
    }

    private fun fetchToHotState() {
        intent {
            reduce { it.copy(topicLoading = true) }
            fetchToHotStateUseCase(
                currentTimeMill = System.currentTimeMillis(),
                size = CARD_SIZE
            ).onSuccess { toHotState ->
                reduce {
                    it.copy(
                        userList = ImmutableListWrapper(
                            store.state.value.userList.list + toHotState.cards.map { c -> c.toUiModel() }
                        ),
                        isFirstPage = toHotState.cards.isEmpty(),
                        timers = ImmutableListWrapper(
                            store.state.value.timers.list +
                                List(toHotState.cards.size) {
                                    CardTimerUiModel(
                                        maxSec = MAX_TIMER_SEC,
                                        currentSec = MAX_TIMER_SEC,
                                        destinationSec = MAX_TIMER_SEC,
                                        startAble = false
                                    )
                                }
                        ),
                        enableTimerIdx = 0,
                        topicList = ImmutableListWrapper(toHotState.topic.topics.map { t -> t.toUiModel() }),
                        topicModalShow = toHotState.needSelectTopic,
                        currentTopic = toHotState.topic.topics.find { t ->
                            t.key == toHotState.selectTopicKey
                        }?.toUiModel(),
                        topicSelectRemainingTime = "24:00:00" //TODO: state.topicResetTimeMill 로 매핑 필요
                    )
                }
            }.onFailure {
                it.printStackTrace()
            }
            reduce { it.copy(topicLoading = false) }
        }
    }

    private fun fetchTopicList() {
        viewModelScope.launch {
            clearUserCard()
            intent { reduce { it.copy(topicLoading = true) } }
            fetchDailyTopicListUseCase()
                .onSuccess { dailyTopic ->
                    intent {
                        reduce {
                            it.copy(
                                topicList = ImmutableListWrapper(dailyTopic.topics.map { t -> t.toUiModel() }),
                                topicModalShow = true,
                                topicSelectRemainingTime = "24:00:00" //TODO: 매핑 필요
                            )
                        }
                        reduce { it.copy(topicLoading = false) }
                    }
                }.onFailure {
                    it.printStackTrace()
                }
        }
    }

    fun openTopicSelectEvent() {
        startTopicRemainingTimer()
    }

    fun closeTopicSelectEvent() {
        if (::topicRemainingTimer.isInitialized) topicRemainingTimer.cancel()
    }

    /**
     * 1 초 마다 LocalDateTime 객체, State 객체를 생성 하는 문제 존재
     * 미완성
     * Topic Modal 을 두번째 부터 열때 타이머가 멈추며, fetchTopicList 가 호출되어 progress 가 돌아감
     */
    private lateinit var topicRemainingTimer: Job
    private fun startTopicRemainingTimer() {
        if (::topicRemainingTimer.isInitialized) topicRemainingTimer.cancel()
        topicRemainingTimer = viewModelScope.launch(Dispatchers.IO) {
            with(store.state.value) {
                while (isActive && topicSelectRemainingTimeMill >= 0) {
                    delay(1000)
                    val remainingString = (topicSelectRemainingTimeMill - System.currentTimeMillis()).let {
                        val date = LocalDateTime.ofInstant(Instant.ofEpochMilli(it), ZoneId.systemDefault())
                        date.format(DateTimeFormatter.ofPattern("HH:mm:ss"))
                    }
                    intent {
                        reduce {
                            it.copy(
                                topicSelectRemainingTime = remainingString,
                                topicSelectRemainingTimeMill = topicSelectRemainingTimeMill - 1000
                            )
                        }
                    }
                }

                if (topicSelectRemainingTimeMill < 0) {
                    fetchTopicList()
                }
            }
        }
    }

    private fun clearUserCard() {
        passedCardIdSet.clear()
        passedCardIdSet.clear()
        intent {
            reduce {
                it.copy(
                    userList = ImmutableListWrapper(emptyList()),
                    timers = ImmutableListWrapper(emptyList())
                )
            }
        }
    }

    /**
     * 다음 아이템 있다면 Scroll
     * 다음 아이템이 없다면 페이징 로딩 여부 체크
     * - 로딩 중 이라면 channel 을 통해 페이징 로딩이 끝나는 것을 대기
     * - 로딩 중 이지 않다면 페이징 처리가 끝났는데 다음 Item이 없으므로 List Empty 처리
     */
    private fun tryScrollToNext(currentIdx: Int) {
        viewModelScope.launch {
            when ((currentIdx + 1) in currentUserListRange) {
                true -> intent {
                    postSideEffect(
                        ToHotSideEffect.Scroll(currentIdx + 1)
                    )
                }

                else -> {
                    if (pagingLoading) {
                        intent {
                            reduce {
                                it.copy(cardLoading = true)
                            }
                        }
                        fetchUserListPagingResultChannel.receive() // 페이징 완료 대기
                        intent {
                            reduce {
                                it.copy(cardLoading = false)
                            }
                            if ((currentIdx + 1) !in currentUserListRange) {
                                return@intent
                            }
                            postSideEffect(
                                ToHotSideEffect.Scroll(currentIdx + 1)
                            )
                        }
                    } else {
                        removeAllCard()
                    }
                }
            }
        }
    }

    /**
     * 페이징 - 마지막 Index Card 에서 페이징 요청
     * - 페이징 List 가 없다면?
     * - 페이징 로딩 중에 다음 카드로 넘어 가려 한다면 - 로딩 효과 표시, 페이징 완료 후 다음 리스트 표시
     */
    private suspend fun fetchUserCard(lastUserIdx: Int? = null) {
        pagingLoading = lastUserIdx != null
        if (!pagingLoading) intent { reduce { it.copy(cardLoading = true) } }
        fetchDailyUserCardUseCase(
            passedUserIdList = passedUserCardStack.map { it.id }.toList(),
            lastUserDailyFallingCourserIdx = lastUserIdx,
            size = CARD_SIZE
        ).onSuccess { dailyUserCardList ->
            intent {
                reduce {
                    it.copy(
                        userList = ImmutableListWrapper(
                            store.state.value.userList.list + dailyUserCardList.cards.map { c -> c.toUiModel() }
                        ),
                        isFirstPage = dailyUserCardList.cards.isEmpty(),
                        timers = ImmutableListWrapper(
                            store.state.value.timers.list +
                                List(dailyUserCardList.cards.size) {
                                    CardTimerUiModel(
                                        maxSec = MAX_TIMER_SEC,
                                        currentSec = MAX_TIMER_SEC,
                                        destinationSec = MAX_TIMER_SEC,
                                        startAble = false
                                    )
                                }
                        ),
                        enableTimerIdx = if (pagingLoading) it.enableTimerIdx else 0,
                        cardLoading = false
                    )
                }
            }
        }.onFailure {
            it.printStackTrace()
        }
        if (pagingLoading) {
            pagingLoading = false
            delay(100) // state update가 pagerState에 반영되기 이전이라, delay를 통해 pagerState 반영을 대기. 더 우아한 방법은?
            fetchUserListPagingResultChannel.trySend(Unit) // receive 대기 중 이면 success, 아니면 fail
        }
    }

    fun backClickEvent(topicModalShown: Boolean) {
        if (store.state.value.currentTopic == null) return
        if (topicModalShown) {
            intent {
                reduce {
                    it.copy(
                        topicModalShow = false
                    )
                }
            }
        }
    }

    fun topicSelectEvent(topicKey: Int) {
        intent {
            reduce {
                it.copy(
                    selectTopicKey = topicKey
                )
            }
        }
    }

    fun topicSelectFinishEvent() {
        viewModelScope.launch {
            intent { reduce { it.copy(cardLoading = true) } }
            delay(500)
            intent {
                reduce {
                    it.copy(
                        topicModalShow = false,
                        currentTopic = it.topicList.list.find { t -> t.key == it.selectTopicKey }
                    )
                }
                reduce { it.copy(cardLoading = false) }
            }
            fetchToHotState()
        }
    }

    /**
     * Card List Item 이 제거 되거나 추가 되면, Index 에 변경이 일어나서 다시 호출됨
     * 중복 데이터 처리를 위해 passedCardIdSet 추가
     */
    fun userChangeEvent(userIdx: Int) {
        Log.d("ToHot", "userChangeEvent => $userIdx")
        if (userIdx !in currentUserListRange) return
        with(store.state.value) {
            if (!passedCardIdSet.contains(userList.list[userIdx].id)) {
                passedCardIdSet.add(userList.list[userIdx].id)
                val passUser = passedUserCardStack.push(userList.list[userIdx])
                passedCardCountBetweenTouch++
                if (userIdx == currentUserListRange.last) {
                    viewModelScope.launch {
                        fetchUserCard(lastUserIdx = passUser.idx)
                    }
                }
            }
        }
        intent {
            reduce {
                it.copy(
                    timers = ImmutableListWrapper(
                        it.timers.list.toMutableList().apply {
                            this[userIdx] = this[userIdx].copy(
                                maxSec = MAX_TIMER_SEC,
                                currentSec = MAX_TIMER_SEC,
                                destinationSec = MAX_TIMER_SEC - 1
                            )
                        }
                    ),
                    enableTimerIdx = userIdx,
                    cardMoveAllow = passedCardCountBetweenTouch <= CARD_COUNT_ALLOW_WITHOUT_TOUCH,
                    reportMenuDialogShow = false,
                    reportDialogShow = false,
                    blockDialogShow = false,
                    holdDialogShow = passedCardCountBetweenTouch > CARD_COUNT_ALLOW_WITHOUT_TOUCH
                )
            }
        }
    }

    fun userCardLoadFinishEvent(idx: Int, result: Boolean, error: Throwable?) {
        Log.d("TAG", "userCardLoadFinishEvent => $idx, $result")
        intent {
            reduce {
                it.copy(
                    timers = ImmutableListWrapper(
                        it.timers.list.toMutableList().apply {
                            this[idx] = this[idx].copy(
                                startAble = true
                            )
                        }
                    )
                )
            }
        }
    }

    /**
     * timer tic 이 변경될 때 호출
     * - timer 가 0이면 다음 유저 스크롤
     * - timer 가 0이 아니면 timer 를 1 감소
     */
    fun ticChangeEvent(tic: Int, userIdx: Int) = with(store.state.value) {
        Log.d("ToHot", "ticChangeEvent => $tic from $userIdx => enableTimerIdx[$enableTimerIdx]")
        if (userIdx != enableTimerIdx) return@with
        if (tic <= 0) {
            tryScrollToNext(userIdx)
            return
        }
        if (userIdx !in userList.list.indices) return
        intent {
            reduce {
                it.copy(
                    timers = ImmutableListWrapper(
                        it.timers.list.toMutableList().apply {
                            this[userIdx] = this[userIdx].copy(
                                currentSec = this[userIdx].destinationSec,
                                destinationSec = this[userIdx].destinationSec - 1
                            )
                        }
                    )
                )
            }
        }
    }

    fun likeCardEvent(idx: Int) {
        tryScrollToNext(idx)
    }

    fun unlikeCardEvent(idx: Int) {
        tryScrollToNext(idx)
    }

    fun reportDialogDismissEvent() {
        intent {
            reduce {
                it.copy(
                    cardMoveAllow = true,
                    reportMenuDialogShow = false,
                    reportDialogShow = false,
                    blockDialogShow = false
                )
            }
        }
    }

    fun reportMenuEvent() {
        intent {
            reduce {
                it.copy(
                    cardMoveAllow = false,
                    reportMenuDialogShow = true
                )
            }
        }
    }

    fun reportMenuReportEvent() {
        intent {
            reduce {
                it.copy(
                    reportMenuDialogShow = false,
                    reportDialogShow = true
                )
            }
        }
    }

    fun reportMenuBlockEvent() {
        intent {
            reduce {
                it.copy(
                    reportMenuDialogShow = false,
                    blockDialogShow = true
                )
            }
        }
    }

    fun cardReportEvent(idx: Int) {
        intent {
            postSideEffect(
                ToHotSideEffect.ToastMessage(
                    message = stringProvider.getString(
                        StringProvider.ResId.ReportSuccess
                    )
                )
            )
            reduce {
                it.copy(
                    fallingAnimationIdx = idx,
                    reportMenuDialogShow = false,
                    reportDialogShow = false
                )
            }
        }
    }

    fun cardBlockEvent(idx: Int) {
        intent {
            postSideEffect(
                ToHotSideEffect.ToastMessage(
                    message = stringProvider.getString(
                        StringProvider.ResId.BlockSuccess
                    )
                )
            )
            reduce {
                it.copy(
                    fallingAnimationIdx = idx,
                    reportMenuDialogShow = false,
                    blockDialogShow = false
                )
            }
        }
    }

    fun fallingAnimationFinish(idx: Int) {
        intent {
            reduce {
                it.copy(
                    fallingAnimationIdx = -1
                )
            }
            when ((idx + 1) in currentUserListRange) {
                true -> postSideEffect(
                    ToHotSideEffect.RemoveAfterScroll(
                        scrollIdx = idx + 1,
                        removeIdx = idx
                    )
                )

                else -> {
                    removeUserCard(idx) // 지우고
                    tryScrollToNext(idx) // 다음 Item 스크롤 시도
                }
            }
        }
    }

    private fun removeAllCard() {
        intent {
            reduce {
                it.copy(
                    userList = ImmutableListWrapper(emptyList()),
                    timers = ImmutableListWrapper(emptyList()),
                    enableTimerIdx = 0
                )
            }
        }
    }

    fun removeUserCard(userIdx: Int) = with(store.state.value) {
        if (userIdx !in userList.list.indices) return
        intent {
            reduce {
                it.copy(
                    userList = ImmutableListWrapper(
                        it.userList.list.toMutableList().apply { removeAt(userIdx) }
                    ),
                    timers = ImmutableListWrapper(
                        it.timers.list.toMutableList().apply { removeAt(userIdx) }
                    ),
                    enableTimerIdx = if (enableTimerIdx >= userIdx) {
                        enableTimerIdx - 1
                    } else {
                        enableTimerIdx
                    }
                )
            }
        }
    }

    fun topicChangeClickEvent() {
        intent {
            reduce {
                //TODO: Stop user card timer
                it.copy(
                    topicModalShow = true
                )
            }
        }
    }

    fun alarmClickEvent() {
        //TODO: Navigate Alarm Screen
    }

    fun screenTouchEvent() {
        passedCardCountBetweenTouch = 0
    }

    fun releaseHoldEvent() {
        passedCardCountBetweenTouch = 0
        intent {
            reduce {
                it.copy(
                    cardMoveAllow = true,
                    holdDialogShow = false
                )
            }
        }
    }

    companion object {
        private const val MAX_TIMER_SEC = 5

        private const val CARD_COUNT_ALLOW_WITHOUT_TOUCH = 3

        private const val CARD_SIZE = 7
    }
}
