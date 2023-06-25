package tht.feature.chat.screen.detail.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.compose_ui.common.viewmodel.collectAsState
import tht.feature.chat.component.detail.ChatDetailList
import tht.feature.chat.component.detail.ChatDetailTopAppBar
import tht.feature.chat.component.detail.ChatEditTextContainer
import tht.feature.chat.viewmodel.detail.ChatDetailViewModel
import tht.feature.chat.viewmodel.detail.state.ChatDetailState

@Composable
internal fun ChatDetailScreen(
    viewModel: ChatDetailViewModel = hiltViewModel(),
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.getChatList()
    }

    val state = viewModel.collectAsState().value
    val currentText = viewModel.currentText.collectAsState().value
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            ChatDetailTopAppBar(
                title = "마음",
                onClickBack = { },
                onClickReport = {},
                onClickLogout = {},
            )
            Box(modifier = Modifier.weight(1f)) {
                when (state) {
                    is ChatDetailState.ChatList -> ChatDetailList(state.chatList)
                }
            }
        }
        ChatEditTextContainer(
            modifier = Modifier.align(Alignment.BottomCenter),
            text = currentText,
            onChangedText = viewModel::updateCurrentText,
        )
    }
}

@Composable
@Preview(showBackground = true)
fun ChatDetailScreenPreview() {
    ChatDetailScreen()
}
