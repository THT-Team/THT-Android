package tht.feature.tohot.component.progress

import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * 1. maxTime, currentSec 를 받아 ProgressBar 구성
 * 2. maxTime, destinationSec 로 목표 progress 산출
 * 3. 애니메이션 수행
 * 4. 애니메이션 수행 후 ticChanged 호출
 */
@Composable
fun ToHotAnimateTimeProgressContainer(
    modifier: Modifier = Modifier,
    enable: Boolean,
    maxTimeSec: Int,
    currentSec: Int,
    destinationSec: Int,
    colors: List<Color> = listOf(
        Color(0xFFF9CC2E),
        Color(0xFFF98F2E),
        Color(0xFFF93A2E)
    ),
    progressBackgroundColor: Color = colorResource(id = tht.core.ui.R.color.black_353535),
    duration: Int = (currentSec - destinationSec) * 1000,
    ticChanged: (Int) -> Unit = { }
) {
    val destinationProgress = destinationSec.toFloat() / maxTimeSec.toFloat()
    var color = colors.lastOrNull() ?: Color.Yellow
    for (i in colors.indices) {
        val value = colors.size - i - 1
        if (destinationProgress >= (1.0f / colors.size) * value) {
            color = colors[i]
            break
        }
    }
    val progressColor by animateColorAsState(
        targetValue = color,
        animationSpec = tween(durationMillis = 1000)
    )

    val progressAnimatable = remember { Animatable((currentSec.toFloat() / maxTimeSec.toFloat())) }
    LaunchedEffect(key1 = destinationSec, key2 = enable) {
        if (enable) {
            Log.d(
                "ToHot",
                "duration => $duration, progress => $destinationProgress," +
                    " target : ${progressAnimatable.targetValue}, value : ${progressAnimatable.value}"
            )
            if (progressAnimatable.targetValue == destinationProgress) {
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
                targetValue = destinationProgress,
                animationSpec = tween(
                    durationMillis = duration,
                    easing = LinearEasing
                )
            )
            ticChanged((progressAnimatable.value * maxTimeSec).toInt())
        }
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(color = Color(0xFF1A1A1A).copy(alpha = 0.5f))
            .padding(horizontal = 10.dp)
    ) {
        ToHotTimeCircularProgress(
            modifier = Modifier
                .align(Alignment.CenterVertically),
            size = 36.dp,
            color = progressColor,
            size = 24.dp,
            progressColor = animateProgressColor,
            backgroundColor = progressBackgroundColor,
            progress = 1 - progressAnimatable.value,
            sec = currentSec
        )

        ToHotTimeProgressBar(
            modifier = Modifier
                .align(Alignment.CenterVertically),
            color = progressColor,
            progress = progressAnimatable.value
        )
    }
}
