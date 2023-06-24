package tht.feature.tohot.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.compose_ui.common.viewmodel.Container
import com.example.compose_ui.common.viewmodel.Store
import com.example.compose_ui.common.viewmodel.intent
import com.example.compose_ui.common.viewmodel.store
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import tht.feature.tohot.StringProvider
import tht.feature.tohot.model.CardTimerUiModel
import tht.feature.tohot.model.ImmutableListWrapper
import tht.feature.tohot.model.ToHotUserUiModel
import tht.feature.tohot.model.topics
import tht.feature.tohot.state.ToHotSideEffect
import tht.feature.tohot.state.ToHotState
import tht.feature.tohot.userData
import tht.feature.tohot.userData2
import tht.feature.tohot.userData3
import tht.feature.tohot.userData4
import tht.feature.tohot.userData5
import tht.feature.tohot.userData6
import tht.feature.tohot.userData7
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Stack
import javax.inject.Inject

/**
 * - 손으로 드래그 중에 시간이 다 달면 예외 발생
 * - Topic Modal 이 열릴때 마다 fetchTopicList 를 호출 해서 list 를 최신화 해줘야 할지?
 * - userList 가 비어 있을 때 modal 을 열면 fetchTopicList 가 호출 되면서 toHotLogic 도 호출돼 userList 가 호출 되는 문제
 */
@HiltViewModel
class ToHotViewModel @Inject constructor(
    private val stringProvider: StringProvider
) : ViewModel(), Container<ToHotState, ToHotSideEffect> {
    private val userList = listOf(
        userData2,
        userData3,
        userData4,
        userData
    )
    private val userList2 = listOf(
        userData5,
        userData6
    )
    private val userList3 = listOf(
        userData7
    )

    override val store: Store<ToHotState, ToHotSideEffect> =
        store(
            initialState = ToHotState(
                userList = ImmutableListWrapper(emptyList()),
                timers = ImmutableListWrapper(emptyList()),
                enableTimerIdx = 0,
                cardMoveAllow = true,
                loading = false,
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
    private val passedCardIdSet = mutableSetOf<Int>()

    private var pagingable = true // 임시 개발용 변수
    private var pagingCount = 0 // 임시 개발용 변수
    private var pagingLoading = false

    private val fetchUserListPagingResultChannel = Channel<Unit>()

    private val currentUserListRange: IntRange
        get() = store.state.value.userList.list.indices

    init {
        toHotLogic()
    }

    /**
     *  UserList API 호출
     *  - Topic 이 선택 되어 있지 않다고 리턴 되면 Topic 선택 화면
     */
    private fun toHotLogic() {
        with(store.state.value) {
            when {
                topicList.list.isEmpty() -> fetchTopicList(true)

                currentTopic == null -> {
                    clearUserCard()
                    intent {
                        reduce { it.copy(topicModalShow = true) }
                    }
                }

                userList.list.isEmpty() -> viewModelScope.launch { fetchUserCard(isPaging = false) }

                else -> {}
            }
        }
    }

    private fun fetchTopicList(openTopicList: Boolean) {
        viewModelScope.launch {
            intent { reduce { it.copy(loading = true) } }
            delay(500)
            intent {
                reduce {
                    it.copy(
                        topicList = ImmutableListWrapper(topics),
                        topicModalShow = if (openTopicList) true else it.topicModalShow,
                        topicSelectRemainingTime = "24:00:00"
                    )
                }
                reduce { it.copy(loading = false) }
            }
            toHotLogic()
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
                    fetchTopicList(false)
                }
            }
        }
    }

    private fun clearUserCard() {
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
                                it.copy(loading = true)
                            }
                        }
                        fetchUserListPagingResultChannel.receive() // 페이징 완료 대기
                        intent {
                            reduce {
                                it.copy(loading = false)
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
    private suspend fun fetchUserCard(isPaging: Boolean) {
        when (isPaging) {
            true -> {
                pagingCount++
                pagingLoading = true
                if (!pagingable) {
                    //TODO: 페이징 유저가 더 없는 경우. 처리 고민
                    pagingLoading = false
                    return
                }
                if (pagingCount > 2) {
                    pagingable = false
                    pagingLoading = false
                    return
                }
                delay(1000)
                intent {
                    reduce {
                        it.copy(
                            userList = ImmutableListWrapper(
                                store.state.value.userList.list + if(pagingCount == 1) userList2 else userList3
                            ),
                            isFirstPage = userList.isEmpty(),
                            timers = ImmutableListWrapper(
                                store.state.value.timers.list +
                                    List(
                                        if (pagingCount == 1) userList2.size else userList3.size
                                    ) {
                                        CardTimerUiModel(
                                            maxSec = MAX_TIMER_SEC,
                                            currentSec = MAX_TIMER_SEC,
                                            destinationSec = MAX_TIMER_SEC,
                                            startAble = false
                                        )
                                    }
                            ),
                            loading = false
                        )
                    }
                }
                pagingLoading = false
                // PagerState 가 업데이트 됨을 인지하는 방법?
                // pagerState.canScrollForward 가 false 가 뜨네
                // 실제로 PagerState 가 업데이트 되기 전에 호출 되서 그런듯 -> Screen 이 호출 되기 전임
                // 해결 방법은?
                delay(100) // delay(1) 도 안되고 yield 도 안됨
                fetchUserListPagingResultChannel.trySend(Unit) // receive 대기 중 이면 success, 아니면 fail
            }

            else -> {
                pagingCount = 0
                pagingable = true
                intent { reduce { it.copy(loading = true) } }
                delay(500)
                intent {
                    reduce {
                        it.copy(
                            userList = ImmutableListWrapper(userList),
                            isFirstPage = userList.isEmpty(),
                            timers = ImmutableListWrapper(
                                List(userList.size) {
                                    CardTimerUiModel(
                                        maxSec = MAX_TIMER_SEC,
                                        currentSec = MAX_TIMER_SEC,
                                        destinationSec = MAX_TIMER_SEC,
                                        startAble = false
                                    )
                                }
                            ),
                            enableTimerIdx = 0,
                            loading = false
                        )
                    }
                }
            }
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

    fun topicSelectEvent(topicKey: Long) {
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
            intent { reduce { it.copy(loading = true) } }
            delay(500)
            intent {
                reduce {
                    it.copy(
                        topicModalShow = false,
                        currentTopic = it.topicList.list.find { t -> t.key == it.selectTopicKey }
                    )
                }
                reduce { it.copy(loading = false) }
            }
            clearUserCard()
            toHotLogic()
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
                passedUserCardStack.push(userList.list[userIdx])
                passedCardCountBetweenTouch++
                if (userIdx == currentUserListRange.last) {
                    viewModelScope.launch { fetchUserCard(isPaging = true) }
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
                    cardMoveAllow = passedCardCountBetweenTouch <= cardCountAllowWithoutTouch,
                    reportMenuDialogShow = false,
                    reportDialogShow = false,
                    blockDialogShow = false,
                    holdDialogShow = passedCardCountBetweenTouch > cardCountAllowWithoutTouch
                )
            }
        }
    }

    fun userCardLoadFinishEvent(idx: Int, result: Boolean?, error: Throwable?) {
        Log.d("TAG", "userCardLoadFinishEvent => $idx, $result")
        result?.let {
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

        private const val cardCountAllowWithoutTouch = 3
    }
}
