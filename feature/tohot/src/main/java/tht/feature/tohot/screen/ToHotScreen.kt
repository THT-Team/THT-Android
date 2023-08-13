package tht.feature.tohot.screen

import android.view.MotionEvent
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCancellationBehavior
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieAnimatable
import com.airbnb.lottie.compose.rememberLottieComposition
import kotlinx.coroutines.launch
import tht.core.ui.extension.showToast
import tht.feature.tohot.R
import tht.feature.tohot.component.card.ToHotCard
import tht.feature.tohot.component.card.ToHotEmptyCard
import tht.feature.tohot.component.card.ToHotEnterCard
import tht.feature.tohot.component.card.ToHotErrorCard
import tht.feature.tohot.component.card.ToHotLoadingCard
import tht.feature.tohot.component.dialog.ToHotHoldDialog
import tht.feature.tohot.component.dialog.ToHotUseReportDialog
import tht.feature.tohot.component.dialog.ToHotUserBlockDialog
import tht.feature.tohot.component.dialog.ToHotUserReportMenuDialog
import tht.feature.tohot.component.toolbar.ToHotToolBar
import tht.feature.tohot.component.toolbar.ToHotToolBarContent
import tht.feature.tohot.model.CardTimerUiModel
import tht.feature.tohot.model.ImmutableListWrapper
import tht.feature.tohot.model.ToHotUserUiModel
import tht.feature.tohot.state.ToHotCardState
import tht.feature.tohot.state.ToHotLoading
import tht.feature.tohot.state.ToHotSideEffect
import tht.feature.tohot.viewmodel.ToHotViewModel

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class)
@Composable
fun ToHotRoute(
    toHotViewModel: ToHotViewModel = hiltViewModel()
) {
    val toHotState by toHotViewModel.store.state.collectAsState()
    val pagerState = rememberPagerState()
    val context = LocalContext.current

    val userHeartLottieComposition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.user_heart)
    )
    val userHeartLottieAnimatable = rememberLottieAnimatable()

    val userXLottieComposition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.user_x)
    )
    val userXLottieAnimatable = rememberLottieAnimatable()


    LaunchedEffect(key1 = toHotViewModel, key2 = context) {
        launch {
            toHotViewModel.store.sideEffect.collect {
                try {
                    when (it) {
                        is ToHotSideEffect.ToastMessage -> context.showToast(it.message)

                        is ToHotSideEffect.Scroll -> {
                            try {
                                pagerState.animateScrollToPage(it.idx)
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }

                        is ToHotSideEffect.RemoveAfterScroll -> {
                            try {
                                pagerState.animateScrollToPage(it.scrollIdx)
                            } catch (e: Exception) {
                                e.printStackTrace()
                            } finally {
                                toHotViewModel.removeUserCard(it.removeIdx)
                            }
                        }

                        is ToHotSideEffect.UserHeart -> {
                            userHeartLottieAnimatable.animate(
                                composition = userHeartLottieComposition,
                                initialProgress = 0f,
                                cancellationBehavior = LottieCancellationBehavior.OnIterationFinish
                            )
                            toHotViewModel.userHeartAnimationFinishEvent(it.userIdx)
                        }

                        is ToHotSideEffect.UserX -> {
                            userXLottieAnimatable.animate(
                                composition = userXLottieComposition,
                                initialProgress = 0f,
                                cancellationBehavior = LottieCancellationBehavior.OnIterationFinish
                            )
                            toHotViewModel.userHeartAnimationFinishEvent(it.userIdx)
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    ToHotUserReportMenuDialog(
        isShow = toHotState.reportMenuDialogShow,
        onReportClick = toHotViewModel::reportMenuReportEvent,
        onBlockClick = toHotViewModel::reportMenuBlockEvent,
        onDismiss = toHotViewModel::reportDialogDismissEvent
    )

    ToHotUseReportDialog(
        isShow = toHotState.reportDialogShow,
        reportReason = toHotState.reportReason,
        onReportClick = { toHotViewModel.cardReportEvent(pagerState.currentPage, it) },
        onCancelClick = toHotViewModel::reportDialogDismissEvent,
        onDismiss = toHotViewModel::reportDialogDismissEvent
    )

    ToHotUserBlockDialog(
        isShow = toHotState.blockDialogShow,
        onBlockClick = { toHotViewModel.cardBlockEvent(pagerState.currentPage) },
        onCancelClick = toHotViewModel::reportDialogDismissEvent,
        onDismiss = toHotViewModel::reportDialogDismissEvent
    )

    ToHotHoldDialog(
        isShow = toHotState.holdDialogShow,
        onRestartClick = toHotViewModel::releaseHoldEvent
    )

    val modalBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true,
        confirmValueChange = { false }
    )
    LaunchedEffect(key1 = toHotState.topicModalShow) {
        if (toHotState.topicModalShow) {
            modalBottomSheetState.show()
        } else {
            modalBottomSheetState.hide()
        }
    }

    LaunchedEffect(key1 = modalBottomSheetState) {
        snapshotFlow { modalBottomSheetState.currentValue }
            .collect {
                when (it) {
                    ModalBottomSheetValue.Expanded, ModalBottomSheetValue.HalfExpanded ->
                        toHotViewModel.openTopicSelectEvent()

                    ModalBottomSheetValue.Hidden ->
                        toHotViewModel.closeTopicSelectEvent()
                }
            }
    }

    BackHandler {
        toHotViewModel.backClickEvent(modalBottomSheetState.currentValue != ModalBottomSheetValue.Hidden)
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        TopicSelectModel(
            modalBottomSheetState = modalBottomSheetState,
            remainingTime = toHotState.topicResetRemainingTime,
            topics = toHotState.topicList,
            selectTopicKey = toHotState.selectTopicKey,
            topicClickListener = toHotViewModel::topicSelectEvent,
            selectFinishListener = toHotViewModel::topicSelectFinishEvent
        ) {
            ToHotScreen(
                modifier = Modifier
                    .pointerInteropFilter {
                        when (it.action) {
                            MotionEvent.ACTION_DOWN -> toHotViewModel.screenTouchEvent()
                        }
                        false
                    },
                cardList = toHotState.userList,
                toHotCardState = toHotState.userCardState,
                pagerState = pagerState,
                timers = toHotState.timers,
                currentUserIdx = toHotState.enableTimerIdx,
                cardMoveAllow = toHotState.cardMoveAllow,
                topicIconUrl = toHotState.currentTopic?.iconUrl,
                topicIconRes = toHotState.currentTopic?.iconRes,
                topicTitle = toHotState.currentTopic?.title,
                hasUnReadAlarm = toHotState.hasUnReadAlarm,
                fallingAnimationTargetIdx = toHotState.fallingAnimationIdx,
                onFallingAnimationFinish = toHotViewModel::fallingAnimationFinish,
                topicSelectListener = toHotViewModel::topicChangeClickEvent,
                alarmClickListener = toHotViewModel::alarmClickEvent,
                pageChanged = toHotViewModel::userChangeEvent,
                ticChanged = toHotViewModel::ticChangeEvent,
                loadFinishListener = toHotViewModel::userCardLoadFinishEvent,
                onLikeClick = toHotViewModel::userHeartEvent,
                onUnLikeClick = toHotViewModel::userXEvent,
                onReportMenuClick = toHotViewModel::reportMenuEvent,
                onRefreshClick = toHotViewModel::refreshEvent
            )
        }

        ToHotLoadingCard(
            isVisible = { toHotState.loading != ToHotLoading.None },
            message = when (toHotState.loading) {
                ToHotLoading.TopicList -> stringResource(id = R.string.to_hot_topic_list_loading)
                ToHotLoading.TopicSelect -> stringResource(id = R.string.to_hot_topic_select_loading)
                ToHotLoading.UserList -> stringResource(id = R.string.to_hot_user_card_loading)
                ToHotLoading.Report -> stringResource(id = R.string.to_hot_user_report_loading)
                ToHotLoading.Block -> stringResource(id = R.string.to_hot_user_block_loading)
                else -> ""
            }
        )

        LottieAnimation(
            modifier = Modifier
                .size(300.dp)
                .align(Alignment.Center),
            composition = userHeartLottieComposition,
            progress = { userHeartLottieAnimatable.progress },
            contentScale = ContentScale.FillHeight
        )

        LottieAnimation(
            modifier = Modifier
                .size(300.dp)
                .align(Alignment.Center),
            composition = userXLottieComposition,
            progress = { userXLottieAnimatable.progress },
            contentScale = ContentScale.FillHeight
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ToHotScreen(
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
    onFallingAnimationFinish: (Int) -> Unit = { },
    topicSelectListener: () -> Unit = { },
    alarmClickListener: () -> Unit = { },
    pageChanged: (Int) -> Unit,
    ticChanged: (Int, Int) -> Unit,
    onLikeClick: (Int) -> Unit = { },
    onUnLikeClick: (Int) -> Unit = { },
    onReportMenuClick: () -> Unit = { },
    onRefreshClick: () -> Unit = { },
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

        when (cardList.list.isEmpty()) {
            true -> {
                when (toHotCardState) {
                    ToHotCardState.Initialize -> ToHotEnterCard(onClick = onRefreshClick)
                    ToHotCardState.Running -> ToHotEmptyCard(onClick = onRefreshClick)
                    ToHotCardState.Error -> ToHotErrorCard(onClick = onRefreshClick)
                }
            }

            else -> {
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
                        maxTimeSec = timers.list[idx].maxSec,
                        currentSec = timers.list[idx].currentSec,
                        destinationSec = timers.list[idx].destinationSec,
                        enable = currentUserIdx == pagerState.currentPage &&
                            timers.list[idx].startAble && cardMoveAllow,
                        fallingAnimationEnable = idx == fallingAnimationTargetIdx,
                        onFallingAnimationFinish = { onFallingAnimationFinish(idx) },
                        userCardClick = { },
                        onReportMenuClick = onReportMenuClick,
                        ticChanged = { ticChanged(it, idx) },
                        onLikeClick = { onLikeClick(idx) },
                        onUnLikeClick = { onUnLikeClick(idx) },
                        loadFinishListener = { s, e -> loadFinishListener(idx, s, e) }
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
