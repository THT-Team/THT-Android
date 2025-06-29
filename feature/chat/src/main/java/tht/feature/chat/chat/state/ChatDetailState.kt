package tht.feature.chat.chat.state

import tht.feature.chat.model.ChatDetailInformationUiModel
import tht.feature.chat.model.ChatHistoryUiModel
import tht.feature.setting.uimodel.MyPageUserInfoUiModel

sealed class ChatDetailState {
    data class ChatList(
        val isLoading: Boolean,
        val chatDetailInformation: ChatDetailInformationUiModel? = null,
        val chatList: List<ChatHistoryUiModel> = emptyList(),
        val chatIdx: String? = null,
        val userUuid: String? = null,
        val userInformation: MyPageUserInfoUiModel? = null,
        val showOptionDialog: Boolean = false,
        val showReportDialog: Boolean = false,
        val showBlockDialog: Boolean = false,
        val showExitDialog: Boolean = false,
        val showProfileDetailDialog: Boolean = false,
    ) : ChatDetailState()
}
