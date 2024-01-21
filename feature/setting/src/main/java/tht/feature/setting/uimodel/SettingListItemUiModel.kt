package tht.feature.setting.uimodel

import androidx.compose.runtime.Immutable

/**
 * 1. title
 * 2. title + content
 * 3. title + location
 * 4. title + toggle
 * 5. title + icon(guard)
 * 6. title + subTitle + button
 */
sealed interface SettingListItemUiModel {
    val title: String
    val key: String
}

// 1. title
@Immutable
data class SettingItemUiModel(
    override val title: String,
    override val key: String
) : SettingListItemUiModel

// 2. title + content(?)
@Immutable
data class SettingContentItemItemUiModel(
    override val title: String,
    val content: String?,
    override val key: String
) : SettingListItemUiModel

// 3. title + location
@Immutable
data class SettingLocationItemItemUiModel(
    override val title: String,
    val location: String?,
    override val key: String
) : SettingListItemUiModel

// 4. title + toggle
@Immutable
data class SettingToggleItemItemUiModel(
    override val title: String,
    val enable: Boolean,
    override val key: String
) : SettingListItemUiModel


// 5. title + icon
@Immutable
data class SettingIconItemUiModel(
    override val title: String,
    val icon: SettingIcon,
    override val key: String
) : SettingListItemUiModel {
    enum class SettingIcon {
        Guard
    }
}

// 6. title + subTitle + button
@Immutable
data class SettingButtonItemUiModel(
    override val title: String,
    val subTitle: String,
    val btnTitle: String,
    override val key: String
) : SettingListItemUiModel
