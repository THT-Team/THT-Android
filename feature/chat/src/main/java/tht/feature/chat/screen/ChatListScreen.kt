package tht.feature.chat.screen

import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import tht.feature.chat.component.LazyColumnChatItem
import tht.feature.chat.screen.detail.ChatDetailActivity
import tht.feature.chat.viewmodel.state.ChatState

@Composable
internal fun ChatListScreen(
    items: ChatState.ChatList,
) {
    val context = LocalContext.current as AppCompatActivity
    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumnChatItem(
            items = items.chatList,
            isLoading = items.isLoading,
            onClickItem = {
                context.startActivity(ChatDetailActivity.newIntent(context))
            },
        )
    }
}
