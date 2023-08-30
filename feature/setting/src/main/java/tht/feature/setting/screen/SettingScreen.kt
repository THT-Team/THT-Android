package tht.feature.setting.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose_ui.component.text.p.ThtP2
import tht.feature.setting.component.SettingButton
import tht.feature.setting.component.SettingTopBar

@Composable
internal fun SettingScreen(

) {
    Scaffold(
        topBar = {
            SettingTopBar(title = "마이페이지") {
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
        },
        backgroundColor = Color.Transparent
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    top = 12.dp,
                    bottom = 30.dp
                )
        ) {
        }
    }
}



@Composable
@Preview(showBackground = true, backgroundColor = 0xFF161616)
internal fun SettingTopBarPreview() {
    SettingScreen()
}
