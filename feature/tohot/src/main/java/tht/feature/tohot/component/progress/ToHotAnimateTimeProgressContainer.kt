package tht.feature.tohot.component.progress

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ToHotAnimateTimeProgressContainer(
    modifier: Modifier = Modifier,
    maxTimeSec: Int,
    currentSec: Int,
    ticChanged: (Int) -> Unit = { }
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(color = Color(0xFF1A1A1A).copy(alpha = 0.5f))
            .padding(horizontal = 10.dp, vertical = 4.dp)
    ) {
        ToHotAnimateTimeLinearProgress(
            modifier = Modifier.padding(start = 12.dp),
            maxTimeSec = maxTimeSec,
            currentSec = currentSec,
            ticChanged = ticChanged
        )
    }
}
