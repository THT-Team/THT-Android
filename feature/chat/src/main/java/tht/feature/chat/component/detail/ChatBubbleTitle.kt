package tht.feature.chat.component.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose_ui.component.spacer.Spacer
import tht.feature.chat.component.detail.bubble.ArrowAlignment
import tht.feature.chat.component.detail.bubble.ArrowShape
import tht.feature.chat.component.detail.bubble.BubbleLayout
import tht.feature.chat.component.detail.bubble.BubbleShadow
import tht.feature.chat.component.detail.bubble.rememberBubbleState
import tht.feature.chat.model.ChatDetailInformationUiModel

@Composable
fun ChatBubbleTitle(chatDetailInformation: ChatDetailInformationUiModel?) {
    if (chatDetailInformation == null) return

    val bubbleStateBottom = rememberBubbleState(
        alignment = ArrowAlignment.BottomCenter,
        arrowShape = ArrowShape.FullTriangle,
        cornerRadius = 24.dp
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BubbleLayout(
            modifier = Modifier
                .fillMaxWidth(),
            bubbleState = bubbleStateBottom,
            backgroundColor = Color(0xFF252525),
            shadow = BubbleShadow(
                elevation = 0.dp
            )
        ) {
            Row(
                modifier = Modifier
                    .padding(vertical = 8.dp, horizontal = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    modifier = Modifier
                        .border(width = 1.dp, color = Color(0xFFF9CC2E), shape = RoundedCornerShape(24.dp))
                ) {
                    Text(
                        modifier = Modifier
                            .padding(vertical = 4.dp, horizontal = 10.dp),
                        text = chatDetailInformation.talkSubject,
                        color = Color(0xFFF9CC2E)
                    )
                }
                Spacer(space = 8.dp)
                Text(
                    modifier = Modifier,
                    text = chatDetailInformation.talkIssue,
                    color = Color.White
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ChatBubbleTitlePreview() {
    Column(modifier = Modifier.background(Color.Black)) {
        ChatBubbleTitle(
            chatDetailInformation = ChatDetailInformationUiModel(
                chatRoomIdx = 0L,
                talkSubject = "행복",
                talkIssue = "오늘 행복한 하루 보내셨나요?",
                startDate = "2024년 7월 11일 목요일",
                isChatAble = true,
            )
        )
    }
}

