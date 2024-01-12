package tht.feature.chat.viewmodel.state

import kotlinx.collections.immutable.toImmutableList
import tht.feature.chat.model.ChatListUiModel

internal val skeletonChatList = (1..20).map {
    ChatListUiModel(
        chatRoomIdx = it.toLong(),
        partnerName = it.toString(),
        partnerProfileUrl = "",
        currentMessage = "",
        messageTime = ""
    )
}.toImmutableList()
