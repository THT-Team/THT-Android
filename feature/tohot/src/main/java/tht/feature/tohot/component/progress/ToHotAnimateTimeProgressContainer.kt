package tht.feature.tohot.component.progress

import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * 1. maxTime, currentSec 를 받아 ProgressBar 구성
 * 2. maxTime, destinationSec 로 목표 progress 산출
 * 3. 애니메이션 수행
 * 4. 애니메이션 수행 후 ticChanged 호출
 *
 * TODO: Timer 가 tic 마다 끊기는 듯한 UI 문제 확인
 * - Release Build 테스트
 * - Recomposition 최적화 확인
 */
@Composable
fun ToHotAnimateTimeProgressContainer(
    modifier: Modifier = Modifier,
    enable: Boolean,
    maxTimeSec: Int,
    currentSec: Float,
    destinationSec: Float,
    progressColor: List<Color> = listOf(
        Color(0xFFF9CC2E),
        Color(0xFFF98F2E),
        Color(0xFFF93A2E)
    ),
    progressBackgroundColor: Color = colorResource(id = tht.core.ui.R.color.black_353535),
    duration: Float = ((currentSec - destinationSec) * 1000),
    ticChanged: (Float) -> Unit = { }
) {
    Log.d("ToHot", "Timer => c[$currentSec], d[$destinationSec]")
    val destinationProgress = destinationSec / maxTimeSec.toFloat()
    var color = progressColor.lastOrNull() ?: Color.Yellow
    for (i in progressColor.indices) {
        val value = progressColor.size - i - 1
        if (destinationProgress >= (1.0f / progressColor.size) * value) {
            color = progressColor[i]
            break
        }
    }
    val animateProgressColor by animateColorAsState(
        targetValue = color,
        animationSpec = tween(durationMillis = 1000),
        label = "animateProgressColor"
    )

    val progressAnimatable = remember { Animatable((currentSec / maxTimeSec.toFloat())) }
    LaunchedEffect(key1 = destinationSec, key2 = enable) {
        if (enable) {
            Log.d(
                "ToHot",
                "duration => $duration, progress => $destinationProgress," +
                    " target : ${progressAnimatable.targetValue}, value : ${progressAnimatable.value}"
            )
            if (progressAnimatable.targetValue == destinationProgress) {
                ticChanged((progressAnimatable.value * maxTimeSec))
                return@LaunchedEffect
            }
            if (destinationSec > progressAnimatable.value * maxTimeSec) {
                progressAnimatable.animateTo(
                    targetValue = currentSec / maxTimeSec.toFloat(),
                    animationSpec = tween(
                        durationMillis = 0,
                        easing = LinearEasing
                    )
                )
            }
            progressAnimatable.animateTo(
                targetValue = destinationProgress,
                animationSpec = tween(
                    durationMillis = duration.toInt(),
                    easing = LinearEasing
                )
            )
            ticChanged((progressAnimatable.value * maxTimeSec))
        }
    }

    ToHotProgressTimeBackground(
        modifier = modifier,
        color = colorResource(id = tht.core.ui.R.color.black_1A1A1A).copy(alpha = 0.5f)
    ) {
        ToHotTimeCircularProgress(
            modifier = Modifier
                .align(Alignment.CenterVertically),
            size = 24.dp,
            progressColor = animateProgressColor,
            backgroundColor = progressBackgroundColor,
            progress = 1 - progressAnimatable.value,
            sec = currentSec.toInt()
        )

        ToHotTimeProgressBar(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(start = 10.dp),
            color = animateProgressColor,
            progress = progressAnimatable.value
        )
    }
}

@Composable
@Preview
private fun ToHotAnimateTimeProgressContainerPreview() {
    ToHotAnimateTimeProgressContainer(
        modifier = Modifier.padding(horizontal = 13.dp, vertical = 12.dp),
        enable = true,
        maxTimeSec = 5,
        currentSec = 5f,
        ticChanged = {},
        destinationSec = 4f
    )
}
