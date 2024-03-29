package tht.feature.tohot.component.card

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tht.tht.domain.signup.model.IdealTypeModel
import com.tht.tht.domain.signup.model.InterestModel
import tht.feature.tohot.component.pager.ToHotCardImagePager
import tht.feature.tohot.component.pager.ToHotPagerIndicator
import tht.feature.tohot.component.progress.ToHotAnimateTimeProgressContainer
import tht.feature.tohot.component.progress.ToHotEmptyTimeProgressContainer
import tht.feature.tohot.component.progress.ToHotHeartTimeProgressContainer
import tht.feature.tohot.component.userinfo.ToHotUserInfoCard
import tht.feature.tohot.model.CardTimerUiModel
import tht.feature.tohot.model.ImmutableListWrapper

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ToHotCard(
    modifier: Modifier = Modifier,
    imageUrls: ImmutableListWrapper<String>,
    name: String,
    age: Int,
    address: String,
    interests: ImmutableListWrapper<InterestModel>,
    idealTypes: ImmutableListWrapper<IdealTypeModel>,
    introduce: String,
    timer: CardTimerUiModel.ToHotTimer,
    maxTimeSec: Int,
    currentSec: Float,
    destinationSec: Float,
    enable: Boolean,
    fallingAnimationEnable: Boolean = false,
    isHoldCard: Boolean,
    isShakingCard: Boolean,
    onFallingAnimationFinish: () -> Unit = { },
    ticChanged: (Float) -> Unit = { },
    userCardClick: () -> Unit = { },
    onLikeClick: () -> Unit = { },
    onUnLikeClick: () -> Unit = { },
    onReportMenuClick: () -> Unit = { },
    loadFinishListener: (Boolean, Throwable?) -> Unit = { _, _ -> },
    onHoldDoubleTab: () -> Unit = { }
) {
    val pagerState = rememberPagerState()
    var userInfoFullShow by remember { mutableStateOf(false) }

    val fallingAnimatedProgress = remember { Animatable(0f) }
    LaunchedEffect(key1 = fallingAnimationEnable) {
        if (!fallingAnimationEnable) return@LaunchedEffect
        fallingAnimatedProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 500)
        )
        onFallingAnimationFinish()
    }

    FallingCard(
        modifier = modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(12.dp)),
        fallingProgress = fallingAnimatedProgress.value
    ) {
        ToHotCardImagePager(
            modifier = Modifier.fillMaxSize(),
            pagerState = pagerState,
            imageUrls = imageUrls,
            isHold = isHoldCard,
            isShaking = isShakingCard && enable,
            loadFinishListener = loadFinishListener,
            onHoldDoubleTab = onHoldDoubleTab
        )

        val timerModifier = Modifier
            .align(Alignment.TopCenter)
            .padding(horizontal = 13.dp, vertical = 12.dp)
        when (timer) {
            CardTimerUiModel.ToHotTimer.Timer -> {
                ToHotAnimateTimeProgressContainer(
                    modifier = timerModifier,
                    enable = enable && !isHoldCard,
                    maxTimeSec = maxTimeSec,
                    currentSec = currentSec,
                    ticChanged = ticChanged,
                    destinationSec = destinationSec
                )
            }

            CardTimerUiModel.ToHotTimer.Heart -> {
                ToHotHeartTimeProgressContainer(
                    modifier = timerModifier
                )
            }

            CardTimerUiModel.ToHotTimer.Dislike -> {
                ToHotEmptyTimeProgressContainer(
                    modifier = timerModifier
                )
            }
        }

        if (isHoldCard) return@FallingCard

        ToHotUserInfoCard(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(horizontal = 18.dp, vertical = 38.dp),
            isFullCardShow = userInfoFullShow,
            name = name,
            age = age,
            address = address,
            interests = interests,
            idealTypes = idealTypes,
            introduce = introduce,
            onClick = {
                userInfoFullShow = userInfoFullShow.not()
                userCardClick()
            },
            onReportClick = onReportMenuClick,
            onLikeClick = onLikeClick,
            onUnLikeClick = onUnLikeClick
        )

        ToHotPagerIndicator(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp, start = 10.dp, end = 10.dp),
            pagerState = pagerState,
            pageCount = imageUrls.list.size,
            width = 10.dp,
            height = 10.dp
        )
    }
}

@Composable
@Preview
private fun ToHotCardPreview() {
    ToHotCard(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 14.dp, end = 14.dp, top = 6.dp, bottom = 14.dp),
        imageUrls = ImmutableListWrapper(
            listOf(
                "https://profile"
            )
        ),
        name = "nickname",
        age = 1,
        address = "address",
        interests = ImmutableListWrapper(emptyList()),
        idealTypes = ImmutableListWrapper(emptyList()),
        introduce = "introduce",
        timer = CardTimerUiModel.ToHotTimer.Timer,
        maxTimeSec = 5,
        currentSec = 5f,
        destinationSec = 4f,
        enable = true,
        isHoldCard = false,
        isShakingCard = false
    )
}

@Composable
@Preview
private fun ToHotCardHoldCardPreview() {
    ToHotCard(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 14.dp, end = 14.dp, top = 6.dp, bottom = 14.dp),
        imageUrls = ImmutableListWrapper(
            listOf(
                "https://profile"
            )
        ),
        name = "nickname",
        age = 1,
        address = "address",
        interests = ImmutableListWrapper(emptyList()),
        idealTypes = ImmutableListWrapper(emptyList()),
        introduce = "introduce",
        timer = CardTimerUiModel.ToHotTimer.Timer,
        maxTimeSec = 5,
        currentSec = 5f,
        destinationSec = 4f,
        enable = true,
        isHoldCard = true,
        isShakingCard = false
    )
}

@Composable
@Preview
private fun ToHotHeartCardPreview() {
    ToHotCard(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 14.dp, end = 14.dp, top = 6.dp, bottom = 14.dp),
        imageUrls = ImmutableListWrapper(
            listOf(
                "https://profile"
            )
        ),
        name = "nickname",
        age = 1,
        address = "address",
        interests = ImmutableListWrapper(emptyList()),
        idealTypes = ImmutableListWrapper(emptyList()),
        introduce = "introduce",
        timer = CardTimerUiModel.ToHotTimer.Heart,
        maxTimeSec = 5,
        currentSec = 5f,
        destinationSec = 4f,
        enable = true,
        isHoldCard = false,
        isShakingCard = false
    )
}

@Composable
@Preview
private fun ToHotDislikeCardPreview() {
    ToHotCard(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 14.dp, end = 14.dp, top = 6.dp, bottom = 14.dp),
        imageUrls = ImmutableListWrapper(
            listOf(
                "https://profile"
            )
        ),
        name = "nickname",
        age = 1,
        address = "address",
        interests = ImmutableListWrapper(emptyList()),
        idealTypes = ImmutableListWrapper(emptyList()),
        introduce = "introduce",
        timer = CardTimerUiModel.ToHotTimer.Dislike,
        maxTimeSec = 5,
        currentSec = 5f,
        destinationSec = 4f,
        enable = true,
        isHoldCard = false,
        isShakingCard = false
    )
}
