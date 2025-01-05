package tht.feature.tohot.component.card

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose_ui.component.text.headline.ThtHeadline5
import com.example.compose_ui.component.text.p.ThtP1
import com.example.compose_ui.component.text.p.ThtP2
import kotlinx.coroutines.delay
import tht.core.ui.R
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

@Composable
fun TopicSelectTypeCardScreen(
    isEvent: Boolean,
    topicExpiredDuration: Duration,
    introduce: String,
    buttonEnabled: Boolean,
    modifier: Modifier = Modifier,
    onClickConfirm: () -> Unit = { },
    topicsContent: @Composable ColumnScope.() -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        colorResource(R.color.black_1D1D1D),
                        colorResource(R.color.black_161616),
                        colorResource(R.color.black_1D1D1D)
                    )
                ),
                shape = RoundedCornerShape(12.dp)
            )
            .border(
                width = 1.dp,
                brush = Brush.linearGradient(
                    colors = listOf(
                        colorResource(R.color.gray_8d8d8d).copy(alpha = 0.3f),
                        colorResource(R.color.black_414141).copy(alpha = 0.9f)
                    )
                ),
                shape = RoundedCornerShape(12.dp)
            )
            .clipToBounds()
            .padding(horizontal = 16.dp, vertical = 17.dp)
    ) {
        Spacer(modifier = Modifier.height(15.dp))
        ThtP1(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = "NEW TOPIC",
            fontWeight = FontWeight.Medium,
            color = colorResource(R.color.gray_666666)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (isEvent) {
                ThtHeadline5(
                    text = "[이벤트] ",
                    fontWeight = FontWeight.SemiBold,
                    color = colorResource(R.color.yellow_f9cc2e)
                )
            }
            ThtHeadline5(
                text = introduce,
                fontWeight = FontWeight.SemiBold,
                color = colorResource(R.color.white_f9fafa)
            )
        }
        Spacer(modifier = Modifier.height(24.dp))

        topicsContent()

        Spacer(modifier = Modifier.height(34.dp))

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 22.dp)
                .heightIn(min = 54.dp)
                .clip(RoundedCornerShape(12.dp)),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = colorResource(id = R.color.yellow_f9cc2e),
                contentColor = colorResource(id = R.color.black_222222)
            ),
            enabled = buttonEnabled,
            onClick = onClickConfirm
        ) {
            ThtHeadline5(
                text = stringResource(id = tht.feature.tohot.R.string.starting),
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.black_222222)
            )
        }

        Spacer(modifier = Modifier.height(18.dp))
        Row(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ThtP2(
                text = "다음 주제어까지",
                fontWeight = FontWeight.Normal,
                color = colorResource(R.color.gray_8d8d8d)
            )
            Spacer(modifier = Modifier.width(8.dp))
            DurationText(topicExpiredDuration)
        }
        Spacer(modifier = Modifier.height(18.dp))
    }
}

@Composable
private fun DurationText(duration: Duration) {
    // 시간을 표현하는 상태 변수
    val timeText by rememberCountdownTimer(duration)

    ThtP1(
        text = timeText,
        fontWeight = FontWeight.Medium,
        color = colorResource(R.color.white_f9fafa)
    )
}

@Composable
fun rememberCountdownTimer(duration: Duration): MutableState<String> {
    val timeText = remember(duration) { mutableStateOf(duration.toIsoString()) }
    LaunchedEffect(key1 = duration) {
        // 초기 설정된 duration으로부터 시간을 감소시키는 반복 태스크
        var remainingTime = duration
        while (remainingTime.inWholeSeconds > 0) {
            delay(1000) // 1초 대기
            remainingTime -= 1.seconds // 1초 감소
            timeText.value = remainingTime.toIsoString() // 새 시간을 텍스트로 업데이트
        }
    }
    return timeText
}

@Composable
@Preview
private fun TopicSelectTypeCardScreenEventPreview() {
    TopicSelectTypeCardScreen(
        isEvent = true,
        introduce = "introduce",
        topicExpiredDuration = Duration.ZERO,
        buttonEnabled = true
    ) {
        Box(modifier = Modifier.weight(1f))
    }
}

@Composable
@Preview
private fun TopicSelectTypeCardScreenPreview() {
    TopicSelectTypeCardScreen(
        isEvent = false,
        introduce = "introduce",
        topicExpiredDuration = Duration.ZERO,
        buttonEnabled = true
    ) {
        Box(modifier = Modifier.weight(1f))
    }
}
