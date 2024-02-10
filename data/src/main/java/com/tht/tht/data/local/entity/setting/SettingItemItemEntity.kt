package com.tht.tht.data.local.entity.setting

/**
 * 1. title
 * 2. title + content
 * 3. title + location
 * 4. title + toggle
 * 5. title + icon(guard)
 * 6. title + subTitle + button
 */
sealed interface SettingListItemEntity {
    val title: String
    val key: String
}

// 1. title
data class SettingItemEntity(
    override val title: String,
    override val key: String
) : SettingListItemEntity

// 2. title + content(?)
data class SettingContentItemItemEntity(
    override val title: String,
    val content: String?,
    override val key: String
) : SettingListItemEntity

// 3. title + location
data class SettingLocationItemItemEntity(
    override val title: String,
    val location: String?,
    override val key: String
) : SettingListItemEntity

// 4. title + toggle
data class SettingToggleItemItemEntity(
    override val title: String,
    val enable: Boolean,
    override val key: String
) : SettingListItemEntity

// 5. title + icon
data class SettingIconItemEntity(
    override val title: String,
    val icon: SettingIcon,
    override val key: String
) : SettingListItemEntity {
    enum class SettingIcon {
        Guard
    }
}

// 6. title + subTitle + button
data class SettingButtonItemEntity(
    override val title: String,
    val subTitle: String,
    val btnTitle: String,
    override val key: String
) : SettingListItemEntity
