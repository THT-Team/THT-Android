package com.tht.tht.domain.setting.repository

import com.tht.tht.domain.setting.model.SettingSectionModel

interface SettingRepository {
    suspend fun fetchSettingMangerItemList(): List<SettingSectionModel>

    suspend fun fetchSettingAccountManagerItemList(): List<SettingSectionModel>
}
