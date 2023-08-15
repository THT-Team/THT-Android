package tht.feature.tohot.component.progress

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ProgressIndicatorDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import tht.core.ui.R

@Composable
fun ToHotHeartTimeProgressContainer(
    modifier: Modifier = Modifier
) {
    ToHotProgressTimeBackground(
        modifier = modifier,
        color = colorResource(id = R.color.black_1A1A1A).copy(alpha = 0.5f)
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(end = 12.dp)
        ) {
            Image(
                painter = painterResource(id = tht.feature.tohot.R.drawable.ic_timer_heart),
                contentDescription = "heart_timer"
            )
            Image(
                modifier = Modifier.align(Alignment.Center),
                painter = painterResource(id = tht.feature.tohot.R.drawable.ic_heart),
                contentDescription = "heart"
            )
        }
        val gradientBrush = Brush.horizontalGradient(
            colors = listOf(
                colorResource(id = R.color.red_f9184e),
                colorResource(id = R.color.orange_f9743d),
                colorResource(id = R.color.yellow_f9cc2e),
            ),
            startX = 0.0f,
            endX = Float.POSITIVE_INFINITY,
        )
        Canvas(
            modifier = modifier
                .fillMaxWidth()
                .height(ProgressIndicatorDefaults.StrokeWidth)
                .align(Alignment.CenterVertically)
        ) {
            val strokeWidth = size.height
            drawLine(
                brush = gradientBrush,
                cap = StrokeCap.Round,
                start = Offset(0f, size.height / 2),
                end = Offset(size.width, size.height / 2),
                strokeWidth = strokeWidth
            )
        }
    }
}

@Composable
@Preview
private fun ToHotHeartTimeProgressContainerPreview() {
    ToHotHeartTimeProgressContainer()
}
