package tht.feature.setting.screen

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose_ui.component.text.headline.ThtHeadline4
import com.example.compose_ui.component.toolbar.ThtToolbar
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import tht.core.ui.R
import tht.feature.setting.composable.SettingImageBanner
import tht.feature.setting.composable.SettingSection
import tht.feature.setting.uimodel.SettingImageBannerItemUiModel
import tht.feature.setting.uimodel.SettingItemSectionUiModel
import tht.feature.setting.uimodel.SettingListItemUiModel
import tht.feature.setting.uimodel.SettingSectionUiModel

@Composable
fun SettingScreen(
    title: String,
    items: ImmutableList<SettingSectionUiModel>,
    onBackPressed: () -> Unit,
    onSettingItemClick: (SettingListItemUiModel) -> Unit,
    modifier: Modifier = Modifier,
    scrollState: ScrollState = rememberScrollState()
) {
    Column(
        modifier.background(color = colorResource(id = R.color.black_161616))
    ) {
        ThtToolbar(
            modifier = Modifier
                .fillMaxWidth(),
            onBackPressed = onBackPressed,
            content = {
                ThtHeadline4(
                    text = title,
                    fontWeight = FontWeight.SemiBold,
                    color = colorResource(id = R.color.white_f9fafa)
                )
            }
        )
        Column(
            modifier = modifier.verticalScroll(scrollState, true)
        ) {
            items.forEachIndexed { index, section ->
                when (section) {
                    is SettingItemSectionUiModel -> {
                        Spacer(modifier = Modifier.height(12.dp))
                        SettingSection(
                            section = section,
                            onClick = onSettingItemClick
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                    is SettingImageBannerItemUiModel -> {
                        Spacer(modifier = Modifier.height(12.dp))
                        SettingImageBanner(imageBanner = section)
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }
                if (index == items.size - 1) {
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}

@Composable
@Preview
private fun SettingScreenPreview() {
    SettingScreen(
        title = "설정",
        items = persistentListOf(),
        onBackPressed = {},
        onSettingItemClick = {}
    )
}
