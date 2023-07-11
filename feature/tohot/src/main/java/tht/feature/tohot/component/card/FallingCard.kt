package tht.feature.tohot.component.card

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun FallingCard(
    modifier: Modifier = Modifier,
    fallingProgress: Float,
    maxAngle: Float = 30f,
    fallingToLeft: Boolean = true,
    content: @Composable BoxScope.() -> Unit = { }
) {
    val direction = if (fallingToLeft) -1 else 1
    Box(
        modifier = modifier
            .graphicsLayer {
                // 카드 회전 애니메이션 적용
                rotationZ = fallingProgress * maxAngle * direction
                transformOrigin = TransformOrigin(0f, 1f)
            }
            .background(Color.Blue)
    ) {
        content()
    }
}

@Composable
@Preview
private fun FallingCardPreview() {
    FallingCard(
        modifier = Modifier.fillMaxSize(),
        fallingProgress = 0.5f
    )
}
