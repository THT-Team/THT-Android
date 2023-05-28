package tht.feature.tohot.component.progress

import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.tooling.preview.Preview

/**
 * 1. maxTime, currentSec 를 받아 ProgressBar 구성
 * 2. maxTime, destinationSec 로 목표 progress 산출
 * 3. 애니메이션 수행
 * 4. 애니메이션 수행 후 ticChanged 호출
 */
@Composable
fun ToHotAnimateTimeLinearProgress(
    modifier: Modifier = Modifier,
    enable: Boolean,
    maxTimeSec: Int,
    currentSec: Int,
    destinationSec: Int,
    duration: Int = (currentSec - destinationSec) * 1000,
    color: Color,
    ticChanged: (Int) -> Unit = { }
) {
    val progressAnimatable = remember { Animatable((currentSec.toFloat() / maxTimeSec.toFloat())) }
    ToHotTimeProgressBar(
        modifier = modifier,
        progress = progressAnimatable.value,
        color = color
    )
    LaunchedEffect(key1 = destinationSec, key2 = enable) {
        if (enable) {
            Log.d("ToHot", "duration => $duration, progress => ${destinationSec.toFloat() / maxTimeSec.toFloat()}, target : ${progressAnimatable.targetValue}, value : ${progressAnimatable.value}")
            if (progressAnimatable.targetValue == destinationSec.toFloat() / maxTimeSec.toFloat()) {
                ticChanged((progressAnimatable.value * maxTimeSec).toInt())
                return@LaunchedEffect
            }
            if (destinationSec > progressAnimatable.value * maxTimeSec) {
                progressAnimatable.animateTo(
                    targetValue = currentSec.toFloat() / maxTimeSec.toFloat(),
                    animationSpec = tween(
                        durationMillis = 0,
                        easing = LinearEasing
                    )
                )
            }
            progressAnimatable.animateTo(
                targetValue = destinationSec.toFloat() / maxTimeSec.toFloat(),
                animationSpec = tween(
                    durationMillis = duration,
                    easing = LinearEasing
                )
            )
            ticChanged((progressAnimatable.value * maxTimeSec).toInt())
        }
    }
}

@Composable
fun ToHotTimeProgressBar(
    modifier: Modifier = Modifier,
    progress: Float,
    backgroundColor: Color = Color(0xFF222222),
    color: Color = Color(0xFFF9CC2E)
) {
    LinearProgressIndicator(
        modifier = modifier.fillMaxWidth(),
        progress = progress, // 0.1 ~ 1.0
        backgroundColor = backgroundColor,
        color = color,
        strokeCap = StrokeCap.Round
    )
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFF000000, name = "AnimateProgress")
private fun ToHotAnimateTimeProgressBarPreview() {
    ToHotAnimateTimeLinearProgress(
        enable = true,
        maxTimeSec = 5,
        currentSec = 5,
        destinationSec = 3,
        color = Color(0xFFF9CC2E)
    )
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFF000000, name = "progress")
private fun ToHotTimeProgressBarPreview() {
    ToHotTimeProgressBar(
        progress = 0.4f
    )
}
