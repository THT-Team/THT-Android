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
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import tht.feature.tohot.StringProvider
import tht.feature.tohot.mapper.toCardUiModel
import tht.feature.tohot.mapper.toUiModel
import tht.feature.tohot.model.CardTimerUiModel
import tht.feature.tohot.model.MatchingUserUiModel
import tht.feature.tohot.model.ToHotCardUiModel
import tht.feature.tohot.model.ToHotUserUiModel
import tht.feature.tohot.model.TopicSelectUiModel
import tht.feature.tohot.model.TopicUiModel
import tht.feature.tohot.tohot.state.ToHotCardState
import tht.feature.tohot.tohot.state.ToHotLoading
import tht.feature.tohot.tohot.state.ToHotSideEffect
import tht.feature.tohot.tohot.state.ToHotState
import java.util.Stack
import javax.inject.Inject
import kotlin.time.DurationUnit
import kotlin.time.toDuration

/**
 * TODO: UseCase Test Code 작성
 * TODO: 토픽 재선택 UI Block -> clearUserCard 필요 여부 고민
 * TODO: FetchUserList 를 할 때 Topic ExpiredCheck 진행 후 조회 필요
 *  1. State에 Topic 관련 정보를 몰아넣을 객체 생성
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
        cardList = persistentListOf(),
        timer = createDefaultTimer(),
        enableTimerIdx = 0,
        dialogState = ToHotState.DialogState(),
        cardVisibleState = ToHotState.CardVisibleState(
            cardMoveAllow = false
        ),
        loading = ToHotLoading.None,
        topic = ToHotState.TopicInfo(),
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

    private val userCardLoadedIdxSet = mutableSetOf<Int>()

    private val currentUserListRange: IntRange
        get() = store.state.value.cardList.indices

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
                        val userCardList = toHotState.cards.toCardUiModel()
                        val cardState = if (!toHotState.isAvailableTopic()) {
                            ToHotCardState.NoneSelectTopic
                        } else if (userCardList.isEmpty()) {
                            ToHotCardState.NoneInitializeUser
                        } else if (autoRunToHot) {
                            ToHotCardState.Running
                        } else {
                            ToHotCardState.Enter
                        }

                        val cardList = if (cardState == ToHotCardState.NoneSelectTopic) {
                            persistentListOf(ToHotCardUiModel.Topic(toHotState.topic.toUiModel()))
                        } else {
                            userCardList.toImmutableList()
                        }

                        it.copy(
                            cardList = cardList,
                            userCardState = cardState,
                            timer = createDefaultTimer(),
                            enableTimerIdx = 0,
                            topic = ToHotState.TopicInfo(
                                currentTopic = if (toHotState.selectTopic != null) {
                                    toHotState.topic.topics.find { t ->
                                        t.key == toHotState.selectTopic?.key
                                    }?.toUiModel()
                                } else {
                                    null
                                },
                                selectTopicKey = toHotState.selectTopic?.key ?: -1,
                                topicResetTimeMill = toHotState.topicResetTimeMill
                            )
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
                                cardList = persistentListOf(),
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
        val prevUserSize = store.state.value.cardList.size
        var queryCount = 0
        intent {
            reduce { it.copy(loading = ToHotLoading.UserList) }
            while (true) {
                queryUserCard()
                if (store.state.value.cardList.size != prevUserSize) {
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
        //TODO: Topic Expire Check?
        val lastUserIdx = if (passedUserCardStack.empty()) null else passedUserCardStack.peek().idx
        fetchDailyUserCardUseCase(
            passedUserIdList = passedUserCardStack.map { it.id }.toList(),
            lastUserDailyFallingCourserIdx = lastUserIdx,
            size = CARD_SIZE
        ).unWrapTokenException()
            .onSuccess { dailyUserCardList ->
                intent {
                    reduce {
                        val cardList = store.state.value.cardList + dailyUserCardList.toCardUiModel()
                        it.copy(
                            cardList = cardList.toImmutableList(),
                            timer = createDefaultTimer()
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
        // TODO: Check Topic Expire
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
                        val cardList = store.state.value.cardList + dailyUserCardList.toCardUiModel()
                        it.copy(
                            cardList = cardList.toImmutableList(),
                            userCardState = ToHotCardState.Running,
                            enableTimerIdx = if (pagingLoading) it.enableTimerIdx else 0,
                            loading = ToHotLoading.None
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

    fun onSelectTopic(topicKey: Int) {
        intent {
            reduce {
                it.copy(
                    topic = it.topic.copy(
                        selectTopicKey = topicKey
                    )
                )
            }
        }
    }

    private fun getTopic(
        cardList: List<ToHotCardUiModel>,
        selectTopicKey: Int
    ): TopicUiModel? {
        val topicList = cardList.filterIsInstance<ToHotCardUiModel.Topic>()
        return topicList.asSequence()
            .mapNotNull { topicSelectUiModel ->
                when (topicSelectUiModel.topic) {
                    is TopicSelectUiModel.OneTopic -> {
                        topicSelectUiModel.topic.topic.takeIf { it.key == selectTopicKey }
                    }
                    is TopicSelectUiModel.TwoTopic -> {
                        listOf(
                            topicSelectUiModel.topic.topic1,
                            topicSelectUiModel.topic.topic2
                        ).firstOrNull { it.key == selectTopicKey }
                    }
                    is TopicSelectUiModel.FourTopic -> {
                        topicSelectUiModel.topic.topics.firstOrNull { it.key == selectTopicKey }
                    }
                }
            }.firstOrNull()
    }

    fun onConfirmSelectTopic() {
        val selectTopic = getTopic(
            cardList = store.state.value.cardList,
            selectTopicKey = store.state.value.topic.selectTopicKey
        )
        if (selectTopic == null || selectTopic.idx < 0) return

        intent {
            reduce { it.copy(loading = ToHotLoading.TopicSelect) }
            selectTopicUseCase(topicIdx = selectTopic.idx)
                .unWrapTokenException()
                .onSuccess {
                    when (it) {
                        true -> {
                            reduce { state ->
                                state.copy(
                                    topic = state.topic.copy(
                                        selectTopicKey = -1,
                                        currentTopic = selectTopic,
                                    ),
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
    fun onCardChange(userIdx: Int) {
        Log.d(TAG, "userChangeEvent => $userIdx")
        if (userIdx !in currentUserListRange) return
        with(store.state.value) {
            val user = when (val card = store.state.value.cardList[userIdx]) {
                is ToHotCardUiModel.Topic -> {
                    // TODO: InvalidState
                    return
                }
                is ToHotCardUiModel.User -> {
                    card.user
                }
            }
            if (!passedCardIdSet.contains(user.id)) {
                passedCardIdSet.add(user.id)
                val passUser = passedUserCardStack.push(user)
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
                    enableTimerIdx = userIdx,
                    timer = createDefaultTimer(
                        startAble = userCardLoadedIdxSet.contains(userIdx)
                    ),
                    cardVisibleState = ToHotState.CardVisibleState(
                        cardMoveAllow = passedCardCountBetweenTouch <= CARD_COUNT_ALLOW_WITHOUT_TOUCH &&
                            it.cardVisibleState.matchingFullScreenUser == null,
                        holdCard = passedCardCountBetweenTouch > CARD_COUNT_ALLOW_WITHOUT_TOUCH,
                        shakingCard = false
                    ),
                    dialogState = ToHotState.DialogState(
                        reportMenuDialogShow = false,
                        reportDialogShow = false,
                        blockDialogShow = false,
                    ),
                )
            }
        }
    }

    fun userCardLoadFinishEvent(idx: Int, result: Boolean, error: Throwable?) {
        Log.d(TAG, "userCardLoadFinishEvent => $idx, $result")
        error?.printStackTrace()
        userCardLoadedIdxSet.add(idx)
        intent {
            reduce {
                it.copy(
                    timer = createDefaultTimer(startAble = true)
                )
            }
        }
    }

    fun onTimerEnd(userIdx: Int) = with(store.state.value) {
        Log.d("Timer", "onTimerEnd => $userIdx => enableTimerIdx[$enableTimerIdx]")
        if (userIdx != enableTimerIdx) return@with
        tryScrollToNext(userIdx)
    }

    fun onTicChanged(tic: Float, userIdx: Int) = with(store.state.value) {
        Log.d("Timer", "ticChangeEvent => $tic from $userIdx => enableTimerIdx[$enableTimerIdx]")
        if (userIdx != enableTimerIdx) return
        if (userIdx !in cardList.indices) return
        if (tic <= 0) {
            onTimerEnd(userIdx)
            return
        }
        if (tic <= SHAKING_ANIMATION_START_TIC) {
            intent {
                reduce {
                    it.copy(
                        cardVisibleState = it.cardVisibleState.copy(
                            shakingCard = true
                        )
                    )
                }
            }
        }
    }

    fun userHeartEvent(idx: Int) {
        if (heartLoading) return
        if (store.state.value.topic.currentTopic == null) {
            // TODO: Toast
            return
        }
        if (idx !in store.state.value.cardList.indices) return
        val user = when (val card = store.state.value.cardList[idx]) {
            is ToHotCardUiModel.Topic -> {
                // TODO: InvalidState
                return
            }
            is ToHotCardUiModel.User -> {
                card.user
            }
        }
        viewModelScope.launch {
            heartLoading = true
            sendHeartUseCase(
                userUuid = user.id,
                selectDailyTopicIdx = store.state.value.topic.currentTopic!!.idx
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
                    timer = createDefaultTimer(
                        timerType = CardTimerUiModel.ToHotTimer.Heart
                    ),
                    cardVisibleState = it.cardVisibleState.copy(
                        shakingCard = false
                    )
                )
            }
            postSideEffect(
                ToHotSideEffect.UserHeart(idx)
            )
        }
    }

    fun userDislikeEvent(idx: Int) {
        if (heartLoading) return
        if (store.state.value.topic.currentTopic == null) {
            // TODO: Toast
            return
        }
        if (idx !in store.state.value.cardList.indices) return
        val user = when (val card = store.state.value.cardList[idx]) {
            is ToHotCardUiModel.Topic -> {
                // TODO: InvalidState
                return
            }
            is ToHotCardUiModel.User -> {
                card.user
            }
        }
        viewModelScope.launch {
            heartLoading = true
            sendDislikeUseCase(
                userUuid = user.id,
                selectDailyTopicIdx = store.state.value.topic.currentTopic!!.idx
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
                    timer = createDefaultTimer(
                        timerType = CardTimerUiModel.ToHotTimer.Dislike
                    ),
                    cardVisibleState = it.cardVisibleState.copy(
                        shakingCard = false
                    )
                )
            }
            postSideEffect(
                ToHotSideEffect.UserDislike(idx)
            )
        }
    }

    fun userHeartAnimationFinishEvent(idx: Int) {
        if (idx !in store.state.value.cardList.indices) return
        val user = when (val card = store.state.value.cardList[idx]) {
            is ToHotCardUiModel.Topic -> {
                // TODO: InvalidState
                return
            }
            is ToHotCardUiModel.User -> {
                card.user
            }
        }
        intent {
            reduce { it.copy(loading = ToHotLoading.Heart) }
            val res = userHeartApiResultChanel.receive()
            reduce { it.copy(loading = ToHotLoading.None) }
            if (res != null) {
                if (res) {
                    val imageUrl = user.profileImgUrl.list.first()
                    reduce {
                        it.copy(
                            cardVisibleState = it.cardVisibleState.copy(
                                matchingFullScreenUser = MatchingUserUiModel(imageUrl, idx),
                                cardMoveAllow = false
                            )
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
                    cardVisibleState = it.cardVisibleState.copy(
                        matchingFullScreenUser = null,
                        cardMoveAllow = true
                    )
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
                    cardVisibleState = it.cardVisibleState.copy(
                        cardMoveAllow = true,
                    ),
                    dialogState = it.dialogState.copy(
                        reportMenuDialogShow = false,
                        reportDialogShow = false,
                        blockDialogShow = false
                    )
                )
            }
        }
    }

    fun reportMenuEvent() {
        intent {
            reduce {
                it.copy(
                    cardVisibleState = it.cardVisibleState.copy(
                        cardMoveAllow = false,
                    ),
                    dialogState = it.dialogState.copy(
                        reportMenuDialogShow = true
                    )
                )
            }
        }
    }

    fun reportMenuReportEvent() {
        intent {
            reduce {
                it.copy(
                    dialogState = it.dialogState.copy(
                        reportMenuDialogShow = false,
                        reportDialogShow = true
                    )
                )
            }
        }
    }

    fun reportMenuBlockEvent() {
        intent {
            reduce {
                it.copy(
                    dialogState = it.dialogState.copy(
                        reportMenuDialogShow = false,
                        blockDialogShow = true
                    )
                )
            }
        }
    }

    fun cardReportEvent(userIdx: Int, reasonIdx: Int) {
        if (userIdx !in store.state.value.cardList.indices) return
        val user = when (val card = store.state.value.cardList[userIdx]) {
            is ToHotCardUiModel.Topic -> {
                // TODO: InvalidState
                return
            }
            is ToHotCardUiModel.User -> {
                card.user
            }
        }
        intent {
            reduce { it.copy(loading = ToHotLoading.Report) }
            reportUserUseCase(
                userUuid = user.id,
                reason = store.state.value.dialogState.reportReason[reasonIdx]
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
                            cardVisibleState = it.cardVisibleState.copy(
                                fallingAnimationIdx = userIdx,
                            ),
                            dialogState = it.dialogState.copy(
                                reportMenuDialogShow = false,
                                reportDialogShow = false
                            )
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
        if (idx !in store.state.value.cardList.indices) return
        val user = when (val card = store.state.value.cardList[idx]) {
            is ToHotCardUiModel.Topic -> {
                // TODO: InvalidState
                return
            }
            is ToHotCardUiModel.User -> {
                card.user
            }
        }
        intent {
            reduce { it.copy(loading = ToHotLoading.Block) }
            blockUserUseCase(userUuid = user.id)
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
                            cardVisibleState = it.cardVisibleState.copy(
                                fallingAnimationIdx = idx,
                            ),
                            dialogState = it.dialogState.copy(
                                reportMenuDialogShow = false,
                                blockDialogShow = false
                            )
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
                    cardVisibleState = it.cardVisibleState.copy(
                        fallingAnimationIdx = -1
                    )
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
        if (userIdx !in cardList.indices) return
        intent {
            reduce {
                it.copy(
                    cardList = it.cardList.toMutableList().apply { removeAt(userIdx) }.toImmutableList(),
                    enableTimerIdx = if (enableTimerIdx >= userIdx) {
                        enableTimerIdx - 1
                    } else {
                        enableTimerIdx
                    }
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
        store.state.value.cardVisibleState.holdCard.let { holdCard ->
            intent {
                reduce {
                    it.copy(
                        cardVisibleState = it.cardVisibleState.copy(
                            cardMoveAllow = holdCard,
                            holdCard = !holdCard
                        )
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

    private fun createDefaultTimer(
        startAble: Boolean = false,
        timerType: CardTimerUiModel.ToHotTimer = CardTimerUiModel.ToHotTimer.Timer
    ): CardTimerUiModel {
        return CardTimerUiModel(
            maxTimer = MAX_TIMER_MILL.toDuration(DurationUnit.MILLISECONDS),
            initialDelay = TIMER_INITIAL_DELAY_MILL.toDuration(DurationUnit.MILLISECONDS),
            completionDelay = TIMER_COMPLETION_DELAY_MILL.toDuration(DurationUnit.MILLISECONDS),
            duration = TIMER_DURATION_MILL.toDuration(DurationUnit.MILLISECONDS),
            startAble = startAble,
            timerType = timerType
        )
    }

    companion object {
        private const val TAG = "TO_HOT"

        private const val MAX_TIMER_MILL = 5000L

        private const val TIMER_INITIAL_DELAY_MILL = 1000L

        private const val TIMER_COMPLETION_DELAY_MILL = 1000L

        private const val TIMER_DURATION_MILL = 6000L

        private const val SHAKING_ANIMATION_START_TIC = 3f

        private const val CARD_COUNT_ALLOW_WITHOUT_TOUCH = 3

        private const val CARD_SIZE = 5

        private const val QUERY_USER_SUSPEND_TIME_MILL = 5000L

        private const val QUERY_USER_COUNT = 2
    }
}
