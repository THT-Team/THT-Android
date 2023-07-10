package com.tht.tht.domain.dailyusercard

import com.tht.tht.domain.signup.model.IdealTypeModel
import com.tht.tht.domain.signup.model.InterestModel

data class DailyUserCardModel(
    val id: String,
    val nickname: String,
    val isBirthDay: Boolean,
    val interests: List<InterestModel>,
    val idealTypes: List<IdealTypeModel>,
    val age: Int,
    val address: String,
    val profileImgUrl: List<String>,
    val introduce: String
)
