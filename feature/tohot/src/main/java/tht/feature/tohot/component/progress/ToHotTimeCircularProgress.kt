package tht.feature.tohot.component.progress

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * offset setting
 * https://stackoverflow.com/questions/74456259/android-compose-drawarc-would-not-get-centered-within-box
 */
@Composable
fun ToHotTimeCircularProgress(
    modifier: Modifier = Modifier,
    size: Dp,
    sec: Int,
    progress: Float,
    duration: Int,
    color: Color
) {
    Box(
        modifier = modifier
            .size(size)
            .padding(end = 6.dp)
    ) {
        ToHotCircularProgress(
            modifier = Modifier,
            color = color,
            size = size,
            progress = progress,
            duration = duration
        )
        ToHotTimeProgressText(
            modifier = Modifier
                .align(Alignment.Center),
            sec = sec,
            textColor = color
        )
    }
}

@Composable
fun ToHotCircularProgress(
    modifier: Modifier = Modifier,
    color: Color,
    size: Dp,
    progress: Float,
    duration: Int
) {
    val animateFloat = remember { Animatable(progress) }
    val stroke = with(LocalDensity.current) { Stroke(2.dp.toPx()) }
    val sizePx = with(LocalDensity.current) { size.toPx() }
    Canvas(
        modifier = modifier
            .size(size)
            .aspectRatio(1f)
    ) {
        val offsetX = this.center.x - sizePx / 4
        val offsetY = this.center.y - sizePx / 4
        drawArc(
            color = color,
            startAngle = 0f,
            sweepAngle = 360f * progress,
            useCenter = false,
            topLeft = Offset(
                x = offsetX,
                y = offsetY
            ),
            size = Size(sizePx / 2, sizePx / 2),
            style = stroke
        )
    }
    LaunchedEffect(animateFloat) {
        animateFloat.animateTo(
            targetValue = progress,
            animationSpec = tween(durationMillis = duration, easing = LinearEasing)
        )
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFF000000, name = "ToHotCircularProgress")
private fun ToHotAnimateTimeCircularProgressPreview() {
    ToHotCircularProgress(
        progress = 1.0f,
        duration = 1000,
        color = Color.Blue,
        size = 36.dp
    )
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFF000000, name = "ToHotTimeCircularProgress")
private fun ToHotTimeCircularProgressPreview() {
    ToHotTimeCircularProgress(
        color = Color(0xFFF9CC2E),
        size = 36.dp,
        sec = 5,
        progress = 0.8f,
        duration = 1000
    )
}
