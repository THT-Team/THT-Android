package tht.feature.chat.screen.chatlist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import tht.feature.chat.component.LazyColumnChatItem
import tht.feature.chat.viewmodel.state.ChatState

@Composable
internal fun ChatListScreen(
    navigateChatDetail: () -> Unit = { },
    items: ChatState.ChatList,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumnChatItem(
            items = items.chatList,
            isLoading = items.isLoading,
            onClickItem = {
                navigateChatDetail()
            },
        )
    }
}
