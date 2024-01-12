package tht.feature.tohot.component.progress

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.cos
import kotlin.math.sin

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
    progressColor: Color,
    backgroundColor: Color
) {
    Box(
        modifier = modifier
            .size(size)
//            .padding(end = 6.dp)
    ) {
        ToHotCircularProgress(
            modifier = Modifier,
            progressColor = progressColor,
            backgroundColor = backgroundColor,
            size = size,
            progress = progress
        )
        ToHotTimeProgressText(
            modifier = Modifier
                .align(Alignment.Center),
            sec = sec,
            textColor = progressColor
        )
    }
}

/**
 * Progress 앞에 원 배치
 * https://blog.droidchef.dev/custom-progress-with-jetpack-compose-tutorial/
 */
@Composable
fun ToHotCircularProgress(
    modifier: Modifier = Modifier,
    progressColor: Color,
    backgroundColor: Color,
    size: Dp,
    progress: Float
) {
    val stroke = with(LocalDensity.current) {
        Stroke(
            width = 2.dp.toPx(),
            cap = StrokeCap.Round
        )
    }
    val sweepAngle = 360f - (360f * progress)
    Canvas(
        modifier = modifier
            .size(size)
            .aspectRatio(1f)
            .padding(1.dp)
    ) {
        // inner background circle
        drawCircle(
            color = backgroundColor,
            radius = (this.size.width / 2) * 0.80f
        )

        // draw progress
        drawArc(
            color = progressColor,
            startAngle = 360f,
            sweepAngle = sweepAngle,
            useCenter = false,
            size = Size(this.size.width, this.size.height),
            style = stroke
        )

        if (progress == 0f || progress == 360f) return@Canvas

        val circleSize = this.size.height
        val angleInDegrees = sweepAngle.toDouble() - 65.0 // - 값은 하드 코딩 해야 하나?
        val radius = (circleSize / 2)
        val x = -(radius * sin(Math.toRadians(angleInDegrees))).toFloat() + (circleSize / 2)
        val y = (radius * cos(Math.toRadians(angleInDegrees))).toFloat() + (circleSize / 2)

        // progress 앞의 circle
        drawCircle(
            color = progressColor,
            radius = stroke.width * 0.7f,
            center = Offset(x, y)
        )
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFF000000, name = "ToHotCircularProgress")
private fun ToHotAnimateTimeCircularProgressPreview() {
    ToHotCircularProgress(
        progress = 0.0f,
        progressColor = Color.Blue,
        backgroundColor = Color.Gray,
        size = 24.dp
    )
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFF000000, name = "ToHotTimeCircularProgress")
private fun ToHotTimeCircularProgressPreview() {
    ToHotTimeCircularProgress(
        progressColor = colorResource(id = tht.core.ui.R.color.yellow_f9cc2e),
        backgroundColor = colorResource(id = tht.core.ui.R.color.black_353535),
        size = 24.dp,
        sec = 5,
        progress = 0.8f
    )
}
