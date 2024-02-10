package com.tht.tht.data.repository

import com.tht.tht.data.local.datasource.SettingItemDataSource
import com.tht.tht.data.local.mapper.toModel
import com.tht.tht.domain.setting.model.SettingSectionModel
import com.tht.tht.domain.setting.repository.SettingRepository
import javax.inject.Inject

class SettingRepositoryImpl @Inject constructor(
    private val dataSource: SettingItemDataSource
) : SettingRepository {

    override suspend fun fetchSettingMangerItemList(): List<SettingSectionModel> {
        return dataSource.fetchSettingMangerItemList().items.map { it.toModel() }
    }

    override suspend fun fetchSettingAccountManagerItemList(): List<SettingSectionModel> {
        return dataSource.fetchSettingAccountManagerItemList().items.map { it.toModel() }
    }
}
