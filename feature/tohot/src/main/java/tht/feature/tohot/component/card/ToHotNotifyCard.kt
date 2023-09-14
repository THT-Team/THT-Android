package tht.feature.tohot.component.card

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose_ui.component.text.headline.ThtHeadline4
import com.example.compose_ui.component.text.headline.ThtHeadline5
import tht.feature.tohot.R
import tht.feature.tohot.component.progress.ToHotEmptyTimeProgressContainer

@Composable
fun ToHotNotifyCard(
    modifier: Modifier = Modifier,
    image: Painter,
    title: String,
    description: String,
    buttonText: String,
    onClick: () -> Unit = { }
) {
    Column(
        modifier = modifier
            .background(
                colorResource(id = tht.core.ui.R.color.black_222222)
            )
            .fillMaxSize()
            .clip(RoundedCornerShape(12.dp))
    ) {
        ToHotEmptyTimeProgressContainer(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 13.dp, vertical = 12.dp)
        )

        Image(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 54.dp)
                .weight(1f),
            painter = image,
            contentDescription = "mudy_enter"
        )

        ThtHeadline4(
            modifier = Modifier.fillMaxWidth(),
            text = title,
            fontWeight = FontWeight.SemiBold,
            color = colorResource(id = tht.core.ui.R.color.white_f9fafa)
        )

        ThtHeadline4(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 6.dp),
            text = description,
            fontWeight = FontWeight.Normal,
            color = colorResource(id = tht.core.ui.R.color.gray_8d8d8d)
        )

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 26.dp, vertical = 60.dp)
                .clip(RoundedCornerShape(12.dp)),
            onClick = onClick,
            contentPadding = PaddingValues(0.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = colorResource(id = tht.core.ui.R.color.yellow_f9cc2e)
            )
        ) {
            ThtHeadline5(
                modifier = Modifier.padding(vertical = 15.dp),
                text = buttonText,
                fontWeight = FontWeight.Bold,
                color = colorResource(id = tht.core.ui.R.color.black_222222)
            )
        }
    }
}

@Composable
@Preview
private fun ToHotNotifyCardPreview() {
    ToHotNotifyCard(
        image = painterResource(id = R.drawable.ic_mudy_none_initial_user),
        title = "가나다라마바사",
        description = "preview",
        buttonText = "테스트"
    )
}
