package tht.feature.tohot.component.pager

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ToHotPagerIndicator(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    pageCount: Int,
    width: Dp,
    height: Dp,
    backgroundColor: Color =
        colorResource(id = tht.core.ui.R.color.white_f9fafa)
            .copy(alpha = 0.5f),
    selectColor: Color = colorResource(id = tht.core.ui.R.color.white_f9fafa)
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        repeat(pageCount) { iteration ->
            val color = if (pagerState.currentPage == iteration) selectColor else backgroundColor
            Box(
                modifier = Modifier
                    .padding(5.dp)
                    .clip(CircleShape)
                    .background(color)
                    .size(width = width, height = height)
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
@Preview(showBackground = true, backgroundColor = 0xFF000000)
private fun ToHotPagerIndicatorPreview() {
    ToHotPagerIndicator(
        pagerState = rememberPagerState(),
        pageCount = 10,
        width = 20.dp,
        height = 20.dp
    )
}
