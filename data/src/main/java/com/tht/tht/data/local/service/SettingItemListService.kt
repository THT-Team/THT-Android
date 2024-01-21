package com.tht.tht.data.local.service

import com.tht.tht.data.local.entity.setting.SettingItemsListResponse

interface SettingItemListService {
    fun fetchSettingMangerItemList(): SettingItemsListResponse

    fun fetchSettingAccountManagerItemList(): SettingItemsListResponse
}
