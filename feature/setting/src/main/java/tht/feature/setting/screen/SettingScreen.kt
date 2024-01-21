package tht.feature.setting.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose_ui.component.text.headline.ThtHeadline4
import com.example.compose_ui.component.toolbar.ThtToolbar
import tht.core.ui.R
import tht.feature.setting.composable.SettingRowItem

@Composable
fun SettingScreen(
    onBackPressed: () -> Unit,
    onAccountManageClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.background(
            color = colorResource(id = R.color.black_161616)
        )
    ) {
        ThtToolbar(
            modifier = Modifier
                .fillMaxWidth(),
            onBackPressed = onBackPressed,
            content = {
                ThtHeadline4(
                    text = "설정 관리",
                    fontWeight = FontWeight.SemiBold,
                    color = colorResource(id = R.color.white_f9fafa)
                )
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        SettingRowItem(
            modifier = Modifier.padding(horizontal = 16.dp),
            title = "계정 관리",
            onClick = onAccountManageClick
        )
    }
}

@Composable
@Preview
private fun SettingScreenPreview() {
    SettingScreen(
        onBackPressed = {},
        onAccountManageClick = {}
    )
}
