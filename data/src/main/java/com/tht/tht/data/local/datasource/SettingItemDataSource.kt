package com.tht.tht.data.local.datasource

import com.tht.tht.data.local.entity.setting.SettingItemsListResponse

interface SettingItemDataSource {
    suspend fun fetchSettingMangerItemList(): SettingItemsListResponse

    suspend fun fetchSettingAccountManagerItemList(): SettingItemsListResponse
}
