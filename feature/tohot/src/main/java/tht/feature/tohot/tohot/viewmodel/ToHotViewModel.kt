package tht.feature.tohot.tohot.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.compose_ui.common.viewmodel.Container
import com.example.compose_ui.common.viewmodel.Store
import com.example.compose_ui.common.viewmodel.intent
import com.example.compose_ui.common.viewmodel.store
import com.tht.tht.domain.dailyusercard.FetchDailyUserCardUseCase
import com.tht.tht.domain.tohot.FetchToHotStateUseCase
import com.tht.tht.domain.token.model.NeedLogoutException
import com.tht.tht.domain.topic.FetchDailyTopicListUseCase
import com.tht.tht.domain.topic.SelectTopicUseCase
import com.tht.tht.domain.user.BlockUserUseCase
import com.tht.tht.domain.user.ReportUserUseCase
import com.tht.tht.domain.user.SendDislikeUseCase
import com.tht.tht.domain.user.SendHeartUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import tht.feature.tohot.StringProvider
import tht.feature.tohot.mapper.calculateInterval
import tht.feature.tohot.mapper.toUiModel
import tht.feature.tohot.model.CardTimerUiModel
import tht.feature.tohot.model.ImmutableListWrapper
import tht.feature.tohot.model.MatchingUserUiModel
import tht.feature.tohot.model.ToHotUserUiModel
import tht.feature.tohot.tohot.state.ToHotCardState
import tht.feature.tohot.tohot.state.ToHotLoading
import tht.feature.tohot.tohot.state.ToHotSideEffect
import tht.feature.tohot.tohot.state.ToHotState
import java.util.Stack
import javax.inject.Inject

/**
 * TODO: UseCase Test Code 작성
 * TODO: 토픽 재선택 UI Block -> clearUserCard 필요 여부 고민
 */
@HiltViewModel
class ToHotViewModel @Inject constructor(
    private val fetchToHotStateUseCase: FetchToHotStateUseCase,
    private val fetchDailyTopicListUseCase: FetchDailyTopicListUseCase,
    private val selectTopicUseCase: SelectTopicUseCase,
    private val fetchDailyUserCardUseCase: FetchDailyUserCardUseCase,
    private val reportUserUseCase: ReportUserUseCase,
    private val blockUserUseCase: BlockUserUseCase,
    private val sendHeartUseCase: SendHeartUseCase,
    private val sendDislikeUseCase: SendDislikeUseCase,
    private val stringProvider: StringProvider
) : ViewModel(), Container<ToHotState, ToHotSideEffect> {
    private val initializeState get() = ToHotState(
        userList = ImmutableListWrapper(emptyList()),
        timers = ImmutableListWrapper(emptyList()),
        enableTimerIdx = 0,
        cardMoveAllow = true,
        loading = ToHotLoading.None,
        selectTopicKey = -1,
        currentTopic = null,
        topicModalShow = false,
        topicList = ImmutableListWrapper(emptyList()),
        topicResetRemainingTime = "00:00:00",
        topicResetTimeMill = 0,
        hasUnReadAlarm = false
    )
    override val store: Store<ToHotState, ToHotSideEffect> = store(initialState = initializeState)
    private var passedUserCardStack = Stack<ToHotUserUiModel>()
    private var passedCardCountBetweenTouch = 0
    private val passedCardIdSet = mutableSetOf<String>()

    private var pagingLoading = false

    private var heartLoading = false
    private val userHeartApiResultChanel = Channel<Boolean?>()
    private val userDislikeApiResultChanel = Channel<Boolean>()

    private val fetchUserListPagingResultChannel = Channel<Unit>()

    private val currentUserListRange: IntRange
        get() = store.state.value.userList.list.indices

    init {
        fetchToHotState(autoRunToHot = false)
    }

    private fun fetchToHotState(
        autoRunToHot: Boolean
    ) {
        intent {
            reduce { it.copy(loading = ToHotLoading.TopicList) }
            fetchToHotStateUseCase(
                currentTimeMill = System.currentTimeMillis(),
                size = CARD_SIZE
            ).unWrapTokenException()
                .onSuccess { toHotState ->
                    reduce {
                        val newList = toHotState.cards.map { c -> c.toUiModel() }
                        val cardState = if (toHotState.needSelectTopic) {
                            ToHotCardState.NoneSelectTopic
                        } else if (newList.isEmpty()) {
                            ToHotCardState.NoneInitializeUser
                        } else if (autoRunToHot) {
                            ToHotCardState.Running
                        } else {
                            ToHotCardState.Enter
                        }
                        it.copy(
                            userList = ImmutableListWrapper(newList),
                            userCardState = cardState,
                            timers = ImmutableListWrapper(
                                List(toHotState.cards.size) {
                                    CardTimerUiModel(
                                        maxSec = MAX_TIMER_SEC.toInt(),
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
                            topicResetRemainingTime = parseRemainingTime(toHotState.topicResetTimeMill),
                            topicResetTimeMill = toHotState.topicResetTimeMill
                        )
                    }
                }.onFailure { e ->
                    e.printStackTrace()
                    reduce {
                        it.copy(
                            userCardState = ToHotCardState.Error
                        )
                    }
                }
            reduce { it.copy(loading = ToHotLoading.None) }
        }
    }

    private fun fetchTopicList() {
        viewModelScope.launch {
            clearUserCard()
            intent { reduce { it.copy(loading = ToHotLoading.TopicList) } }
            fetchDailyTopicListUseCase()
                .unWrapTokenException()
                .onSuccess { dailyTopic ->
                    intent {
                        reduce {
                            it.copy(
                                topicList = ImmutableListWrapper(dailyTopic.topics.map { t -> t.toUiModel() }),
                                topicModalShow = true,
                                topicResetRemainingTime = parseRemainingTime(dailyTopic.topicResetTimeMill),
                                topicResetTimeMill = dailyTopic.topicResetTimeMill
                            )
                        }
                        reduce { it.copy(loading = ToHotLoading.None) }
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

    private fun calculateRemainingTimeMill(timeMill: Long): Long = timeMill - System.currentTimeMillis()
    private fun parseRemainingTime(timeMill: Long): String {
        return timeMill.calculateInterval(System.currentTimeMillis())
    }

    private fun updateRemainingTime() {
        with(store.state.value) {
            intent {
                reduce {
                    it.copy(
                        topicResetRemainingTime = parseRemainingTime(topicResetTimeMill)
                    )
                }
            }
        }
    }

    /**
     * Timer Job 을 계속 돌리고 있을까..?
     */
    private lateinit var topicRemainingTimer: Job
    private fun startTopicRemainingTimer() {
        if (::topicRemainingTimer.isInitialized) topicRemainingTimer.cancel()
        topicRemainingTimer = viewModelScope.launch(Dispatchers.IO) {
            with(store.state.value) {
                updateRemainingTime()
                while (isActive && topicResetTimeMill >= 0) {
                    delay(1000)
                    updateRemainingTime()
                }
                if (calculateRemainingTimeMill(topicResetTimeMill) < 0) {
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
                initializeState.copy(
                    topicList = it.topicList,
                    selectTopicKey = it.selectTopicKey,
                    currentTopic = it.currentTopic,
                    topicResetRemainingTime = it.topicResetRemainingTime,
                    topicResetTimeMill = it.topicResetTimeMill
                )
            }
        }
    }

    /**
     * 다음 아이템 없고, 페이징 중 이라면 페이징 대기
     * 다음 Item 존재 하면 Scroll
     * 없다면 removeAllCard -> 다음 유저가 없음 표시
     * -> 이때 passedUserStack 을 초기화 해서는 안됨
     */
    private fun tryScrollToNext(currentIdx: Int, animate: Boolean = true) {
        viewModelScope.launch {
            if ((currentIdx + 1) !in currentUserListRange && pagingLoading) {
                intent { reduce { it.copy(loading = ToHotLoading.UserList) } }
                fetchUserListPagingResultChannel.receive() // 페이징 완료 대기
                intent { reduce { it.copy(loading = ToHotLoading.None) } }
            }
            when ((currentIdx + 1) in currentUserListRange) {
                true -> intent {
                    postSideEffect(
                        ToHotSideEffect.Scroll(currentIdx + 1, animate)
                    )
                }
                else -> {
                    intent {
                        reduce {
                            it.copy(
                                userList = ImmutableListWrapper(emptyList()),
                                timers = ImmutableListWrapper(emptyList()),
                                userCardState = ToHotCardState.NoneNextUser,
                                enableTimerIdx = 0
                            )
                        }
                    }
                }
            }
        }
    }

    fun enterEvent() {
        intent {
            reduce {
                it.copy(
                    userCardState = ToHotCardState.Running
                )
            }
        }
    }

    /**
     * 20초동안 10초 간격 으로 2번 조회 시도
     */
    fun queryUserListEvent() {
        if (store.state.value.loading != ToHotLoading.None) return
        val prevUserSize = store.state.value.userList.list.size
        var queryCount = 0
        intent {
            reduce { it.copy(loading = ToHotLoading.UserList) }
            while (true) {
                queryUserCard()
                if (store.state.value.userList.list.size != prevUserSize) {
                    reduce { it.copy(userCardState = ToHotCardState.QuerySuccess) }
                    break
                }
                if (++queryCount >= QUERY_USER_COUNT) {
                    break
                }
                delay(QUERY_USER_SUSPEND_TIME_MILL)
            }
            reduce { it.copy(loading = ToHotLoading.None) }
        }
    }

    /**
     * 대기 하며 유저 조회
     */
    private suspend fun queryUserCard() {
        val lastUserIdx = if (passedUserCardStack.empty()) null else passedUserCardStack.peek().idx
        fetchDailyUserCardUseCase(
            passedUserIdList = passedUserCardStack.map { it.id }.toList(),
            lastUserDailyFallingCourserIdx = lastUserIdx,
            size = CARD_SIZE
        ).unWrapTokenException()
            .onSuccess { dailyUserCardList ->
                intent {
                    reduce {
                        it.copy(
                            userList = ImmutableListWrapper(
                                store.state.value.userList.list + dailyUserCardList.cards.map { c -> c.toUiModel() }
                            ),
                            timers = ImmutableListWrapper(
                                store.state.value.timers.list +
                                    List(dailyUserCardList.cards.size) {
                                        CardTimerUiModel(
                                            maxSec = MAX_TIMER_SEC.toInt(),
                                            currentSec = MAX_TIMER_SEC,
                                            destinationSec = MAX_TIMER_SEC,
                                            startAble = false
                                        )
                                    }
                            ),
                            topicResetRemainingTime = parseRemainingTime(dailyUserCardList.topicResetTimeMill),
                            topicResetTimeMill = dailyUserCardList.topicResetTimeMill
                        )
                    }
                }
            }.onFailure { e ->
                e.printStackTrace()
            }
    }

    /**
     * 페이징 - 마지막 Index Card 에서 페이징 요청
     */
    private suspend fun fetchNextUserCard(lastUserIdx: Int? = null) {
        pagingLoading = lastUserIdx != null
        if (!pagingLoading) intent { reduce { it.copy(loading = ToHotLoading.UserList) } }
        fetchDailyUserCardUseCase(
            passedUserIdList = passedUserCardStack.map { it.id }.toList(),
            lastUserDailyFallingCourserIdx = lastUserIdx,
            size = CARD_SIZE
        ).unWrapTokenException()
            .onSuccess { dailyUserCardList ->
                intent {
                    reduce {
                        it.copy(
                            userList = ImmutableListWrapper(
                                store.state.value.userList.list + dailyUserCardList.cards.map { c -> c.toUiModel() }
                            ),
                            userCardState = ToHotCardState.Running,
                            timers = ImmutableListWrapper(
                                store.state.value.timers.list +
                                    List(dailyUserCardList.cards.size) {
                                        CardTimerUiModel(
                                            maxSec = MAX_TIMER_SEC.toInt(),
                                            currentSec = MAX_TIMER_SEC,
                                            destinationSec = MAX_TIMER_SEC,
                                            startAble = false
                                        )
                                    }
                            ),
                            enableTimerIdx = if (pagingLoading) it.enableTimerIdx else 0,
                            loading = ToHotLoading.None,
                            topicResetRemainingTime = parseRemainingTime(dailyUserCardList.topicResetTimeMill),
                            topicResetTimeMill = dailyUserCardList.topicResetTimeMill
                        )
                    }
                }
            }.onFailure { e ->
                e.printStackTrace()
                intent {
                    reduce {
                        it.copy(
                            userCardState = ToHotCardState.Error,
                            loading = ToHotLoading.None
                        )
                    }
                }
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
        val selectTopicIdx = with(store.state.value) {
            topicList.list.find { it.key == selectTopicKey }
        }?.idx ?: -1
        if (selectTopicIdx < 0) return

        intent {
            reduce { it.copy(loading = ToHotLoading.TopicSelect) }
            selectTopicUseCase(topicIdx = selectTopicIdx)
                .unWrapTokenException()
                .onSuccess {
                    when (it) {
                        true -> {
                            reduce { state ->
                                state.copy(
                                    topicModalShow = false,
                                    currentTopic = state.topicList.list.find { t -> t.key == state.selectTopicKey },
                                    loading = ToHotLoading.None
                                )
                            }
                            fetchToHotState(autoRunToHot = true)
                        }
                        else -> postSideEffect(
                            ToHotSideEffect.ToastMessage(
                                stringProvider.getString(
                                    StringProvider.ResId.TopicSelectFail
                                )
                            )
                        )
                    }
                }.onFailure {
                    it.printStackTrace()
                    postSideEffect(
                        ToHotSideEffect.ToastMessage(
                            stringProvider.getString(
                                StringProvider.ResId.TopicSelectFail
                            ) + it.message
                        )
                    )
                }
            reduce { it.copy(loading = ToHotLoading.None) }
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
                        fetchNextUserCard(lastUserIdx = passUser.idx)
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
                                maxSec = MAX_TIMER_SEC.toInt(),
                                currentSec = MAX_TIMER_SEC,
                                destinationSec = MAX_TIMER_SEC - TIMER_INTERVAL
                            )
                        }
                    ),
                    enableTimerIdx = userIdx,
                    cardMoveAllow = passedCardCountBetweenTouch <= CARD_COUNT_ALLOW_WITHOUT_TOUCH &&
                        it.matchingFullScreenUser == null,
                    reportMenuDialogShow = false,
                    reportDialogShow = false,
                    blockDialogShow = false,
                    holdCard = passedCardCountBetweenTouch > CARD_COUNT_ALLOW_WITHOUT_TOUCH,
                    shakingCard = false
                )
            }
        }
    }

    fun userCardLoadFinishEvent(idx: Int, result: Boolean, error: Throwable?) {
        Log.d("TAG", "userCardLoadFinishEvent => $idx, $result")
        error?.printStackTrace()
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
    fun ticChangeEvent(tic: Float, userIdx: Int) = with(store.state.value) {
        Log.d("Timer", "ticChangeEvent => $tic from $userIdx => enableTimerIdx[$enableTimerIdx]")
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
                                destinationSec = this[userIdx].destinationSec - TIMER_INTERVAL
                            )
                        }
                    ),
                    shakingCard = tic <= SHAKING_ANIMATION_START_TIC
                )
            }
        }
    }

    fun userHeartEvent(idx: Int) {
        if (heartLoading || store.state.value.currentTopic == null) return
        viewModelScope.launch {
            heartLoading = true
            sendHeartUseCase(
                userUuid = store.state.value.userList.list[idx].id,
                selectDailyTopicIdx = store.state.value.currentTopic!!.idx
            ).unWrapTokenException()
                .onSuccess {
                    userHeartApiResultChanel.send(it)
                }.onFailure {
                    it.printStackTrace()
                    userHeartApiResultChanel.send(null)
                }
        }
        intent {
            reduce {
                it.copy(
                    timers = ImmutableListWrapper(
                        it.timers.list.toMutableList().apply {
                            this[idx] = this[idx].copy(timerType = CardTimerUiModel.ToHotTimer.Heart)
                        }
                    ),
                    shakingCard = false
                )
            }
            postSideEffect(
                ToHotSideEffect.UserHeart(idx)
            )
        }
    }

    fun userDislikeEvent(idx: Int) {
        if (heartLoading) return
        viewModelScope.launch {
            heartLoading = true
            sendDislikeUseCase(
                userUuid = store.state.value.userList.list[idx].id,
                selectDailyTopicIdx = store.state.value.currentTopic!!.idx
            ).unWrapTokenException()
                .onSuccess {
                    userDislikeApiResultChanel.send(true)
                }.onFailure {
                    it.printStackTrace()
                    userDislikeApiResultChanel.send(false)
                }
        }
        intent {
            reduce {
                it.copy(
                    timers = ImmutableListWrapper(
                        it.timers.list.toMutableList().apply {
                            this[idx] = this[idx].copy(timerType = CardTimerUiModel.ToHotTimer.Dislike)
                        }
                    ),
                    shakingCard = false
                )
            }
            postSideEffect(
                ToHotSideEffect.UserDislike(idx)
            )
        }
    }

    fun userHeartAnimationFinishEvent(idx: Int) {
        intent {
            reduce { it.copy(loading = ToHotLoading.Heart) }
            val res = userHeartApiResultChanel.receive()
            reduce { it.copy(loading = ToHotLoading.None) }
            if (res != null) {
                if (res) {
                    val imageUrl = store.state.value.userList.list[idx].profileImgUrl.list.first()
                    reduce {
                        it.copy(
                            matchingFullScreenUser = MatchingUserUiModel(imageUrl, idx),
                            cardMoveAllow = false
                        )
                    }
                    delay(300)
                }
                tryScrollToNext(idx, !res)
            } else {
                postSideEffect(
                    ToHotSideEffect.ToastMessage(
                        stringProvider.getString(
                            StringProvider.ResId.HeartFail
                        )
                    )
                )
            }
            heartLoading = false
        }
    }

    fun userDislikeAnimationFinishEvent(idx: Int) {
        intent {
            reduce { it.copy(loading = ToHotLoading.Dislike) }
            val res = userDislikeApiResultChanel.receive()
            reduce { it.copy(loading = ToHotLoading.None) }
            if (res) {
                tryScrollToNext(idx)
            } else {
                postSideEffect(
                    ToHotSideEffect.ToastMessage(
                        stringProvider.getString(
                            StringProvider.ResId.DislikeFail
                        )
                    )
                )
            }
            heartLoading = false
        }
    }

    fun matchingUserFullScreenDismissEvent() {
        intent {
            reduce {
                it.copy(
                    matchingFullScreenUser = null,
                    cardMoveAllow = true
                )
            }
        }
    }

    fun chatRequestEvent(idx: Int) {
        //TODO: 구현 미정
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

    fun cardReportEvent(userIdx: Int, reasonIdx: Int) {
        intent {
            reduce { it.copy(loading = ToHotLoading.Report) }
            reportUserUseCase(
                userUuid = store.state.value.userList.list[userIdx].id,
                reason = store.state.value.reportReason[reasonIdx]
            ).unWrapTokenException()
                .onSuccess {
                    postSideEffect(
                        ToHotSideEffect.ToastMessage(
                            message = stringProvider.getString(
                                StringProvider.ResId.ReportSuccess
                            )
                        )
                    )
                    reduce {
                        it.copy(
                            fallingAnimationIdx = userIdx,
                            reportMenuDialogShow = false,
                            reportDialogShow = false
                        )
                    }
                }.onFailure {
                    it.printStackTrace()
                    postSideEffect(
                        ToHotSideEffect.ToastMessage(
                            message = stringProvider.getString(
                                StringProvider.ResId.ReportFail
                            )
                        )
                    )
                }
            reduce { it.copy(loading = ToHotLoading.None) }
        }
    }

    fun cardBlockEvent(idx: Int) {
        intent {
            reduce { it.copy(loading = ToHotLoading.Block) }
            blockUserUseCase(userUuid = store.state.value.userList.list[idx].id)
                .unWrapTokenException()
                .onSuccess {
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
                }.onFailure {
                    it.printStackTrace()
                    postSideEffect(
                        ToHotSideEffect.ToastMessage(
                            message = stringProvider.getString(
                                StringProvider.ResId.BlockFail
                            )
                        )
                    )
                }
            reduce { it.copy(loading = ToHotLoading.None) }
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
        store.state.value.holdCard.let { holdCard ->
            intent {
                reduce {
                    it.copy(
                        cardMoveAllow = holdCard,
                        holdCard = !holdCard
                    )
                }
            }
        }
    }

    fun logoutEvent() {
        intent { postSideEffect(ToHotSideEffect.Logout) }
    }

    private fun <T> Result<T>.unWrapTokenException(): Result<T> {
        return this.onFailure { throwable ->
            Log.d("cwj", "unWrapTokenException => $throwable")
            when (throwable) {
                is NeedLogoutException -> {
                    intent { reduce { it.copy(loginAvailable = false) } }
                }
            }
        }
    }

    companion object {
        private const val MAX_TIMER_SEC = 5f

        private const val TIMER_INTERVAL = 1f

        private const val SHAKING_ANIMATION_START_TIC = 3f

        private const val CARD_COUNT_ALLOW_WITHOUT_TOUCH = 3

        private const val CARD_SIZE = 5

        private const val QUERY_USER_SUSPEND_TIME_MILL = 5000L

        private const val QUERY_USER_COUNT = 2
    }
}
