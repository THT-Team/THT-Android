package com.tht.tht.data.remote.mapper

import com.tht.tht.data.remote.response.dailyusercard.DailyUserCardResponse
import com.tht.tht.domain.dailyusercard.DailyUserCardListModel
import com.tht.tht.domain.dailyusercard.DailyUserCardModel
import com.tht.tht.domain.signup.model.IdealTypeModel
import com.tht.tht.domain.signup.model.InterestModel


fun DailyUserCardResponse.UserInfo.IdealTypeResponse.toModel(): IdealTypeModel {
    return IdealTypeModel(
        title = name,
        key = idx,
        emojiCode = emojiCode
    )
}

fun DailyUserCardResponse.UserInfo.InterestResponse.toModel(): InterestModel {
    return InterestModel(
        title = name,
        key = idx,
        emojiCode = emojiCode
    )
}

fun DailyUserCardResponse.UserInfo.toModel(): DailyUserCardModel {
    return DailyUserCardModel(
        id = userUuid,
        nickname = username,
        isBirthDay = isBirthDay,
        interests = interestResponses.map { it.toModel() },
        idealTypes = idealTypeResponseList.map { it.toModel() },
        age = age,
        address = address,
        profileImgUrl = userProfilePhotos.sortedBy { it.priority }.map { it.url },
        introduce = introduction
    )
}

fun DailyUserCardResponse.toModel(): DailyUserCardListModel {
    return DailyUserCardListModel(
        selectTopicIdx = selectDailyFallingIdx,
        topicResetTimeMill = topicExpirationUnixTime,
        cards = userInfos.map { it.toModel() }
    )
}
