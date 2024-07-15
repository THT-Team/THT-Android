package tht.feature.chat.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
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
            ChatItem(
                item = item,
                isLoading = isLoading,
                onClickItem = onClickItem
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
        onClickItem = { _, _ -> }
    )
}
