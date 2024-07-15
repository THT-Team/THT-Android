package tht.feature.chat.chat.screen

import android.widget.Toast
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
import tht.feature.chat.chat.state.ChatDetailState
import tht.feature.chat.chat.viewmodel.ChatDetailViewModel
import tht.feature.chat.component.detail.ChatDetailList
import tht.feature.chat.component.detail.ChatDetailTopAppBar
import tht.feature.chat.component.detail.ChatEditTextContainer

@Composable
internal fun ChatDetailScreen(
    viewModel: ChatDetailViewModel = hiltViewModel(),
    onBack: () -> Unit,
    roomIdx: Long,
    partnerName: String,
) {

    LaunchedEffect(Unit) {
        viewModel.getChatDetailInformation(roomIdx)
        viewModel.getUserUuid()
    }

    val state = viewModel.collectAsState().value
    val currentText = viewModel.currentText.collectAsState().value
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            ChatDetailTopAppBar(
                title = partnerName,
                onClickBack = onBack,
                onClickReport = {},
                onClickLogout = {}
            )
            Box(modifier = Modifier.weight(1f)) {
                when (state) {
                    is ChatDetailState.ChatList -> ChatDetailList(
                        userUuid = state.userUuid,
                        chatDetailInformation = state.chatDetailInformation,
                        chatList = state.chatList,
                        onLoadMore = {
                            viewModel.getChatHistory(roomIdx)
                        }
                    )
                }
            }
        }
        ChatEditTextContainer(
            modifier = Modifier.align(Alignment.BottomCenter),
            text = currentText,
            onChangedText = viewModel::updateCurrentText
        )
    }
}

@Composable
@Preview(showBackground = true)
fun ChatDetailScreenPreview() {
    ChatDetailScreen(roomIdx = 0, onBack = {}, partnerName = "")
}
