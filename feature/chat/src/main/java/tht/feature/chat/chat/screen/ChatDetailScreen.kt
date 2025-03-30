package tht.feature.chat.chat.screen

import android.content.Context
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.compose_ui.common.viewmodel.collectAsState
import tht.feature.chat.chat.state.ChatDetailState
import tht.feature.chat.chat.viewmodel.ChatDetailViewModel
import tht.feature.chat.component.detail.ChatDetailList
import tht.feature.chat.component.detail.ChatDetailTopAppBar
import tht.feature.chat.component.detail.ChatEditTextContainer

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun ChatDetailScreen(
    viewModel: ChatDetailViewModel = hiltViewModel(),
    onBack: () -> Unit,
    roomIdx: Long,
    partnerName: String,
    context: Context,
) {
    LaunchedEffect(Unit) {
        viewModel.getChatDetailInformation(roomIdx)
        viewModel.getUserUuid()
    }

    val state = viewModel.collectAsState().value
    val currentText = viewModel.currentText.collectAsState().value

    DisposableEffect(key1 = Unit) {
        viewModel.initStomp(roomIdx)
        viewModel.hideBottomNavigation(context)
        onDispose {
            viewModel.showBottomNavigation(context)
            viewModel.cancelStomp()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.systemBars)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            ChatDetailTopAppBar(
                title = partnerName,
                onClickBack = onBack,
                onClickReport = {},
                onClickLogout = {}
            )
            Box(modifier = Modifier.weight(1f)) {
                when (state) {
                    is ChatDetailState.ChatList -> {
                        ChatDetailList(
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
        }
        ChatEditTextContainer(
            modifier = Modifier.align(Alignment.BottomCenter),
            text = currentText,
            onChangedText = viewModel::updateCurrentText,
            onClickSend = { viewModel.onClickSent(roomIdx) }
        )
    }
}
