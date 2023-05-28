package tht.feature.tohot.component.progress

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ToHotAnimateTimeProgressContainer(
    modifier: Modifier = Modifier,
    enable: Boolean,
    maxTimeSec: Int,
    currentSec: Int,
    destinationSec: Int,
    ticChanged: (Int) -> Unit = { }
) {
    val progress = destinationSec.toFloat() / maxTimeSec.toFloat()
    val progressColor by animateColorAsState(
        targetValue = if (progress >= 0.7f) {
            Color(0xFFF9CC2E)
        } else if (progress >= 0.3f) {
            Color(0xFFF98F2E)
        } else {
            Color(0xFFF93A2E)
        },
        animationSpec = tween(durationMillis = 1000)
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(color = Color(0xFF1A1A1A).copy(alpha = 0.5f))
            .padding(horizontal = 10.dp, vertical = 4.dp)
    ) {
        ToHotAnimateTimeLinearProgress(
            modifier = Modifier
                .padding(start = 12.dp)
                .align(Alignment.CenterVertically),
            enable = enable,
            maxTimeSec = maxTimeSec,
            currentSec = currentSec,
            destinationSec = destinationSec,
            ticChanged = ticChanged,
            color = progressColor
        )
    }
}
