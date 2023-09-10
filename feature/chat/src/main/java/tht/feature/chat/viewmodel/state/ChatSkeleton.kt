package tht.feature.chat.viewmodel.state

import com.tht.tht.domain.chat.model.ChatListModel
import kotlinx.collections.immutable.toImmutableList

internal val skeletonChatList = (1..20).map {
    ChatListModel(
        chatRoomIdx = it.toLong(),
        partnerName = it.toString(),
        partnerProfileUrl = "",
        currentMessage = "",
        messageTime = ""
    )
}.toImmutableList()
