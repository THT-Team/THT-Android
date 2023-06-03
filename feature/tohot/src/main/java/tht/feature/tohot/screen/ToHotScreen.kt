package tht.feature.tohot.screen

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import tht.feature.tohot.component.card.ToHotCard
import tht.feature.tohot.component.toolbar.ToHotTopAppBar
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
                        is ToHotSideEffect.ScrollToAndRemoveFirst -> {
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

    val modalBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true
    )
    LaunchedEffect(key1 = toHotState.selectTopic) {
        if (toHotState.selectTopic == null) {
            modalBottomSheetState.show()
        } else {
            modalBottomSheetState.hide()
        }
    }

    TopicSelectModel(
        modalBottomSheetState = modalBottomSheetState,
        remainingTime = toHotState.topicSelectRemainingTime,
        topics = toHotState.topicList,
        selectTopic = toHotState.selectTopic?.key ?: -1
    ) {
        ToHotScreen(
            cardList = toHotState.userList,
            pagerState = pagerState,
            timers = toHotState.timers,
            currentUserIdx = toHotState.enableTimerIdx,
            pageChanged = toHotViewModel::userChangeEvent,
            ticChanged = toHotViewModel::ticChangeEvent
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ToHotScreen(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    cardList: ImmutableListWrapper<ToHotUserUiModel>,
    timers: ImmutableListWrapper<CardTimerUiModel>,
    currentUserIdx: Int,
    pageChanged: (Int) -> Unit,
    ticChanged: (Int, Int) -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        ToHotTopAppBar()
        VerticalPager(
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
                enable = currentUserIdx == pagerState.currentPage,
                userCardClick = { },
                onReportClick = { },
                ticChanged = { ticChanged(it, idx) }
            )
        }
        LaunchedEffect(key1 = pagerState) {
            snapshotFlow { pagerState.currentPage }
                .collect { pageChanged(it) }
        }
    }
}
