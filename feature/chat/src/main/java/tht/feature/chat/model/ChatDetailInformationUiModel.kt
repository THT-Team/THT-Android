package tht.feature.chat.model

data class ChatDetailInformationUiModel(
    val chatRoomIdx: Long,
    val talkSubject: String,
    val talkIssue: String,
    val startDate: String,
    val isChatAble: Boolean
)
