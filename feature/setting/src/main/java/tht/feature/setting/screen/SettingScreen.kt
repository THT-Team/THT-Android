package tht.feature.setting.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.compose_ui.component.text.headline.ThtHeadline4
import com.example.compose_ui.component.toolbar.ThtToolbar
import tht.core.ui.R

@Composable
fun SettingScreen(
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.background(
            color = colorResource(id = R.color.black_161616)
        )
    ) {
        ThtToolbar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            onBackPressed = onBackPressed,
            content = {
                ThtHeadline4(
                    text = "설정 관리",
                    fontWeight = FontWeight.SemiBold,
                    color = colorResource(id = R.color.white_f9fafa)
                )
            }
        )
    }
}
