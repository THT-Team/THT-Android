package tht.feature.tohot.component.card

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ShakingCard(
    modifier: Modifier = Modifier,
    shakingOn: Boolean,
    duration: Int = 1000,
    content: @Composable BoxScope.() -> Unit = { }
) {
    val shakingValue = 0.5f
    val infiniteTransition = rememberInfiniteTransition(label = "shaking_animation_transition")

    val shakingAnimation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = shakingValue,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = duration
                shakingValue at duration / 3 with LinearEasing
                -shakingValue at (duration / 3) * 2 with LinearEasing
                0.0f at duration with LinearEasing
            },
            repeatMode = RepeatMode.Restart
        ),
        label = "shakingAnimation"
    )

    Box(
        modifier = modifier
            .graphicsLayer {
                // 중앙을 기점으로 회전 효과를 적용
                rotationZ = if (shakingOn) {
                    shakingAnimation
                } else {
                    0f
                }
            }

    ) {
        content()
    }
}

@Composable
@Preview
private fun ShakingCardPreview() {
    ShakingCard(
        modifier = Modifier.fillMaxSize()
            .background(Color.Black),
        shakingOn = true
    )
}
