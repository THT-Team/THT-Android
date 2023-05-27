package tht.feature.tohot.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tht.tht.domain.signup.model.SignupUserModel
import tht.feature.tohot.component.card.ToHotCard
import tht.feature.tohot.component.toolbar.ToHotTopAppBar
import tht.feature.tohot.viewmodel.ToHotViewModel

@Composable
fun ToHotRoute(
    toHotViewModel: ToHotViewModel = hiltViewModel()
) {
    val cardList by toHotViewModel.cardList.collectAsState()
    ToHotScreen(
        cardList = cardList
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ToHotScreen(
    modifier: Modifier = Modifier,
    cardList: List<SignupUserModel>
) {
    val pagerState = rememberPagerState()
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        ToHotTopAppBar { }
        VerticalPager(
            pageCount = cardList.size,
            state = pagerState
        ) {
            val card = cardList[it]
            ToHotCard(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 14.dp, end = 14.dp, top = 6.dp, bottom = 14.dp),
                imageUrls = card.profileImgUrl,
                name = card.nickname,
                age = 10,
                address = card.address,
                interests = card.interestKeys,
                idealTypes = card.idealTypeKeys,
                introduce = card.introduce,
                maxTimeSec = 5,
                currentSec = 4,
                userCardClick = { },
                onReportClick = { },
                ticChanged = { }
            )
        }
    }
}
