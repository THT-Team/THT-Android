package tht.feature.chat.chat.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Text
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.compose_ui.common.viewmodel.collectAsState
import com.example.compose_ui.component.dialog.ThtDialog
import com.example.compose_ui.component.text.subtitle.ThtSubtitle1
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

    DisposableEffect(key1 = Unit) {
        viewModel.initStomp(roomIdx)
        onDispose {
            viewModel.cancelStomp()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.systemBars)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .imePadding()
        ) {
            ChatDetailTopAppBar(
                title = partnerName,
                onClickBack = onBack,
                onClickReport = { viewModel.updateOptionDialogState(true) },
                onClickLogout = { viewModel.updateChatExitDialogState(true) }
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

    if (state is ChatDetailState.ChatList) {
        if (state.showOptionDialog) {
            ThtDialog(
                onDismissRequest = { viewModel.updateOptionDialogState(false) },
                buttonBuilder = {
                    OptionDialog(
                        onClickReport = { viewModel.updateReportDialogState(true) },
                        onClickBlock = { viewModel.updateBlockDialogState(true) },
                    )
                }
            )
        }

        if (state.showReportDialog) {
            val reasons = listOf(
                "불괘한 사진", "허위 프로필", "사진 도용", "욕설 및 비방", "불법 촬영물 공유"
            )
            ThtDialog(
                onDismissRequest = { viewModel.updateReportDialogState(false) },
                title = { Text(text = "어떤 문제가 있나요?") },
                content = {
                    reasons.forEachIndexed { index, reason ->
                        ThtSubtitle1(
                            modifier = Modifier
                                .fillMaxWidth(),
                            text = reason,
                            fontWeight = FontWeight.Normal,
                            color = Color(0xFFF9FAFA),
                            maxLines = 1,
                            textAlign = TextAlign.Center,
                        )
                        if (index != reasons.lastIndex) {
                            Spacer(modifier = Modifier.height(20.dp))
                        } else {
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }
                },
                buttonBuilder = {
                    HorizontalDivider(
                        modifier = Modifier.fillMaxWidth(),
                        thickness = (1.5).dp,
                        color = Color(0xFF666666)
                    )
                    ThtSubtitle1(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { viewModel.updateReportDialogState(false) }
                            .padding(vertical = 16.dp),
                        text = "취소",
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFFF9FAFA),
                        maxLines = 1,
                        textAlign = TextAlign.Center,
                    )
                }
            )
        }

        if (state.showBlockDialog) {
            ThtDialog(
                onDismissRequest = { viewModel.updateBlockDialogState(false) },
                title = { Text(text = "차단할까요?") },
                description = { Text(text = "해당 사용자와 서로 차단되며,\n진행중인 채팅방은 삭제됩니다.") },
                buttonBuilder = {
                    ThtSubtitle1(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        text = "차단하기",
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFFF9FAFA),
                        maxLines = 1,
                        textAlign = TextAlign.Center,
                    )
                    HorizontalDivider(
                        modifier = Modifier.fillMaxWidth(),
                        thickness = (1.5).dp,
                        color = Color(0xFF666666)
                    )
                    ThtSubtitle1(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { viewModel.updateBlockDialogState(false) }
                            .padding(vertical = 16.dp),
                        text = "취소",
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFFF9FAFA),
                        maxLines = 1,
                        textAlign = TextAlign.Center,
                    )
                }
            )
        }

        if(state.showExitDialog) {
            ThtDialog(
                onDismissRequest = { viewModel.updateChatExitDialogState(false) },
                title = { Text(text = "채팅을 종료할까요?") },
                description = { Text(text = "종료 후 채팅을 이어갈 수 없어요.")},
                buttonBuilder = {
                    ThtSubtitle1(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        text = "나가기",
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFFF9FAFA),
                        maxLines = 1,
                        textAlign = TextAlign.Center,
                    )
                    HorizontalDivider(
                        modifier = Modifier.fillMaxWidth(),
                        thickness = (1.5).dp,
                        color = Color(0xFF666666)
                    )
                    ThtSubtitle1(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { viewModel.updateChatExitDialogState(false) }
                            .padding(vertical = 16.dp),
                        text = "취소",
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFFF9FAFA),
                        maxLines = 1,
                        textAlign = TextAlign.Center,
                    )
                }
            )
        }
    }
}

@Composable
fun OptionDialog(
    onClickReport: () -> Unit = {},
    onClickBlock: () -> Unit = {},
) {
    ThtSubtitle1(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClickReport() }
            .padding(vertical = 16.dp),
        text = "신고하기",
        fontWeight = FontWeight.SemiBold,
        color = Color(0xFFF9FAFA),
        maxLines = 1,
        textAlign = TextAlign.Center,
    )
    HorizontalDivider(
        modifier = Modifier.fillMaxWidth(),
        thickness = (1.5).dp,
        color = Color(0xFF666666)
    )
    ThtSubtitle1(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClickBlock() }
            .padding(vertical = 16.dp),
        text = "차단하기",
        fontWeight = FontWeight.SemiBold,
        color = Color(0xFFF9FAFA),
        maxLines = 1,
        textAlign = TextAlign.Center,
    )
}
