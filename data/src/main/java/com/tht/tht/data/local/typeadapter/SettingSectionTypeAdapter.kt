package com.tht.tht.data.local.typeadapter

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import com.tht.tht.data.local.entity.setting.SettingImageBannerItemEntity
import com.tht.tht.data.local.entity.setting.SettingItemSectionEntity
import com.tht.tht.data.local.entity.setting.SettingSectionEntity
import java.lang.reflect.Type

class SettingSectionTypeAdapter : JsonDeserializer<SettingSectionEntity> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext
    ): SettingSectionEntity {
        val jsonObject = json?.asJsonObject

        return when (val type = jsonObject?.get("type")?.asString) {
            "SettingItemSectionEntity" ->
                context.deserialize(jsonObject, SettingItemSectionEntity::class.java)

            "SettingImageBannerItemEntity" ->
                context.deserialize(jsonObject, SettingImageBannerItemEntity::class.java)

            else -> throw JsonParseException("Unknown type: $type")
        }
    }
}
