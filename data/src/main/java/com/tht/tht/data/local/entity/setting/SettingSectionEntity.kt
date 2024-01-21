package com.tht.tht.data.local.entity.setting

/**
 * 1. title + description(?) + items
 * 2. items
 * 3. image banner
 */
sealed interface SettingSectionEntity

// 1. title + description(?) + items
// 2. items
data class SettingItemSectionEntity(
    val title: String,
    val description: String?,
    val items: List<SettingListItemEntity>
) : SettingSectionEntity

// 3. image banner
data class SettingImageBannerItemEntity(
    val banner: ImageBanner
) : SettingSectionEntity {
    enum class ImageBanner {
        Falling
    }
}
