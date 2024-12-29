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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.compose_ui.common.LogComposition
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlin.math.ceil
import kotlin.time.Duration
import kotlin.time.DurationUnit

/**
 * 1. 처음 init 될 때 enable 이 false 면 currentSec, destinationSec가 같아야 함
 * 2. enable 이 true가 되면 destinationSec-=1 진행
 * 3. 단, 중간에 enable이 false가 되었다가 다시 true가 될 땐 반응하면 안됨
 *
 * 상위에서 처리해줘야 할 것 같기도 하네
 * Timer 모델 건드리면서 해보자
 */
@Composable
fun ToHotAnimateTimeProgressContainer(
    enable: Boolean,
    duration: Duration,
    onEnd: () -> Unit,
    modifier: Modifier = Modifier,
) {
    LogComposition("cwj_debug", "ToHotAnimateTimeProgressContainer")
    val maxSec = remember(duration) { duration.toInt(DurationUnit.SECONDS) }
    val destinationSec = 0f

    ToHotAnimateTimeProgressContainerInternal(
        enable = enable,
        modifier = modifier,
        maxTimeSec = maxSec,
        destinationSec = destinationSec,
        duration = (maxSec + 1) * 1000f, // 실제 duration 은 1초 추가
        onTicChanged = {
            if (it <= destinationSec) {
                onEnd()
            }
        }
    )
}

/**
 * 1. maxTime, currentSec 를 받아 ProgressBar 구성
 * 2. maxTime, destinationSec 로 목표 progress 산출
 * 3. 애니메이션 수행
 * 4. 애니메이션 수행 후 ticChanged 호출
 */
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
    onTicChanged: (Float) -> Unit = { }
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
            if (progressAnimatable.targetValue == destinationProgress) {
                /**
                 * Timer Animation 중단 후 재개할 경우, progress 는 줄어 들어 있는 상태 에서
                 * duration 은 기존 값대로 유지 되어 Animation 이 빠르게 진행 되는 문제
                 * -> 현재 progress 에 걸맞는 새로운 duration 계산
                 *
                 * 1. 한 Tic 동안 줄어 들어야 하는 progress 계산
                 * - 총 5초일 경우 1.0 / 5 -> 0.2
                 *
                 * 2. 한 Tic 동안 돌아야 하는 잔여 progress 계산
                 * - progress 가 0.85 에서 중단 된 후 재개 된다면 현재 Tic 에서 0.5 progress 진행 필요
                 * - (0.85 - (destinationSec[4] * oneTicProgress[0.25]))
                 * -> destinationSec[4] * oneTicProgress[0.25]) 값은, destinationSec 에 도달 했을 progress[0.8] 을 의미
                 *
                 * 3. 잔여 progress 를 oneTicProgress 에 곱한 후 본래 duration 에 곱해서 잔여 duration 계산
                 * - 0.05 / 0.2 => 0.25
                 * - 0.25 * 1000 => 250
                 */
                val oneTicProgress = 1 / maxTimeSec.toFloat() // 한 틱 동안 줄어 들어야 하는 progress.value
                val oneTicRemainingProgress = progressAnimatable.value - (destinationSec * oneTicProgress)
                val remainingDuration = oneTicRemainingProgress / oneTicProgress * duration
                Log.d(
                    "Timer remaining",
                    "progressAnimatable.value[${progressAnimatable.value}], " +
                        " oneTicRemainingProgress[$oneTicRemainingProgress], " +
                        "remainingDuration[$remainingDuration]"
                )
                progressAnimatable.animateTo(
                    targetValue = destinationProgress,
                    animationSpec = tween(
                        durationMillis = remainingDuration.toInt(),
                        easing = LinearEasing
                    )
                ) {
                    currentSec = ceil((this.value * maxTimeSec)).toInt()
                }
            } else {
                Log.d(
                    "Timer",
                    "duration => $duration, progress => $destinationProgress," +
                        " target : ${progressAnimatable.targetValue}, value : ${progressAnimatable.value}"
                )
                progressAnimatable.animateTo(
                    targetValue = destinationProgress,
                    animationSpec = tween(
                        durationMillis = duration.toInt(),
                        easing = LinearEasing
                    )
                ) {
                    currentSec = ceil((this.value * maxTimeSec)).toInt()
                }
            }
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

//@Composable
//@Preview
//private fun ToHotAnimateTimeProgressContainerPreview() {
//    ToHotAnimateTimeProgressContainer(
//        modifier = Modifier.padding(horizontal = 13.dp, vertical = 12.dp),
//        enable = true,
//        maxTimeSec = 5,
//        currentSec = 5f,
//        ticChanged = {},
//        destinationSec = 4f
//    )
//}
