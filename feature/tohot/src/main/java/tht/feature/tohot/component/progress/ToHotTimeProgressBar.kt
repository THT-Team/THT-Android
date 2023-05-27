package tht.feature.tohot.component.progress

import android.util.Log
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ToHotAnimateTimeLinearProgress(
    modifier: Modifier = Modifier,
    maxTimeSec: Int,
    currentSec: Int,
    ticChanged: (Int) -> Unit = { }
) {
    //5초중 1초 -> 0.2
    var progress by remember { mutableStateOf((currentSec.toFloat() / maxTimeSec.toFloat())) }
    Log.d("progress", "$progress")
    val progressAnimation by animateFloatAsState(
        targetValue = progress, // 0.1 ~ 1.0
        animationSpec = tween(
            durationMillis = 1000,
            easing = FastOutLinearInEasing
        )
    )
    ToHotTimeProgressBar(
        modifier = modifier,
        progress = progressAnimation
    )
    LaunchedEffect(key1 = currentSec, key2 = maxTimeSec) {
        progress = currentSec.toFloat() / maxTimeSec.toFloat()
        ticChanged((progress * maxTimeSec).toInt())
    }
}

@Composable
fun ToHotTimeProgressBar(
    modifier: Modifier = Modifier,
    progress: Float
) {
    LinearProgressIndicator(
        modifier = modifier.fillMaxWidth(),
        progress = progress,
        backgroundColor = Color(0xFF222222),
        color = Color(0xFFF9CC2E)
    )
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFF000000, name = "AnimateProgress")
private fun ToHotAnimateTimeProgressBarPreview() {
    ToHotAnimateTimeLinearProgress(
        maxTimeSec = 5,
        currentSec = 3
    )
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFF000000, name = "progress")
private fun ToHotTimeProgressBarPreview(
    progress: Float
) {
    ToHotTimeProgressBar(
        progress = (3 / 5).toFloat()
    )
}
