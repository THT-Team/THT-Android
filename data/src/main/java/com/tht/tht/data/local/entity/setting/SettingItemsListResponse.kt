package com.tht.tht.data.local.entity.setting

import com.google.gson.annotations.SerializedName

data class SettingItemsListResponse(
    @SerializedName("items")
    val items: List<SettingSectionEntity>
)
