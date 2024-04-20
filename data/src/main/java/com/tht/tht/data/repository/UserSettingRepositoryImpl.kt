package com.tht.tht.data.repository

import com.tht.tht.data.remote.datasource.setting.UserSettingDataSource
import com.tht.tht.data.remote.mapper.toModel
import com.tht.tht.domain.setting.model.MyPageUserInfoModel
import com.tht.tht.domain.setting.repository.UserSettingRepository
import javax.inject.Inject

class UserSettingRepositoryImpl @Inject constructor(
    private val dataSource: UserSettingDataSource
) : UserSettingRepository {
    override suspend fun fetchMyPageUserInfo(): MyPageUserInfoModel {
        return dataSource.fetchMyPageUserInfo().toModel()
    }
}
