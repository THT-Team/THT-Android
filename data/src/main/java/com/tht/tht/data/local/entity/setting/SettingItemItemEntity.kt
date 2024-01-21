package com.tht.tht.data.local.entity.setting

/**
 * 1. title
 * 2. title + content
 * 3. title + location
 * 4. title + toggle
 * 5. title + icon(guard)
 * 6. title + subTitle + button
 */
sealed interface SettingListItemEntity

// 1. title
data class SettingItemEntity(
    val title: String
) : SettingListItemEntity

// 2. title + content(?)
data class SettingContentItemItemEntity(
    val title: String,
    val content: String?
) : SettingListItemEntity

// 3. title + location
data class SettingLocationItemItemEntity(
    val title: String,
    val location: String?
) : SettingListItemEntity

// 4. title + toggle
data class SettingToggleItemItemEntity(
    val title: String,
    val enable: Boolean
) : SettingListItemEntity


// 5. title + icon
data class SettingIconItemEntity(
    val title: String,
    val icon: SettingIcon
) : SettingListItemEntity {
    enum class SettingIcon {
        Guard
    }
}

// 6. title + subTitle + button
data class SettingButtonItemEntity(
    val title: String,
    val subTitle: String,
    val btnTitle: String
) : SettingListItemEntity




