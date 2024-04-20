package com.tht.tht.data.remote.datasource.setting

import com.tht.tht.data.remote.response.setting.MyPageUserInfoResponse

interface UserSettingDataSource {
    suspend fun fetchMyPageUserInfo(): MyPageUserInfoResponse
}
