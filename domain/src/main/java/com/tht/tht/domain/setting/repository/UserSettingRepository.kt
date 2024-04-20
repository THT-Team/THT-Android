package com.tht.tht.domain.setting.repository

import com.tht.tht.domain.setting.model.MyPageUserInfoModel

interface UserSettingRepository {
    suspend fun fetchMyPageUserInfo(): MyPageUserInfoModel
}
