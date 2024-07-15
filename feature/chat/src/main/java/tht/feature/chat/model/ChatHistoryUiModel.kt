package tht.feature.chat.model

data class ChatHistoryUiModel(
    val chatIdx: String,
    val sender: String,
    val senderUuid: String,
    val msg: String,
    val imgUrl: String,
    val dateTime: String,
)
