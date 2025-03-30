package tht.feature.chat.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import tht.feature.chat.component.draggableItem.DraggableItem
import tht.feature.chat.model.ChatListUiModel

@Composable
internal fun LazyColumnChatItem(
    items: ImmutableList<ChatListUiModel>,
    isLoading: Boolean,
    onClickItem: (Long, String) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        state = rememberLazyListState()
    ) {
        items(items) { item ->
            DraggableItem(
                chatItem = item,
                isLoading = isLoading,
                onClickItem = onClickItem,
                onClickDelete = {},
            )
        }
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFF000000)
internal fun LazyColumnChatItemPreview() {
    LazyColumnChatItem(
        items = listOf(
            ChatListUiModel(
                chatRoomIdx = 1L,
                partnerProfileUrl = "",
                partnerName = "스티치",
                messageTime = "08:24 PM",
                currentMessage = "오늘이지"
            )
        ).toImmutableList(),
        isLoading = false,
        onClickItem = { _, _ -> }
    )
}
