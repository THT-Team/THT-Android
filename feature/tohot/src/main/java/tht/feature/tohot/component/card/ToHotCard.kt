package tht.feature.tohot.component.card

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import tht.feature.tohot.component.pager.ToHotCardImagePager
import tht.feature.tohot.component.pager.ToHotPagerIndicator
import tht.feature.tohot.component.progress.ToHotAnimateTimeProgressContainer
import tht.feature.tohot.component.userinfo.ToHotUserInfoCard

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ToHotCard(
    modifier: Modifier = Modifier,
    imageUrls: List<String>,
    name: String,
    age: Int,
    address: String,
    interests: List<Long>,
    idealTypes: List<Long>,
    introduce: String,
    maxTimeSec: Int,
    currentSec: Int,
    ticChanged: (Int) -> Unit = { },
    userCardClick: () -> Unit = { },
    onReportClick: () -> Unit = { }
) {
    val pagerState = rememberPagerState()
    var userInfoFullShow by remember { mutableStateOf(false) }
    Box(
        modifier = modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(12.dp))
    ) {
        ToHotCardImagePager(
            modifier = Modifier.fillMaxSize(),
            pagerState = pagerState,
            imageUrls = imageUrls
        )

        ToHotAnimateTimeProgressContainer(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(horizontal = 13.dp, vertical = 12.dp),
            maxTimeSec = maxTimeSec,
            currentSec = currentSec,
            ticChanged = ticChanged
        )

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
            onReportClick = onReportClick
        )

        ToHotPagerIndicator(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp, start = 10.dp, end = 10.dp),
            pagerState = pagerState,
            pageCount = imageUrls.size,
            width = 10.dp,
            height = 10.dp
        )
    }
}
