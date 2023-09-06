package tht.feature.tohot.component.pager

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import tht.feature.tohot.component.card.ToHotHoldCard
import tht.feature.tohot.component.card.ToHotCardImage
import tht.feature.tohot.model.ImmutableListWrapper
import tht.feature.tohot.userData

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ToHotCardImagePager(
    modifier: Modifier = Modifier,
    pagerState: PagerState = rememberPagerState(),
    imageUrls: ImmutableListWrapper<String>,
    isHold: Boolean,
    loadFinishListener: (Boolean, Throwable?) -> Unit = { _, _ -> },
    onHoldDoubleTab: () -> Unit = { }
) {
    Box(
        modifier = modifier
    ) {
        HorizontalPager(
            modifier = Modifier.fillMaxSize(),
            pageCount = imageUrls.list.size,
            state = pagerState
        ) { page ->
            ToHotHoldCard(
                modifier = Modifier.fillMaxSize(),
                isHold = isHold,
                clickableContent = false,
                onDoubleTab = onHoldDoubleTab
            ) {
                ToHotCardImage(
                    modifier = Modifier.fillMaxHeight(),
                    imageUrl = imageUrls.list[page],
                    loadFinishListener = loadFinishListener
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
@Preview(showBackground = true, backgroundColor = 0xFF000000)
private fun ToHotCardImagePagerPreview() {
    ToHotCardImagePager(
        imageUrls = userData.profileImgUrl,
        isHold = false
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
@Preview(showBackground = true, backgroundColor = 0xFF000000)
private fun ToHotCardImagePagerBlurPreview() {
    ToHotCardImagePager(
        imageUrls = userData.profileImgUrl,
        isHold = true
    )
}
