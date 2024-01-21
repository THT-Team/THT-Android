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
sealed interface SettingListItemUiModel

// 1. title
@Immutable
data class SettingItemUiModel(
    val title: String
) : SettingListItemUiModel

// 2. title + content(?)
@Immutable
data class SettingContentItemItemUiModel(
    val title: String,
    val content: String?
) : SettingListItemUiModel

// 3. title + location
@Immutable
data class SettingLocationItemItemUiModel(
    val title: String,
    val location: String?
) : SettingListItemUiModel

// 4. title + toggle
@Immutable
data class SettingToggleItemItemUiModel(
    val title: String,
    val enable: Boolean
) : SettingListItemUiModel


// 5. title + icon
@Immutable
data class SettingIconItemUiModel(
    val title: String,
    val icon: SettingIcon
) : SettingListItemUiModel {
    enum class SettingIcon {
        Guard
    }
}

// 6. title + subTitle + button
@Immutable
data class SettingButtonItemUiModel(
    val title: String,
    val subTitle: String,
    val btnTitle: String
) : SettingListItemUiModel
