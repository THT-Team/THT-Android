package tht.feature.tohot.tohot.screen

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose_ui.component.font.rememberPretendardFontStyle
import com.example.compose_ui.component.text.headline.ThtHeadline4
import com.example.compose_ui.component.text.headline.ThtHeadline5
import com.example.compose_ui.component.text.p.ThtP1
import com.example.compose_ui.extensions.dpTextUnit
import tht.core.ui.R
import tht.feature.tohot.component.animation.shakingAnimation

@Composable
fun TopicResetScreen(
    modifier: Modifier = Modifier,
    remainingTime: String,
    animationStart: Boolean
) {
    val rotateAngleAnimate = remember { Animatable(0f) }
    LaunchedEffect(key1 = animationStart) {
        if (animationStart) {
            shakingAnimation(
                rotateAngle = rotateAngleAnimate,
                animateAngle = 5f,
                totalDuration = 600,
                repeatCount = 3
            )
        }
    }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = colorResource(id = R.color.black_222222)
            )
    ) {
        Row(
            modifier = Modifier.padding(start = 30.dp, top = 22.dp, end = 30.dp)
        ) {
            ThtHeadline4(
                modifier = Modifier.weight(1f),
                text = stringResource(id = tht.feature.tohot.R.string.select_topic_title),
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.white_f9fafa),
                textAlign = TextAlign.Start
            )

            Image(
                painter = painterResource(id = tht.feature.tohot.R.drawable.ic_stopwatch_green),
                contentDescription = "ic_stopwatch_green"
            )

            ThtHeadline5(
                modifier = Modifier.padding(start = 8.dp),
                text = remainingTime,
                fontWeight = FontWeight.ExtraBold,
                color = colorResource(id = R.color.green_2ef95a)
            )
        }

        Spacer(modifier = Modifier.height(22.dp))

        Box(
            modifier = Modifier
                .padding(top = 65.dp, bottom = 48.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = tht.feature.tohot.R.drawable.ic_topic_reset_background),
                contentDescription = "ic_topic_reset_background"
            )

            Image(
                modifier = Modifier
                    .graphicsLayer {
                        rotationZ = rotateAngleAnimate.value // z축 기점 회전
                    },
                painter = painterResource(id = tht.feature.tohot.R.drawable.ic_topic_reset),
                contentDescription = "ic_topic_reset"
            )
        }

        // ThtHeadline4
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(color = colorResource(id = R.color.green_2ef95a))
                ) {
                    append(stringResource(id = tht.feature.tohot.R.string.to_hot_topic_reset_1))
                }
                withStyle(
                    style = SpanStyle(color = colorResource(id = R.color.white_f9fafa))
                ) {
                    append(stringResource(id = tht.feature.tohot.R.string.to_hot_topic_reset_2))
                }
                withStyle(
                    style = SpanStyle(color = colorResource(id = R.color.green_2ef95a))
                ) {
                    append(stringResource(id = tht.feature.tohot.R.string.reset))
                }
                withStyle(
                    style = SpanStyle(color = colorResource(id = R.color.white_f9fafa))
                ) {
                    append(stringResource(id = tht.feature.tohot.R.string.to_hot_topic_reset_3))
                }
            },
            textAlign = TextAlign.Center,
            style = rememberPretendardFontStyle(
                fontWeight = FontWeight.SemiBold,
                fontSize = 19.dpTextUnit
            )
        )

        ThtP1(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp),
            text = stringResource(id = tht.feature.tohot.R.string.to_hot_topic_reset_description),
            fontWeight = FontWeight.Normal,
            color = colorResource(id = R.color.gray_8d8d8d)
        )

        Spacer(modifier = Modifier.height(36.dp))
    }
}

@Composable
@Preview
private fun TopicResetScreenPreview() {
    TopicResetScreen(
        remainingTime = "24:00:00",
        animationStart = false
    )
}
