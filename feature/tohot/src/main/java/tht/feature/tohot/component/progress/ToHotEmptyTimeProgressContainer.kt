package tht.feature.tohot.component.progress

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import tht.feature.tohot.R

@Composable
fun ToHotEmptyTimeProgressContainer(
    modifier: Modifier = Modifier
) {
    ToHotProgressTimeBackground(
        modifier = modifier,
        color = colorResource(id = tht.core.ui.R.color.black_1A1A1A).copy(alpha = 0.5f),
    ) {
        Image(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(end = 12.dp),
            painter = painterResource(id = R.drawable.ic_timer_empty),
            contentDescription = "empty_timer"
        )

        ToHotTimeProgressBar(
            modifier = Modifier
                .align(Alignment.CenterVertically),
            color = colorResource(id = tht.core.ui.R.color.black_222222),
            progress = 0f
        )
    }
}

@Composable
@Preview
fun ToHotEmptyTimeProgressContainerPreview() {
    ToHotEmptyTimeProgressContainer()
}
