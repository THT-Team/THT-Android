package tht.feature.chat.model

data class ChatListUiModel(
    val chatRoomIdx: Long,
    val partnerName: String,
    val partnerProfileUrl: String,
    val currentMessage: String,
    val messageTime: String,
)