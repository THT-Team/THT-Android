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
        userData,
        userData5,
        userData6,
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

    init {
        toHotLogic()
    }

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

                userList.list.isEmpty() -> fetchUserCard()
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

    // Topic 을 선택 했을 때 목록을 불러 오는 함수. Not Paging
    private fun fetchUserCard() {
        viewModelScope.launch {
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
                        enableTimerIdx = 0
                    )
                }
                reduce { it.copy(loading = false) }
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
        with(store.state.value) {
            if (userIdx !in userList.list.indices) return
            if (!passedCardIdSet.contains(userList.list[userIdx].id)) {
                passedCardIdSet.add(userList.list[userIdx].id)
                passedUserCardStack.push(userList.list[userIdx])
                passedCardCountBetweenTouch++
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

    fun ticChangeEvent(tic: Int, userIdx: Int) = with(store.state.value) {
        Log.d("ToHot", "ticChangeEvent => $tic from $userIdx => enableTimerIdx[$enableTimerIdx]")
        if (userIdx != enableTimerIdx) return@with
        if (tic <= 0) {
            when (userIdx in userList.list.indices) {
                true ->
                    intent {
                        if ((userIdx + 1) in userList.list.indices) {
                            postSideEffect(
                                ToHotSideEffect.Scroll(userIdx + 1)
                            )
                        } else {
                            //TODO: Paging or Remove List
                            removeAllCard()
                        }
                    }
                else -> removeUserCard(userIdx)
            }
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
        intent {
            postSideEffect(
                ToHotSideEffect.Scroll(idx + 1)
            )
        }
    }

    fun unlikeCardEvent(idx: Int) {
        intent {
            postSideEffect(
                ToHotSideEffect.Scroll(idx + 1)
            )
        }
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
            postSideEffect(
                ToHotSideEffect.RemoveAfterScroll(
                    scrollIdx = idx + 1,
                    removeIdx = idx
                )
            )
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
