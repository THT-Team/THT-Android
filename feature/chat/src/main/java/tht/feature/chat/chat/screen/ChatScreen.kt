package tht.feature.chat.chat.screen

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.compose_ui.common.viewmodel.collectAsState
import tht.feature.chat.chat.state.ChatState
import tht.feature.chat.chat.viewmodel.ChatViewModel
import tht.feature.chat.component.ChatTopAppBar

@Composable
internal fun ChatScreen(
    viewModel: ChatViewModel = hiltViewModel(),
    navigateChatDetail: (Long, String) -> Unit = { _, _ -> }
) {
    val state = viewModel.collectAsState().value
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .windowInsetsPadding(WindowInsets.systemBars)
    ) {
        ChatTopAppBar(
            title = "채팅",
            rightIcons = {
                Image(
                    painter = painterResource(id = tht.feature.chat.R.drawable.ic_bling),
                    contentDescription = null
                )
            }
        )

        Crossfade(
            modifier = Modifier.fillMaxSize(),
            targetState = state,
            animationSpec = tween(400),
            label = ""
        ) { state ->
            when (state) {
                is ChatState.Empty -> ChatEmptyScreen(onClickChangeTitle = {})
                is ChatState.ChatList -> ChatListScreen(items = state, navigateChatDetail = navigateChatDetail)
            }
        }
    }
}
