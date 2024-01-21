package com.tht.tht.data.local.typeadapter

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import com.tht.tht.data.local.entity.setting.SettingButtonItemEntity
import com.tht.tht.data.local.entity.setting.SettingContentItemItemEntity
import com.tht.tht.data.local.entity.setting.SettingIconItemEntity
import com.tht.tht.data.local.entity.setting.SettingItemEntity
import com.tht.tht.data.local.entity.setting.SettingListItemEntity
import com.tht.tht.data.local.entity.setting.SettingLocationItemItemEntity
import com.tht.tht.data.local.entity.setting.SettingToggleItemItemEntity
import java.lang.reflect.Type

class SettingListItemTypeAdapter : JsonDeserializer<SettingListItemEntity> {

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext
    ): SettingListItemEntity {
        val jsonObject = json?.asJsonObject

        return when (val type = jsonObject?.get("type")?.asString) {
            "SettingItemEntity" ->
                context.deserialize(jsonObject, SettingItemEntity::class.java)

            "SettingContentItemItemEntity" ->
                context.deserialize(jsonObject, SettingContentItemItemEntity::class.java)

            "SettingLocationItemItemEntity" ->
                context.deserialize(jsonObject, SettingLocationItemItemEntity::class.java)

            "SettingToggleItemItemEntity" ->
                context.deserialize(jsonObject, SettingToggleItemItemEntity::class.java)

            "SettingIconItemEntity" ->
                context.deserialize(jsonObject, SettingIconItemEntity::class.java)

            "SettingButtonItemEntity" ->
                context.deserialize(jsonObject, SettingButtonItemEntity::class.java)

            else -> throw JsonParseException("Unknown type: $type")
        }
    }
}
