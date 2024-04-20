package com.tht.tht.data.remote.datasource.setting

import com.tht.tht.data.remote.mapper.toUnwrap
import com.tht.tht.data.remote.response.setting.MyPageUserInfoResponse
import com.tht.tht.data.remote.service.setting.MyPageUserInfoService
import javax.inject.Inject

class UserSettingDataSourceImpl @Inject constructor(
    private val service: MyPageUserInfoService
) : UserSettingDataSource {

    override suspend fun fetchMyPageUserInfo(): MyPageUserInfoResponse {
        return service.fetchMyPageUserInfo().toUnwrap()
    }
}
