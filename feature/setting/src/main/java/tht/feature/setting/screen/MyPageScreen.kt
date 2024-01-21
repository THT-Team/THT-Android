package tht.feature.setting.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose_ui.component.text.ThtText
import com.example.compose_ui.component.text.headline.ThtHeadline4
import com.example.compose_ui.component.toolbar.ThtToolbar
import com.example.compose_ui.extensions.dpTextUnit
import tht.core.ui.R

@Composable
fun MyPageScreen(
    modifier: Modifier = Modifier,
    onSettingClick: () -> Unit = { }
) {
    Column(
        modifier = modifier
            .background(color = colorResource(id = R.color.black_161616))
    ) {
        ThtToolbar(
            modifier = Modifier
                .background(color = colorResource(id = R.color.black_161616))
                .padding(horizontal = 16.dp),
            content = {
                ThtHeadline4(
                    text = "마이 페이지",
                    fontWeight = FontWeight.SemiBold,
                    color = colorResource(id = R.color.white_f9fafa)
                )
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = onSettingClick,
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = colorResource(id = R.color.black_222222)
                    ),
                    contentPadding = PaddingValues(
                        horizontal = 14.dp,
                        vertical = 5.dp
                    )
                ) {
                    ThtText(
                        textAlign = TextAlign.Start,
                        text = "설정 관리",
                        textSize = 13.dpTextUnit,
                        fontWeight = FontWeight.SemiBold,
                        color = colorResource(id = R.color.white_f9fafa)
                    )
                }
            }
        )
    }
}

@Composable
@Preview
private fun MyPageScreenPreview() {
    MyPageScreen()
}
