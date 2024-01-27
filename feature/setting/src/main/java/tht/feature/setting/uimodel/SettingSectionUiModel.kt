package tht.feature.setting.uimodel

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList

/**
 * 1. title + description(?) + items
 * 2. items
 * 3. image banner
 */
sealed interface SettingSectionUiModel

// 1. title + description(?) + items
// 2. items
@Immutable
data class SettingItemSectionUiModel(
    val title: String,
    val description: String?,
    val items: ImmutableList<SettingListItemUiModel>
) : SettingSectionUiModel

// 3. image banner
@Immutable
data class SettingImageBannerItemUiModel(
    val banner: ImageBanner
) : SettingSectionUiModel {
    enum class ImageBanner {
        Falling
    }
}
