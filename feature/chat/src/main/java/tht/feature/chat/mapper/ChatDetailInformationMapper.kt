package tht.feature.chat.mapper

import com.tht.tht.domain.chat.model.ChatDetailInformationModel
import tht.feature.chat.model.ChatDetailInformationUiModel

fun ChatDetailInformationModel.toModel() = ChatDetailInformationUiModel(
    chatRoomIdx = chatRoomIdx,
    talkSubject = talkSubject,
    talkIssue = talkIssue,
    startDate = startDate,
    isChatAble = isChatAble,
)
