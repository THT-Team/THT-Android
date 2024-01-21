package tht.feature.setting.uimodel.mapper

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
import kotlinx.collections.immutable.toImmutableList
import tht.feature.setting.uimodel.SettingButtonItemUiModel
import tht.feature.setting.uimodel.SettingContentItemItemUiModel
import tht.feature.setting.uimodel.SettingIconItemUiModel
import tht.feature.setting.uimodel.SettingImageBannerItemUiModel
import tht.feature.setting.uimodel.SettingItemSectionUiModel
import tht.feature.setting.uimodel.SettingItemUiModel
import tht.feature.setting.uimodel.SettingListItemUiModel
import tht.feature.setting.uimodel.SettingLocationItemItemUiModel
import tht.feature.setting.uimodel.SettingSectionUiModel
import tht.feature.setting.uimodel.SettingToggleItemItemUiModel

fun SettingSectionModel.toUiModel(): SettingSectionUiModel {
    return when (this) {
        is SettingItemSectionModel -> SettingItemSectionUiModel(
            title = title,
            description = description,
            items = items.map { it.toUiModel() }.toImmutableList()
        )

        is SettingImageBannerItemModel -> SettingImageBannerItemUiModel(
            banner = banner.toUiModelBanner()
        )
    }
}

fun SettingImageBannerItemModel.ImageBanner.toUiModelBanner(): SettingImageBannerItemUiModel.ImageBanner {
    return when (this) {
        SettingImageBannerItemModel.ImageBanner.Falling -> SettingImageBannerItemUiModel.ImageBanner.Falling
    }
}

fun SettingListItemModel.toUiModel(): SettingListItemUiModel {
    return when (this) {
        is SettingItemModel -> SettingItemUiModel(title = title)
        is SettingContentItemItemModel -> SettingContentItemItemUiModel(
            title = title,
            content = content
        )
        is SettingLocationItemItemModel -> SettingLocationItemItemUiModel(
            title = title,
            location = location
        )
        is SettingToggleItemItemModel -> SettingToggleItemItemUiModel(
            title = title,
            enable = enable
        )
        is SettingIconItemModel -> SettingIconItemUiModel(
            title = title,
            icon = icon.toUiModelIcon()
        )
        is SettingButtonItemModel -> SettingButtonItemUiModel(
            title = title,
            subTitle = subTitle,
            btnTitle = btnTitle
        )
    }
}

fun SettingIconItemModel.SettingIcon.toUiModelIcon(): SettingIconItemUiModel.SettingIcon {
    return when (this) {
        SettingIconItemModel.SettingIcon.Guard -> SettingIconItemUiModel.SettingIcon.Guard
    }
}
