package tht.feature.setting.composable

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import tht.feature.setting.uimodel.SettingButtonItemUiModel
import tht.feature.setting.uimodel.SettingContentItemItemUiModel
import tht.feature.setting.uimodel.SettingIconItemUiModel
import tht.feature.setting.uimodel.SettingItemUiModel
import tht.feature.setting.uimodel.SettingListItemUiModel
import tht.feature.setting.uimodel.SettingLocationItemItemUiModel
import tht.feature.setting.uimodel.SettingToggleItemItemUiModel

@Composable
fun SettingListItem(
    settingItem: SettingListItemUiModel,
    onClick: (SettingListItemUiModel) -> Unit,
    modifier: Modifier = Modifier
) {
    when (settingItem) {
        is SettingItemUiModel -> {
            SettingRowItem(
                modifier = modifier,
                title = settingItem.title,
                onClick = {
                    onClick(settingItem)
                }
            )
        }
        is SettingContentItemItemUiModel -> {
            SettingRowItem(
                modifier = modifier,
                title = settingItem.title,
                onClick = {
                    onClick(settingItem)
                }
            )
        }
        is SettingLocationItemItemUiModel -> {
            SettingRowItem(
                modifier = modifier,
                title = settingItem.title,
                onClick = {
                    onClick(settingItem)
                }
            )
        }
        is SettingToggleItemItemUiModel -> {
            SettingRowItem(
                modifier = modifier,
                title = settingItem.title,
                onClick = {
                    onClick(settingItem)
                }
            )
        }
        is SettingIconItemUiModel -> {
            SettingRowItem(
                modifier = modifier,
                title = settingItem.title,
                onClick = {
                    onClick(settingItem)
                }
            )
        }
        is SettingButtonItemUiModel -> {
            SettingRowItem(
                modifier = modifier,
                title = settingItem.title,
                onClick = {
                    onClick(settingItem)
                }
            )
        }
    }
}
