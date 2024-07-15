package tht.feature.chat.component.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.example.compose_ui.component.image.ThtImage
import com.example.compose_ui.component.spacer.Spacer
import com.example.compose_ui.component.text.caption.ThtCaption2
import com.example.compose_ui.component.text.p.ThtP2
import tht.feature.chat.model.ChatDetailInformationUiModel
import tht.feature.chat.model.ChatHistoryUiModel

@Composable
fun ChatDetailList(
    userUuid: String?,
    chatDetailInformation: ChatDetailInformationUiModel?,
    chatList: List<ChatHistoryUiModel>,
    onLoadMore: () -> Unit
) {
    val listState = rememberLazyListState()

    listState.OnTopReached() {
        onLoadMore()
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp, bottom = 69.dp),
        state = listState,
    ) {
        item {
            Spacer(modifier = Modifier.height(16.dp))
            ChatBubbleTitle(chatDetailInformation = chatDetailInformation)
            Spacer(modifier = Modifier.height(8.dp))
        }
        items(chatList) { item ->
            if (item.senderUuid == userUuid) {
                Sender(text = item.msg, updateTime = item.dateTime)
            } else {
                Receiver(
                    text = item.msg,
                    updateTime = item.dateTime,
                    isShowProfile = true,
                    userName = item.sender
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
        }
    }
}

@Composable
fun Sender(text: String, updateTime: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.End
    ) {
        ThtCaption2(
            text = updateTime,
            fontWeight = FontWeight.Normal,
            color = Color(0xFFF9FAFA),
            textAlign = TextAlign.End
        )
        Spacer(space = 8.dp)
        ThtP2(
            modifier = Modifier
                .widthIn(min = 0.dp, max = 226.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(Color(0xFFF9CC2E))
                .padding(horizontal = 10.dp, vertical = (6.5).dp),
            text = text,
            fontWeight = FontWeight.Normal,
            color = Color.Black,
            textAlign = TextAlign.Start
        )
    }
}

@Composable
fun Receiver(
    isShowProfile: Boolean,
    userName: String,
    text: String,
    updateTime: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Start
    ) {
        if (isShowProfile) {
            ThtImage(
                modifier = Modifier.clip(shape = RoundedCornerShape(6.dp)),
                src = "https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2FMjAyMTEyMjJfMjYz%2FMDAxNjQwMTA3ODUyNzgy.2vrUEWwtR7K3P-TtNzfIsdCoM73Af9YPfpDLwq_iwMUg.D5PI3qGu_Q1tGN1HaZvFJX0dWqocJEk0AsnQ5zz1RGsg.JPEG.eeducator%2Fpexels-cottonbro-3663069.jpg&type=sc960_832", // ktlint-disable max-line-length
                size = DpSize(34.dp, 34.dp)
            )
            Spacer(space = 10.dp)
        }
        Column {
            ThtP2(
                modifier = Modifier,
                text = userName,
                fontWeight = FontWeight.Normal,
                color = Color(0xFF8D8D8D),
                textAlign = TextAlign.Start
            )
            Spacer(space = 8.dp)
            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.End
            ) {
                ThtP2(
                    modifier = Modifier
                        .widthIn(min = 0.dp, max = 226.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color(0xFF222222))
                        .padding(horizontal = 10.dp, vertical = (6.5).dp),
                    text = text,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFFF9FAFA),
                    textAlign = TextAlign.Start
                )
                Spacer(space = 8.dp)
                ThtCaption2(
                    modifier = Modifier
                        .weight(1f)
                        .wrapContentWidth(align = Alignment.Start),
                    text = updateTime,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFFF9FAFA),
                    textAlign = TextAlign.Start
                )
            }
        }
    }
}

@Composable
fun LazyListState.OnTopReached(
    buffer: Int = 0,
    onLoadMore: () -> Unit,
) {
    require(buffer >= 0) { "buffer가 0보다 작습니다 - $buffer" }
    val shouldLoadMore = remember {
        derivedStateOf {
            val firstVisibleItem =
                layoutInfo.visibleItemsInfo.firstOrNull() ?: return@derivedStateOf true
            firstVisibleItem.index == 0
        }
    }

    LaunchedEffect(shouldLoadMore) {
        snapshotFlow { shouldLoadMore.value }.collect {
            if (it) onLoadMore()
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ReceiverPreview() {
    Receiver(
        text = "긴 텍스트 세줄 이상 문장은 이렇게씁니다아아아아아아아아아아아아아아아아아아아아아긴 텍스트 세줄 이상 문장은 이렇게씁니다아",
        updateTime = "3:12 PM",
        isShowProfile = false,
        userName = "stitch"
    )
}

@Preview(showBackground = true)
@Composable
fun ReceiverPreview2() {
    Receiver(
        text = "긴 텍스트",
        updateTime = "3:12 PM",
        isShowProfile = false,
        userName = "stitch"
    )
}

@Preview(showBackground = true)
@Composable
fun SenderPreview() {
    Sender(text = "안녕하세요!", updateTime = "3:12 PM")
}

@Preview(showBackground = true)
@Composable
fun SenderPreview2() {
    Sender(
        text = "긴 텍스트 세줄 이상 문장은 이렇게씁니다아아아아아아아아아아아아아아아아아아아아아긴 텍스트 세줄 이상 문장은 이렇게씁니다아",
        updateTime = "3:12 PM"
    )
}
