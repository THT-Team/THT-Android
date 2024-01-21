package com.tht.tht.data.local.datasource

import com.tht.tht.data.di.IODispatcher
import com.tht.tht.data.local.entity.setting.SettingItemsListResponse
import com.tht.tht.data.local.service.SettingItemListService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SettingItemDataSourceImpl @Inject constructor(
    private val service: SettingItemListService,
    @IODispatcher private val dispatcher: CoroutineDispatcher
) : SettingItemDataSource {
    override suspend fun fetchSettingMangerItemList(): SettingItemsListResponse {
        return withContext(dispatcher) {
            service.fetchSettingMangerItemList()
        }
    }

    override suspend fun fetchSettingAccountManagerItemList(): SettingItemsListResponse {
        return withContext(dispatcher) {
            service.fetchSettingAccountManagerItemList()
        }
    }
}
