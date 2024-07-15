package tht.feature.chat.chat.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import tht.feature.chat.chat.state.ChatState
import tht.feature.chat.component.LazyColumnChatItem

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
