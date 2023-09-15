package tht.feature.chat.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.tht.tht.domain.chat.model.ChatListModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import tht.feature.chat.model.ChatListUiModel

@Composable
internal fun LazyColumnChatItem(
    items: ImmutableList<ChatListUiModel>,
    isLoading: Boolean,
    onClickItem: () -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        state = rememberLazyListState(),
    ) {
        items(items) { item ->
            ChatItem(
                item = item,
                isLoading = isLoading,
                onClickItem = onClickItem,
            )
        }
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFF000000)
internal fun LazyColumnChatItemPreview() {
    LazyColumnChatItem(
        items = persistentListOf(),
        isLoading = false,
        onClickItem = {},
    )
}
