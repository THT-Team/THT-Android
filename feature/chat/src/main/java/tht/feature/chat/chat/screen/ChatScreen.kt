package tht.feature.chat.chat.screen

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.compose_ui.R
import com.example.compose_ui.common.viewmodel.collectAsState
import tht.feature.chat.component.ChatTopAppBar
import tht.feature.chat.chat.viewmodel.ChatViewModel
import tht.feature.chat.chat.state.ChatState

@Composable
internal fun ChatScreen(
    viewModel: ChatViewModel = hiltViewModel(),
    navigateChatDetail: () -> Unit = { }
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.getFakeChatList()
    }

    val state = viewModel.collectAsState().value
    Column(modifier = Modifier.fillMaxWidth()) {
        ChatTopAppBar(
            title = "채팅",
            rightIcons = {
                Icon(
                    tint = Color.White,
                    painter = painterResource(id = R.drawable.ic_non_alert),
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
