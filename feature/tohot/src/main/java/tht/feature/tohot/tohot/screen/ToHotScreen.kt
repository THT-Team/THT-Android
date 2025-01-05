package tht.feature.tohot.tohot.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import tht.feature.tohot.component.card.ToHotCard
import tht.feature.tohot.component.card.ToHotEnterCard
import tht.feature.tohot.component.card.ToHotErrorCard
import tht.feature.tohot.component.card.ToHotNoneInitialUserCard
import tht.feature.tohot.component.card.ToHotNoneNextUserCard
import tht.feature.tohot.component.card.ToHotQuerySuccessCard
import tht.feature.tohot.component.card.TopicSelectCard
import tht.feature.tohot.component.toolbar.ToHotToolBar
import tht.feature.tohot.component.toolbar.ToHotToolBarContent
import tht.feature.tohot.mockUserList
import tht.feature.tohot.model.CardTimerUiModel
import tht.feature.tohot.model.ToHotCardUiModel
import tht.feature.tohot.tohot.state.ToHotCardState
import tht.feature.tohot.tohot.state.ToHotLoading
import tht.feature.tohot.tohot.state.ToHotState
import kotlin.time.DurationUnit
import kotlin.time.toDuration

@Composable
internal fun ToHotScreen(
    modifier: Modifier = Modifier,
    toHotCardState: ToHotCardState,
    topicInfo: ToHotState.TopicInfo,
    pagerState: PagerState,
    cardList: ImmutableList<ToHotCardUiModel>,
    timer: CardTimerUiModel,
    currentUserIdx: Int,
    cardMoveAllow: Boolean,
    hasUnReadAlarm: Boolean,
    fallingAnimationTargetIdx: Int,
    isHoldCard: Boolean,
    isShakingCard: Boolean,
    onSelectTopic: (Int) -> Unit = { },
    onClickConfirm: () -> Unit = { },
    onFallingAnimationFinish: (Int) -> Unit = { },
    topicSelectListener: () -> Unit = { },
    alarmClickListener: () -> Unit = { },
    onCardChange: (Int) -> Unit,
    onTimerEnd: (Int) -> Unit,
    onTicChanged: (Float, Int) -> Unit,
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
                topicIconUrl = topicInfo.currentTopic?.iconUrl,
                topicIconRes = topicInfo.currentTopic?.iconRes,
                topicTitle = topicInfo.currentTopic?.title,
                hasUnReadAlarm = hasUnReadAlarm,
                topicSelectListener = topicSelectListener,
                alarmClickListener = alarmClickListener
            )
        }

        val cardModifier = Modifier
            .fillMaxSize()
            .padding(start = 14.dp, end = 14.dp, top = 6.dp, bottom = 14.dp)
            .clip(RoundedCornerShape(12.dp))

        when (toHotCardState) {
            ToHotCardState.Enter -> ToHotEnterCard(
                modifier = cardModifier,
                onClick = onEnterClick
            )
            ToHotCardState.NoneInitializeUser -> ToHotNoneInitialUserCard(
                modifier = cardModifier,
                onClick = onRefreshClick
            )
            ToHotCardState.QuerySuccess -> ToHotQuerySuccessCard(
                modifier = cardModifier,
                onClick = onEnterClick
            )
            ToHotCardState.Error -> ToHotErrorCard(
                modifier = cardModifier,
                onClick = onRefreshClick
            )

            ToHotCardState.NoneSelectTopic,
            ToHotCardState.Running -> {
                VerticalPager(
                    userScrollEnabled = false,
                    state = pagerState,
                    key = {
                        // List 가 업데이트 되기 이전에 PagerState.pageCount 블록이 업데이트 된 ListSize를 리턴해서 IndexOutOfBoundsException 발생
                        // 원인 파악을 아직 하지 못해서 임시 방편 처리
                        if (it in cardList.indices) {
                            cardList[it].id
                        } else {
                            it
                        }
                    }
                ) { idx ->
                    when (val card = cardList[idx]) {
                        is ToHotCardUiModel.NoneNextUser -> {
                            ToHotNoneNextUserCard(
                                modifier = cardModifier,
                                onClick = onRefreshClick
                            )
                        }
                        is ToHotCardUiModel.User -> {
                            val isCurrentCard = currentUserIdx == pagerState.currentPage &&
                                idx == currentUserIdx
                            ToHotCard(
                                modifier = cardModifier,
                                imageUrls = card.user.profileImgUrl,
                                name = card.user.nickname,
                                age = card.user.age,
                                address = card.user.address,
                                interests = card.user.interests,
                                idealTypes = card.user.idealTypes,
                                introduce = card.user.introduce,
                                timer = if (isCurrentCard) timer else null,
                                enable = isCurrentCard && timer.startAble && cardMoveAllow,
                                fallingAnimationEnable = idx == fallingAnimationTargetIdx,
                                isHoldCard = isHoldCard,
                                isShakingCard = isShakingCard,
                                onFallingAnimationFinish = { onFallingAnimationFinish(idx) },
                                userCardClick = { },
                                onReportMenuClick = onReportMenuClick,
                                onTicChanged = { onTicChanged(it, idx) },
                                onTimerEnd = { onTimerEnd(idx) },
                                onLikeClick = { onLikeClick(idx) },
                                onUnLikeClick = { onUnLikeClick(idx) },
                                loadFinishListener = { s, e -> loadFinishListener(idx, s, e) },
                                onHoldDoubleTab = onHoldDoubleTab
                            )
                        }

                        is ToHotCardUiModel.Topic -> {
                            TopicSelectCard(
                                modifier = cardModifier,
                                topicCard = card.topic,
                                selectTopicIdx = topicInfo.selectTopicIdx,
                                onSelectTopic = onSelectTopic,
                                onClickConfirm = onClickConfirm
                            )
                        }
                    }
                }
                LaunchedEffect(key1 = pagerState) {
                    snapshotFlow { pagerState.currentPage }.collect { onCardChange(it) }
                }
            }
        }
    }
}

@Composable
@Preview
fun ToHotScreenPreview() {
    val toHotState = ToHotState(
        cardList = mockUserList.toList().map { ToHotCardUiModel.User(it) }.toImmutableList(),
        userCardState = ToHotCardState.Running,
        timer = CardTimerUiModel(
            maxTimer = 5.toDuration(DurationUnit.NANOSECONDS),
            initialDelay = 1.toDuration(DurationUnit.NANOSECONDS),
            completionDelay = 1.toDuration(DurationUnit.NANOSECONDS),
            duration = 6.toDuration(DurationUnit.NANOSECONDS),
            startAble = true
        ),
        enableTimerIdx = 0,
        cardVisibleState = ToHotState.CardVisibleState(
            cardMoveAllow = true
        ),
        dialogState = ToHotState.DialogState(),
        loading = ToHotLoading.None,
        topic = ToHotState.TopicInfo(
            selectTopicIdx = -1,
            currentTopic = null,
            topicResetTimeMill = 0
        ),
        hasUnReadAlarm = false
    )
    ToHotScreen(
        cardList = toHotState.cardList,
        toHotCardState = toHotState.userCardState,
        pagerState = rememberPagerState(
            pageCount = { toHotState.cardList.size }
        ),
        timer = toHotState.timer,
        currentUserIdx = toHotState.enableTimerIdx,
        cardMoveAllow = toHotState.cardVisibleState.cardMoveAllow,
        topicInfo = toHotState.topic,
        hasUnReadAlarm = toHotState.hasUnReadAlarm,
        fallingAnimationTargetIdx = toHotState.cardVisibleState.fallingAnimationIdx,
        isHoldCard = false,
        isShakingCard = false,
        onFallingAnimationFinish = { },
        topicSelectListener = { },
        alarmClickListener = { },
        onCardChange = { },
        onTimerEnd = { },
        onTicChanged = { _, _ -> },
        loadFinishListener = { _, _, _ -> },
        onLikeClick = { },
        onUnLikeClick = { },
        onReportMenuClick = { },
        onRefreshClick = { }
    )
}
