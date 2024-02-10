package com.tht.tht.domain.setting.model

/**
 * 1. title + description(?) + items
 * 2. items
 * 3. image banner
 */
sealed interface SettingSectionModel

// 1. title + description(?) + items
// 2. items
data class SettingItemSectionModel(
    val title: String,
    val description: String?,
    val items: List<SettingListItemModel>
) : SettingSectionModel

// 3. image banner
data class SettingImageBannerItemModel(
    val banner: ImageBanner
) : SettingSectionModel {
    enum class ImageBanner {
        Falling
    }
}

