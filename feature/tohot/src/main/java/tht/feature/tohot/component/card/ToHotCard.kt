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
import com.tht.tht.domain.signup.model.IdealTypeModel
import com.tht.tht.domain.signup.model.InterestModel
import tht.feature.tohot.component.pager.ToHotCardImagePager
import tht.feature.tohot.component.pager.ToHotPagerIndicator
import tht.feature.tohot.component.progress.ToHotAnimateTimeProgressContainer
import tht.feature.tohot.component.userinfo.ToHotUserInfoCard
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
    maxTimeSec: Int,
    currentSec: Int,
    destinationSec: Int,
    enable: Boolean,
    ticChanged: (Int) -> Unit = { },
    userCardClick: () -> Unit = { },
    onReportClick: () -> Unit = { },
    loadFinishListener: (Boolean?, Throwable?) -> Unit = { _, _ -> }
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
            imageUrls = imageUrls,
            loadFinishListener = loadFinishListener
        )

        ToHotAnimateTimeProgressContainer(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(horizontal = 13.dp, vertical = 12.dp),
            enable = enable,
            maxTimeSec = maxTimeSec,
            currentSec = currentSec,
            ticChanged = ticChanged,
            destinationSec = destinationSec,
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
            pageCount = imageUrls.list.size,
            width = 10.dp,
            height = 10.dp
        )
    }
}
