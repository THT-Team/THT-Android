package tht.feature.setting.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.compose_ui.component.spacer.Spacer
import com.example.compose_ui.component.text.p.ThtP1
import tht.core.ui.R
import tht.feature.setting.uimodel.SettingImageBannerItemUiModel

@Composable
fun SettingImageBanner(
    imageBanner: SettingImageBannerItemUiModel,
    modifier: Modifier = Modifier
) {
    when (imageBanner.banner) {
        SettingImageBannerItemUiModel.ImageBanner.Falling -> {
            Row(
                modifier = modifier
                    .clip(RoundedCornerShape(6.dp))
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                colorResource(id = R.color.red_f9184e),
                                colorResource(id = R.color.orange_f9743d),
                                colorResource(id = R.color.yellow_f9cc2e)
                            )
                        )
                    )
            ) {
                Column(
                    modifier = Modifier
                        .padding(start = 22.dp)
                        .align(Alignment.CenterVertically)
                ) {
                    ThtP1(
                        text = "대화에 빠져드는 시간",
                        fontWeight = FontWeight.Bold,
                        color = colorResource(id = R.color.white_f9fafa),
                        textAlign = TextAlign.Start
                    )
                    Spacer(space = 5.dp)
                    Icon(
                        painter = painterResource(id = tht.feature.setting.R.drawable.ic_falling_banner),
                        contentDescription = "ic_falling_banner",
                        tint = colorResource(id = R.color.white_f9fafa)
                    )
                }
            }
        }
    }
}
