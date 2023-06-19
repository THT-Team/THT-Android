package tht.feature.tohot.screen

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.compose_ui.component.progress.ThtCircularProgress
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import tht.feature.tohot.component.card.ToHotCard
import tht.feature.tohot.component.card.ToHotEmptyCard
import tht.feature.tohot.component.card.ToHotEnterCard
import tht.feature.tohot.component.dialog.ToHotUseReportDialog
import tht.feature.tohot.component.dialog.ToHotUserBlockDialog
import tht.feature.tohot.component.dialog.ToHotUserReportMenuDialog
import tht.feature.tohot.component.toolbar.ToHotToolBar
import tht.feature.tohot.component.toolbar.ToHotToolBarContent
import tht.feature.tohot.model.CardTimerUiModel
import tht.feature.tohot.model.ImmutableListWrapper
import tht.feature.tohot.model.ToHotUserUiModel
import tht.feature.tohot.state.ToHotSideEffect
import tht.feature.tohot.viewmodel.ToHotViewModel

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
fun ToHotRoute(
    toHotViewModel: ToHotViewModel = hiltViewModel()
) {
    val toHotState by toHotViewModel.store.state.collectAsState()
    val pagerState = rememberPagerState()

    LaunchedEffect(key1 = toHotViewModel) {
        launch {
            toHotViewModel.store.sideEffect.collect {
                Log.d("ToHot", "sideEffect collect => $isActive")
                try {
                    when (it) {
                        is ToHotSideEffect.RemoveAndScroll -> {
                            try {
                                pagerState.animateScrollToPage(it.scrollIdx)
                                Log.d("ToHot", "scroll to ${it.scrollIdx}")
                            } catch (e: Exception) {
                                e.printStackTrace()
                                Log.d("ToHot", "Cancel Coroutine")
                            } finally {
                                toHotViewModel.removeUserCard(it.removeIdx)
                                Log.d("ToHot", "remove ${it.removeIdx}")
                            }
                        }
                    }
                }catch (e :Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    ToHotUserReportMenuDialog(
        isShow = toHotState.reportMenuDialogShow,
        onReportClick = toHotViewModel::reportMenuReportEvent,
        onBlockClick = toHotViewModel::reportMenuBlockEvent,
        onDismiss = { toHotViewModel.dialogDismissEvent(pagerState.currentPage) }
    )

    ToHotUseReportDialog(
        isShow = toHotState.reportDialogShow,
        reportReason = toHotState.reportReason,
        onReportClick = { toHotViewModel.reportEvent(pagerState.currentPage) },
        onCancelClick = { toHotViewModel.dialogDismissEvent(pagerState.currentPage) },
        onDismiss = { toHotViewModel.dialogDismissEvent(pagerState.currentPage) }
    )

    ToHotUserBlockDialog(
        isShow = toHotState.blockDialogShow,
        onBlockClick = { toHotViewModel.blockEvent(pagerState.currentPage) },
        onCancelClick = { toHotViewModel.dialogDismissEvent(pagerState.currentPage) },
        onDismiss = { toHotViewModel.dialogDismissEvent(pagerState.currentPage) }
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
            remainingTime = toHotState.topicSelectRemainingTime,
            topics = toHotState.topicList,
            selectTopicKey = toHotState.selectTopicKey,
            topicClickListener = toHotViewModel::topicSelectEvent,
            selectFinishListener = toHotViewModel::topicSelectFinishEvent
        ) {
            ToHotScreen(
                cardList = toHotState.userList,
                isEnterDelay = toHotState.isFirstPage,
                pagerState = pagerState,
                timers = toHotState.timers,
                currentUserIdx = toHotState.enableTimerIdx,
                topicIconUrl = toHotState.currentTopic?.iconUrl,
                topicIconRes = toHotState.currentTopic?.iconRes,
                topicTitle = toHotState.currentTopic?.title,
                hasUnReadAlarm = toHotState.hasUnReadAlarm,
                topicSelectListener = toHotViewModel::topicChangeClickEvent,
                alarmClickListener = toHotViewModel::alarmClickEvent,
                pageChanged = toHotViewModel::userChangeEvent,
                ticChanged = toHotViewModel::ticChangeEvent,
                loadFinishListener = toHotViewModel::userCardLoadFinishEvent,
                onLikeClick = toHotViewModel::likeCardEvent,
                onUnLikeClick = toHotViewModel::unlikeCardEvent,
                onReportMenuClick = toHotViewModel::reportMenuEvent
            )
        }

        ThtCircularProgress(
            modifier = Modifier.align(Alignment.Center),
            dataLoading = { toHotState.loading },
            color = colorResource(id = tht.core.ui.R.color.yellow_f9cc2e)
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ToHotScreen(
    modifier: Modifier = Modifier,
    isEnterDelay: Boolean,
    pagerState: PagerState,
    cardList: ImmutableListWrapper<ToHotUserUiModel>,
    timers: ImmutableListWrapper<CardTimerUiModel>,
    currentUserIdx: Int,
    topicIconUrl: String?,
    topicIconRes: Int?,
    topicTitle: String?,
    hasUnReadAlarm: Boolean,
    topicSelectListener: () -> Unit = { },
    alarmClickListener: () -> Unit = { },
    pageChanged: (Int) -> Unit,
    ticChanged: (Int, Int) -> Unit,
    onLikeClick: (Int) -> Unit = { },
    onUnLikeClick: (Int) -> Unit = { },
    onReportMenuClick: () -> Unit = { },
    loadFinishListener: (Int, Boolean?, Throwable?) -> Unit = { _, _, _ -> }
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
                if (isEnterDelay) {
                    ToHotEnterCard()
                } else {
                    ToHotEmptyCard()
                }
            }

            else -> {
                VerticalPager(
                    userScrollEnabled = false,
                    pageCount = cardList.list.size,
                    state = pagerState,
                    key = { cardList.list[it].nickname }
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
                        enable = currentUserIdx == pagerState.currentPage && timers.list[idx].startAble,
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
