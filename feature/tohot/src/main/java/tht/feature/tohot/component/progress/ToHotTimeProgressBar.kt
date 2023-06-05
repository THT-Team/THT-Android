package tht.feature.tohot.component.progress

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.tooling.preview.Preview

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
@Preview(showBackground = true, backgroundColor = 0xFF000000, name = "progress")
private fun ToHotTimeProgressBarPreview() {
    ToHotTimeProgressBar(
        progress = 0.4f
    )
}
