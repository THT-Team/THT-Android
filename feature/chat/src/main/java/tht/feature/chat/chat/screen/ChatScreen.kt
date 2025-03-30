package tht.feature.chat.chat.screen

import android.content.Context
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import com.example.compose_ui.common.viewmodel.collectAsState
import com.example.compose_ui.component.spacer.Spacer
import com.example.compose_ui.component.text.p.ThtP1
import tht.feature.chat.chat.state.ChatState
import tht.feature.chat.chat.viewmodel.ChatViewModel
import tht.feature.chat.component.ChatTopAppBar

@Composable
internal fun ChatScreen(
    viewModel: ChatViewModel = hiltViewModel(),
    context: Context,
    navigateChatDetail: (Long, String) -> Unit = { _, _ -> }
) {
    OnLifecycleEvent { _, event ->
        if (event == Lifecycle.Event.ON_START) {
            viewModel.showBottomNavigation(context)
        }
    }
    val state = viewModel.collectAsState().value
    ChatScreen(
        state = state,
        navigateChatDetail = navigateChatDetail,
    )
}

@Composable
internal fun ChatScreen(
    state: ChatState,
    navigateChatDetail: (Long, String) -> Unit = { _, _ -> }
) {
    Box {
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
        NewTopicTip(
            modifier = Modifier
                .graphicsLayer {
                    translationY = 32f
                }
                .align(Alignment.TopCenter)
        )
    }
}

@Composable
@Preview
private fun BoxScope.NewTopicTip(
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .statusBarsPadding()
            .clip(RoundedCornerShape(30.dp))
            .background(Color(0xFF222222))
            .padding(horizontal = 17.dp, vertical = 14.dp)
    ) {
        ThtP1(
            text = "새로운 주제어가 오픈되었어요!",
            fontWeight = FontWeight.W400,
            color = Color.White
        )
        Spacer(9.dp)
        ThtP1(
            text = "메인화면으로 이동",
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFFF9CC2E),
        )
    }
}

@Preview
@Composable
private fun ChatScreenPreview() {
    ChatScreen(
        state = ChatState.Empty
    )
}
