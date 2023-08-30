package tht.feature.setting.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose_ui.component.text.headline.ThtHeadline4
import com.example.compose_ui.component.text.p.ThtP2

@Composable
internal fun SettingTopBar(
    title: String,
    sideButton: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = (15.5).dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            ThtHeadline4(
                modifier = Modifier.align(Alignment.CenterVertically),
                text = title,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFFF9FAFA)
            )
        }

        Box(modifier = Modifier.align(Alignment.CenterEnd)) {
            sideButton()
        }
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFF161616)
internal fun SettingTopBarPreview() {
    SettingTopBar("마이페이지") {
        SettingButton(
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(0xFF222222),
                contentColor = Color(0xFFF9FAFA),
            ),
            onClick = { /*TODO*/ }
        ) {
            ThtP2(textAlign = TextAlign.Center, text = "설정 관리", fontWeight = FontWeight.Normal, color = Color(0xFFF9FAFA))
        }
    }
}


