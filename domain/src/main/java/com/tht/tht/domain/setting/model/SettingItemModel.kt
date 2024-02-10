package com.tht.tht.domain.setting.model

/**
 * 1. title
 * 2. title + content
 * 3. title + location
 * 4. title + toggle
 * 5. title + icon(guard)
 * 6. title + subTitle + button
 */
sealed interface SettingListItemModel {
    val title: String
    val key: String
}

// 1. title
data class SettingItemModel(
    override  val title: String,
    override val key: String
) : SettingListItemModel

// 2. title + content(?)
data class SettingContentItemItemModel(
    override  val title: String,
    val content: String?,
    override val key: String
) : SettingListItemModel

// 3. title + location
data class SettingLocationItemItemModel(
    override val title: String,
    val location: String?,
    override val key: String
) : SettingListItemModel

// 4. title + toggle
data class SettingToggleItemItemModel(
    override val title: String,
    val enable: Boolean,
    override val key: String
) : SettingListItemModel


// 5. title + icon
data class SettingIconItemModel(
    override val title: String,
    val icon: SettingIcon,
    override val key: String
) : SettingListItemModel {
    enum class SettingIcon {
        Guard
    }
}

// 6. title + subTitle + button
data class SettingButtonItemModel(
    override val title: String,
    val subTitle: String,
    val btnTitle: String,
    override val key: String
) : SettingListItemModel

