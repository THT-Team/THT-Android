package com.tht.tht.data.remote.request.signup

import com.google.gson.annotations.SerializedName

data class SignupRequest(
    @SerializedName("agreement")
    val agreement: Agreement,
    @SerializedName("birthDay")
    val birthDay: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("idealTypeList")
    val idealTypeList: List<Long>,
    @SerializedName("interestList")
    val interestList: List<Long>,
    @SerializedName("introduction")
    val introduction: String,
    @SerializedName("locationRequest")
    val locationRequest: LocationRequest,
    @SerializedName("phoneNumber")
    val phoneNumber: String,
    @SerializedName("photoList")
    val photoList: List<String>,
    @SerializedName("preferGender")
    val preferGender: String,
    @SerializedName("username")
    val username: String,
    @SerializedName("deviceKey")
    val fcmToken: String,
    @SerializedName("snsType")
    val snsType: String,
    @SerializedName("snsUniqueId")
    val snsUniqueId: String
) {
    data class Agreement(
        @SerializedName("locationServiceAgree")
        val locationServiceAgree: Boolean,
        @SerializedName("marketingAgree")
        val marketingAgree: Boolean,
        @SerializedName("personalPrivacyInfoAgree")
        val personalPrivacyInfoAgree: Boolean,
        @SerializedName("serviceUseAgree")
        val serviceUseAgree: Boolean
    )

    data class LocationRequest(
        @SerializedName("address")
        val address: String,
        @SerializedName("lat")
        val lat: Double,
        @SerializedName("lon")
        val lon: Double,
        @SerializedName("regionCode")
        val regionCode: Int
    )
}
