package tht.feature.chat.chat.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.collections.immutable.ImmutableList
import okhttp3.internal.toImmutableList
import tht.feature.chat.chat.state.ChatState
import tht.feature.chat.component.LazyColumnChatItem
import tht.feature.chat.model.ChatListUiModel

@Composable
internal fun ChatListScreen(
    navigateChatDetail: (Long, String) -> Unit = { _, _ -> },
    items: ChatState.ChatList,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumnChatItem(
            items = items.chatList,
            isLoading = items.isLoading,
            onClickItem = navigateChatDetail,
        )
    }
}


@Preview
@Composable
private fun ChatListScreenPreview(modifier: Modifier = Modifier) {
    ChatListScreen(
        navigateChatDetail = { _, _ -> },
        items = ChatState.ChatList(
            isLoading = false,
            chatList = listOf(
                ChatListUiModel(
                    chatRoomIdx = 0L,
                    partnerName = "헬로우우",
                    partnerProfileUrl = "",
                    currentMessage = "매칭된 무디와 먼저 대화를 시작해보세요.",
                    messageTime = "08:24 PM"
                ),
                ChatListUiModel(
                    chatRoomIdx = 1L,
                    partnerName = "폴링처음이에요",
                    partnerProfileUrl = "",
                    currentMessage = "매칭된 무디와 먼저 대화를 시작해보세요.",
                    messageTime = "08:25 PM"
                ),
                ChatListUiModel(
                    chatRoomIdx = 2L,
                    partnerName = "미니미니미",
                    partnerProfileUrl = "",
                    currentMessage = "매칭된 무디와 먼저 대화를 시작해보세요.",
                    messageTime = "02:23 PM"
                ),
                ChatListUiModel(
                    chatRoomIdx = 0L,
                    partnerName = "헬로우우",
                    partnerProfileUrl = "",
                    currentMessage = "매칭된 무디와 먼저 대화를 시작해보세요.",
                    messageTime = "08:24 PM"
                ),
            ).toImmutableList() as ImmutableList<ChatListUiModel>
        )
    )
}
