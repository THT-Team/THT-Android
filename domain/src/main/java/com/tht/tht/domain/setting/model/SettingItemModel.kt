package com.tht.tht.domain.setting.model

/**
 * 1. title
 * 2. title + content
 * 3. title + location
 * 4. title + toggle
 * 5. title + icon(guard)
 * 6. title + subTitle + button
 */
sealed interface SettingListItemModel

// 1. title
data class SettingItemModel(
    val title: String
) : SettingListItemModel

// 2. title + content(?)
data class SettingContentItemItemModel(
    val title: String,
    val content: String?
) : SettingListItemModel

// 3. title + location
data class SettingLocationItemItemModel(
    val title: String,
    val location: String?
) : SettingListItemModel

// 4. title + toggle
data class SettingToggleItemItemModel(
    val title: String,
    val enable: Boolean
) : SettingListItemModel


// 5. title + icon
data class SettingIconItemModel(
    val title: String,
    val icon: SettingIcon
) : SettingListItemModel {
    enum class SettingIcon {
        Guard
    }
}

// 6. title + subTitle + button
data class SettingButtonItemModel(
    val title: String,
    val subTitle: String,
    val btnTitle: String
) : SettingListItemModel

