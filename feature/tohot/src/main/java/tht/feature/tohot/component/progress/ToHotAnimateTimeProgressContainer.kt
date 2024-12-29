package tht.feature.tohot.component.progress

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose_ui.common.LogComposition
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.ceil

@Composable
fun ToHotAnimateTimeProgressContainer(
    enable: Boolean,
    duration: Long,
    onEnd: () -> Unit,
    modifier: Modifier = Modifier,
    initialDelay: Long = 0L,
    completionDelay: Long = 0L,
) {
    val coroutineScope = rememberCoroutineScope()
    LogComposition("cwj_debug", "ToHotAnimateTimeProgressContainer")
    var progressState by remember { mutableStateOf(false) } // disActive
    LaunchedEffect(initialDelay, enable) {
        if (enable) {
            delay(initialDelay)
            progressState = true
        }
    }
    if (progressState) {
        val maxSec = remember(duration) { (duration / 1000).toInt() }
        val destinationSec = 0f

        ToHotAnimateTimeProgressContainerInternal(
            enable = enable,
            modifier = modifier,
            maxTimeSec = maxSec,
            destinationSec = destinationSec,
            duration = (maxSec + 1) * 1000f, // 실제 duration 은 1초 추가
            onTicChanged = {
                coroutineScope.launch {
                    if (completionDelay > 0) {
                        progressState = false
                    }
                    delay(completionDelay)
                    if (it <= destinationSec) {
                        onEnd()
                    }
                }
            }
        )
    } else {
        ToHotEmptyTimeProgressContainer(modifier = modifier)
    }
}

@Composable
private fun ToHotAnimateTimeProgressContainerInternal(
    modifier: Modifier = Modifier,
    enable: Boolean,
    maxTimeSec: Int,
    destinationSec: Float,
    progressColor: ImmutableList<Color> = persistentListOf(
        Color(0xFFF9CC2E),
        Color(0xFFF98F2E),
        Color(0xFFF93A2E)
    ),
    progressBackgroundColor: Color = colorResource(id = tht.core.ui.R.color.black_353535),
    duration: Float = ((maxTimeSec - destinationSec) * 1000),
    onTicChanged: (Float) -> Unit = { },
    completionDelayMillis: Long = 0L,
) {
    var currentSec by remember { mutableIntStateOf(maxTimeSec) }
    val destinationProgress = destinationSec / maxTimeSec.toFloat()
    var color by remember(progressColor) {
        mutableStateOf(progressColor.firstOrNull() ?: Color.Yellow )
    }
    LaunchedEffect(currentSec) {
        for (i in progressColor.indices) {
            // currentSec로 하면 색상 변경이 좀 늦어져서, 1초 뒤 변경될 progress 기준으로 계산
            val currentProgress = (currentSec - 1).coerceAtLeast(0).toFloat() / maxTimeSec
            val value = progressColor.size - 1 - i
            if (currentProgress >= (1.0f / progressColor.size) * value) {
                color = progressColor[i]
                break
            }
        }
    }
    val animateProgressColor by animateColorAsState(
        targetValue = color,
        animationSpec = tween(durationMillis = 1000),
        label = "animateProgressColor"
    )

    val progressAnimatable = remember { Animatable(1f) }
    LaunchedEffect(key1 = destinationSec, key2 = enable) {
        if (enable) {
            // 현재 progressValue -> 0.0 까지 필요한 destination 계산
            val progressDuration = duration * (progressAnimatable.value / 1f)
            progressAnimatable.animateTo(
                targetValue = destinationProgress,
                animationSpec = tween(
                    durationMillis = progressDuration.toInt(),
                    easing = LinearEasing
                )
            ) {
                currentSec = ceil((this.value * maxTimeSec)).toInt()
            }
            delay(completionDelayMillis)
            onTicChanged((progressAnimatable.value * maxTimeSec))
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
            sec = currentSec
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
        onEnd = {},
        duration = 1000
    )
}
