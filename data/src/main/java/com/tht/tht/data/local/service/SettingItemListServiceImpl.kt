package com.tht.tht.data.local.service

import android.content.Context
import com.google.gson.Gson
import com.tht.tht.data.local.entity.setting.SettingItemsListResponse
import com.tht.tht.data.local.entity.setting.SettingListItemEntity
import com.tht.tht.data.local.entity.setting.SettingSectionEntity
import com.tht.tht.data.local.typeadapter.SettingListItemTypeAdapter
import com.tht.tht.data.local.typeadapter.SettingSectionTypeAdapter
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SettingItemListServiceImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : SettingItemListService {

    private val gson = Gson().newBuilder()
        .registerTypeAdapter(SettingSectionEntity::class.java, SettingSectionTypeAdapter())
        .registerTypeAdapter(SettingListItemEntity::class.java, SettingListItemTypeAdapter())
        .create()

    override fun fetchSettingMangerItemList(): SettingItemsListResponse {
        return try {
            val inputStream = context.resources.assets.open("setting_manage_items.json")
            val reader = inputStream.bufferedReader()
            gson.fromJson(reader, SettingItemsListResponse::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        }
    }

    override fun fetchSettingAccountManagerItemList(): SettingItemsListResponse {
        return try {
            val inputStream = context.resources.assets.open("setting_account_manager_items.json")
            val reader = inputStream.bufferedReader()
            gson.fromJson(reader, SettingItemsListResponse::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        }
    }
}
