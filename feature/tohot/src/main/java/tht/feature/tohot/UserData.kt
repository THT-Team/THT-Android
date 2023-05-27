package tht.feature.tohot

import com.tht.tht.domain.signup.model.SignupUserModel

internal val userData = SignupUserModel(
    phone = "01012345678",
    termsAgreement = emptyMap(),
    nickname = "nick",
    email = "email",
    gender = "BISEXUAL",
    birthday = "2022.02.03",
    interestKeys = listOf(15L, 12L, 20L),
    idealTypeKeys = listOf(8L, 9L, 6L),
    lat = 37.487266399999996,
    lng = 126.9607512,
    address = "address",
    preferredGender = "BISEXUAL",
    profileImgUrl = listOf(
        "https://firebasestorage.googleapis.com/v0/b/tht-android-a954a.appspot.com/o/1684673205398_0?alt=media&token=93c6fbbf-20bd-4d43-953c-50c0722c31c2",
        "https://firebasestorage.googleapis.com/v0/b/tht-android-a954a.appspot.com/o/1684673205398_1?alt=media&token=14048744-7aa1-4872-bbc4-aa013f4a6c33"
    ),
    introduce = "introduce"
)
