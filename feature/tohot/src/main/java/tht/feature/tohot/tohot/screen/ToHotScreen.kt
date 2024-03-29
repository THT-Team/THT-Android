package tht.feature.tohot.tohot.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import tht.feature.tohot.component.card.ToHotCard
import tht.feature.tohot.component.card.ToHotEnterCard
import tht.feature.tohot.component.card.ToHotNoneNextUserCard
import tht.feature.tohot.component.card.ToHotNoneInitialUserCard
import tht.feature.tohot.component.card.ToHotErrorCard
import tht.feature.tohot.component.card.ToHotQuerySuccessCard
import tht.feature.tohot.component.toolbar.ToHotToolBar
import tht.feature.tohot.component.toolbar.ToHotToolBarContent
import tht.feature.tohot.mockUserList
import tht.feature.tohot.model.CardTimerUiModel
import tht.feature.tohot.model.ImmutableListWrapper
import tht.feature.tohot.model.ToHotUserUiModel
import tht.feature.tohot.tohot.state.ToHotCardState
import tht.feature.tohot.tohot.state.ToHotLoading
import tht.feature.tohot.tohot.state.ToHotState

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun ToHotScreen(
    modifier: Modifier = Modifier,
    toHotCardState: ToHotCardState,
    pagerState: PagerState,
    cardList: ImmutableListWrapper<ToHotUserUiModel>,
    timers: ImmutableListWrapper<CardTimerUiModel>,
    currentUserIdx: Int,
    cardMoveAllow: Boolean,
    topicIconUrl: String?,
    topicIconRes: Int?,
    topicTitle: String?,
    hasUnReadAlarm: Boolean,
    fallingAnimationTargetIdx: Int,
    isHoldCard: Boolean,
    isShakingCard: Boolean,
    onFallingAnimationFinish: (Int) -> Unit = { },
    topicSelectListener: () -> Unit = { },
    alarmClickListener: () -> Unit = { },
    pageChanged: (Int) -> Unit,
    ticChanged: (Float, Int) -> Unit,
    onLikeClick: (Int) -> Unit = { },
    onUnLikeClick: (Int) -> Unit = { },
    onReportMenuClick: () -> Unit = { },
    onEnterClick: () -> Unit = { },
    onRefreshClick: () -> Unit = { },
    onHoldDoubleTab: () -> Unit = { },
    loadFinishListener: (Int, Boolean, Throwable?) -> Unit = { _, _, _ -> }
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        ToHotToolBar {
            ToHotToolBarContent(
                topicIconUrl = topicIconUrl,
                topicIconRes = topicIconRes,
                topicTitle = topicTitle,
                hasUnReadAlarm = hasUnReadAlarm,
                topicSelectListener = topicSelectListener,
                alarmClickListener = alarmClickListener
            )
        }

        when (toHotCardState) {
            ToHotCardState.NoneSelectTopic -> ToHotEnterCard()
            ToHotCardState.Enter -> ToHotEnterCard(onClick = onEnterClick)
            ToHotCardState.NoneInitializeUser -> ToHotNoneInitialUserCard(onClick = onRefreshClick)
            ToHotCardState.NoneNextUser -> ToHotNoneNextUserCard(onClick = onRefreshClick)
            ToHotCardState.QuerySuccess -> ToHotQuerySuccessCard(onClick = onEnterClick)
            ToHotCardState.Error -> ToHotErrorCard(onClick = onRefreshClick)

            ToHotCardState.Running -> {
                VerticalPager(
                    userScrollEnabled = false,
                    pageCount = cardList.list.size,
                    state = pagerState,
                    key = { cardList.list[it].id }
                ) { idx ->
                    val card = cardList.list[idx]
                    ToHotCard(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(start = 14.dp, end = 14.dp, top = 6.dp, bottom = 14.dp),
                        imageUrls = card.profileImgUrl,
                        name = card.nickname,
                        age = card.age,
                        address = card.address,
                        interests = card.interests,
                        idealTypes = card.idealTypes,
                        introduce = card.introduce,
                        timer = timers.list[idx].timerType,
                        maxTimeSec = timers.list[idx].maxSec,
                        currentSec = timers.list[idx].currentSec,
                        destinationSec = timers.list[idx].destinationSec,
                        enable = currentUserIdx == pagerState.currentPage &&
                            timers.list[idx].startAble && cardMoveAllow,
                        fallingAnimationEnable = idx == fallingAnimationTargetIdx,
                        isHoldCard = isHoldCard,
                        isShakingCard = isShakingCard,
                        onFallingAnimationFinish = { onFallingAnimationFinish(idx) },
                        userCardClick = { },
                        onReportMenuClick = onReportMenuClick,
                        ticChanged = { ticChanged(it, idx) },
                        onLikeClick = { onLikeClick(idx) },
                        onUnLikeClick = { onUnLikeClick(idx) },
                        loadFinishListener = { s, e -> loadFinishListener(idx, s, e) },
                        onHoldDoubleTab = onHoldDoubleTab
                    )
                }
                LaunchedEffect(key1 = pagerState) {
                    snapshotFlow { pagerState.currentPage }
                        .collect { pageChanged(it) }
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
@Preview
fun ToHotScreenPreview() {
    val toHotState = ToHotState(
        userList = ImmutableListWrapper(mockUserList.toList()),
        userCardState = ToHotCardState.Running,
        timers = ImmutableListWrapper(
            Array(mockUserList.size) {
                CardTimerUiModel(
                    maxSec = 5,
                    currentSec = 5f,
                    destinationSec = 4.5f,
                    startAble = false
                )
            }.toList()
        ),
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
    ToHotScreen(
        cardList = toHotState.userList,
        toHotCardState = toHotState.userCardState,
        pagerState = rememberPagerState(),
        timers = toHotState.timers,
        currentUserIdx = toHotState.enableTimerIdx,
        cardMoveAllow = toHotState.cardMoveAllow,
        topicIconUrl = toHotState.currentTopic?.iconUrl,
        topicIconRes = toHotState.currentTopic?.iconRes,
        topicTitle = toHotState.currentTopic?.title,
        hasUnReadAlarm = toHotState.hasUnReadAlarm,
        fallingAnimationTargetIdx = toHotState.fallingAnimationIdx,
        isHoldCard = false,
        isShakingCard = false,
        onFallingAnimationFinish = { },
        topicSelectListener = { },
        alarmClickListener = { },
        pageChanged = { },
        ticChanged = { _, _ -> },
        loadFinishListener = { _, _, _ -> },
        onLikeClick = { },
        onUnLikeClick = { },
        onReportMenuClick = { },
        onRefreshClick = { }
    )
}
