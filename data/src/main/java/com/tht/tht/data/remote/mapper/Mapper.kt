package com.tht.tht.data.remote.mapper

import com.tht.tht.data.remote.response.ideal.IdealTypeResponse
import com.tht.tht.data.remote.response.interests.InterestTypeResponse
import com.tht.tht.domain.signup.model.IdealTypeModel
import com.tht.tht.domain.signup.model.InterestModel

fun InterestTypeResponse.toModel(): InterestModel {
    return InterestModel(
        title = name,
        key = idx,
        emojiCode = emojiCode
    )
}

fun IdealTypeResponse.toModel(): IdealTypeModel {
    return IdealTypeModel(
        title = name,
        key = idx,
        emojiCode = emojiCode
    )
}
