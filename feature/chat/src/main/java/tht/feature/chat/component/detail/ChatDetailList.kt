package tht.feature.chat.component.detail

import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.example.compose_ui.component.image.ThtImage
import com.example.compose_ui.component.spacer.Spacer
import com.example.compose_ui.component.text.caption.ThtCaption2
import com.example.compose_ui.component.text.p.ThtP2
import tht.feature.chat.model.ChatDetailInformationUiModel
import tht.feature.chat.model.ChatHistoryUiModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ChatDetailList(
    userUuid: String?,
    chatDetailInformation: ChatDetailInformationUiModel?,
    chatList: List<ChatHistoryUiModel>,
    onLoadMore: () -> Unit
) {
    val listState = rememberLazyListState()
    var previousPosition: Int? by remember {
        mutableStateOf(null)
    }
    var isScrolling by remember {
        mutableStateOf(false)
    }

    listState.OnTopReached(buffer = 10) {
        previousPosition = chatList.size
        onLoadMore()
    }

    LaunchedEffect(chatList, !isScrolling) {
        if (chatList.isNotEmpty()) {
            listState.scrollToItem(chatList.lastIndex)
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp, bottom = 69.dp),
        state = listState,
    ) {
        stickyHeader {
            Spacer(modifier = Modifier.height(16.dp))
            ChatBubbleTitle(chatDetailInformation = chatDetailInformation)
            Spacer(modifier = Modifier.height(8.dp))
        }
        itemsIndexed(chatList) { index, item ->
            val isSameUser =
                if (index != 0 && chatList[index - 1].senderUuid != userUuid) true else if (index == 0) null else false
            if (item.senderUuid == userUuid) {
                MyChat(item)
            } else {
                OtherChat(item, isSameUser = isSameUser, isShowProfile = true)
            }
            Spacer(modifier = Modifier.height(6.dp))
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
                layoutInfo.visibleItemsInfo.firstOrNull() ?: return@derivedStateOf false
            firstVisibleItem.index == 0
        }
    }
    LaunchedEffect(shouldLoadMore) {
        snapshotFlow { shouldLoadMore.value }.collect {
            if (it) onLoadMore()
        }
    }
}


@Composable
fun OtherChat(
    chat: ChatHistoryUiModel,
    isShowProfile: Boolean,
    isSameUser: Boolean?,
) {
    val screenWidthDp = with(LocalDensity.current) {
        LocalContext.current.resources.displayMetrics.widthPixels.toDp()
    }
    val maxWidthDp = screenWidthDp * 0.6f
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Start
    ) {
        if (isSameUser == false || isSameUser == null) {
            if (isShowProfile) {
                ThtImage(
                    modifier = Modifier.clip(shape = RoundedCornerShape(6.dp)),
                    src = chat.imgUrl,
                    size = DpSize(34.dp, 34.dp)
                )
                Spacer(space = 10.dp)
            }
        }
        if (isSameUser == true && isShowProfile) Spacer(space = 44.dp)
        Column {
            if (isSameUser == false || isSameUser == null) {
                ThtP2(
                    modifier = Modifier,
                    text = chat.sender,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFF8D8D8D),
                    textAlign = TextAlign.Start
                )
                Spacer(space = 8.dp)
            }

            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.End
            ) {
                ThtP2(
                    modifier = Modifier
                        .background(Color(0xFF222222), shape = RoundedCornerShape(20.dp))
                        .padding(horizontal = 10.dp, vertical = (6.5).dp)
                        .widthIn(max = maxWidthDp)
                        .wrapContentWidth(),
                    text = chat.msg,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFFF9FAFA),
                    textAlign = TextAlign.Start
                )
                Spacer(space = 8.dp)
                ThtCaption2(
                    modifier = Modifier
                        .weight(1f)
                        .wrapContentWidth(align = Alignment.Start),
                    text = chat.dateTime,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFFF9FAFA),
                    textAlign = TextAlign.Start
                )
            }
        }
    }
}

@Composable
fun MyChat(
    chat: ChatHistoryUiModel
) {
    val screenWidthDp = with(LocalDensity.current) {
        LocalContext.current.resources.displayMetrics.widthPixels.toDp()
    }
    val maxWidthDp = screenWidthDp * 0.6f
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.Bottom
    ) {
        ThtCaption2(
            text = chat.dateTime,
            fontWeight = FontWeight.Normal,
            color = Color(0xFFF9FAFA),
            textAlign = TextAlign.End
        )
        Spacer(space = 8.dp)
        ThtP2(
            modifier = Modifier
                .background(Color(0xFFF9CC2E), RoundedCornerShape(20.dp))
                .padding(horizontal = 10.dp, vertical = (6.5).dp)
                .widthIn(max = maxWidthDp),
            text = chat.msg,
            fontWeight = FontWeight.Normal,
            color = Color.Black,
            textAlign = TextAlign.Start
        )
    }
}
