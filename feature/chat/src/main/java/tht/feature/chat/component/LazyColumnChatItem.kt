package tht.feature.chat.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import tht.feature.chat.viewmodel.state.ImmutableListWrapper

@Composable
internal fun LazyColumnChatItem(
    items: ImmutableListWrapper<String>,
    isLoading: Boolean,
    onClickItem: () -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        state = rememberLazyListState(),
    ) {
        items(items.list) { item ->
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
        items = ImmutableListWrapper(emptyList()),
        isLoading = false,
        onClickItem = {},
    )
}
