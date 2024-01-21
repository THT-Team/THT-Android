package tht.feature.setting.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.compose_ui.component.text.caption.ThtCaption1
import com.example.compose_ui.component.text.p.ThtP1
import tht.feature.setting.uimodel.SettingItemSectionUiModel
import tht.feature.setting.uimodel.SettingListItemUiModel

@Composable
fun SettingSection(
    section: SettingItemSectionUiModel,
    onClick: (SettingListItemUiModel) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(horizontal = 16.dp)
    ) {
        ThtP1(
            modifier = Modifier.padding(start = 14.dp),
            text = section.title,
            fontWeight = FontWeight.Normal,
            color = colorResource(id = tht.core.ui.R.color.gray_8d8d8d)
        )
        section.items.forEachIndexed { index, item ->
            if (index == 0) {
                Spacer(modifier = Modifier.height(6.dp))
            }
            SettingListItem(
                settingItem = item,
                onClick = onClick
            )
            if (index != section.items.size - 1) {
                Spacer(modifier = Modifier.height(6.dp))
            }
        }
        if (!section.description.isNullOrBlank()) {
            ThtCaption1(
                modifier = Modifier.padding(start = 14.dp, top = 6.dp),
                text = section.description,
                fontWeight = FontWeight.Normal,
                color = colorResource(id = tht.core.ui.R.color.gray_8d8d8d),
                textAlign = TextAlign.Start
            )
        }
    }
}
