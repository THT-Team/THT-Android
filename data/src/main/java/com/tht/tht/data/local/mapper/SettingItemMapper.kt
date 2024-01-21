package com.tht.tht.data.local.mapper

import com.tht.tht.data.local.entity.setting.SettingButtonItemEntity
import com.tht.tht.data.local.entity.setting.SettingContentItemItemEntity
import com.tht.tht.data.local.entity.setting.SettingIconItemEntity
import com.tht.tht.data.local.entity.setting.SettingImageBannerItemEntity
import com.tht.tht.data.local.entity.setting.SettingItemEntity
import com.tht.tht.data.local.entity.setting.SettingItemSectionEntity
import com.tht.tht.data.local.entity.setting.SettingListItemEntity
import com.tht.tht.data.local.entity.setting.SettingLocationItemItemEntity
import com.tht.tht.data.local.entity.setting.SettingSectionEntity
import com.tht.tht.data.local.entity.setting.SettingToggleItemItemEntity
import com.tht.tht.domain.setting.model.SettingButtonItemModel
import com.tht.tht.domain.setting.model.SettingContentItemItemModel
import com.tht.tht.domain.setting.model.SettingIconItemModel
import com.tht.tht.domain.setting.model.SettingImageBannerItemModel
import com.tht.tht.domain.setting.model.SettingItemModel
import com.tht.tht.domain.setting.model.SettingItemSectionModel
import com.tht.tht.domain.setting.model.SettingListItemModel
import com.tht.tht.domain.setting.model.SettingLocationItemItemModel
import com.tht.tht.domain.setting.model.SettingSectionModel
import com.tht.tht.domain.setting.model.SettingToggleItemItemModel

fun SettingSectionEntity.toModel(): SettingSectionModel {
    return when (this) {
        is SettingItemSectionEntity -> SettingItemSectionModel(
            title = title,
            description = description,
            items = items.map { it.toModel() }
        )

        is SettingImageBannerItemEntity -> SettingImageBannerItemModel(
            banner = banner.toModelBanner()
        )
    }
}

fun SettingImageBannerItemEntity.ImageBanner.toModelBanner(): SettingImageBannerItemModel.ImageBanner {
    return when (this) {
        SettingImageBannerItemEntity.ImageBanner.Falling -> SettingImageBannerItemModel.ImageBanner.Falling
    }
}

fun SettingListItemEntity.toModel(): SettingListItemModel {
    return when (this) {
        is SettingItemEntity -> SettingItemModel(title = title, key = key)
        is SettingContentItemItemEntity -> SettingContentItemItemModel(
            title = title,
            content = content,
            key = key
        )
        is SettingLocationItemItemEntity -> SettingLocationItemItemModel(
            title = title,
            location = location,
            key = key
        )
        is SettingToggleItemItemEntity -> SettingToggleItemItemModel(
            title = title,
            enable = enable,
            key = key
        )
        is SettingIconItemEntity -> SettingIconItemModel(
            title = title,
            icon = icon.toModelIcon(),
            key = key
        )
        is SettingButtonItemEntity -> SettingButtonItemModel(
            title = title,
            subTitle = subTitle,
            btnTitle = btnTitle,
            key = key
        )
    }
}

fun SettingIconItemEntity.SettingIcon.toModelIcon(): SettingIconItemModel.SettingIcon {
    return when (this) {
        SettingIconItemEntity.SettingIcon.Guard -> SettingIconItemModel.SettingIcon.Guard
    }
}
