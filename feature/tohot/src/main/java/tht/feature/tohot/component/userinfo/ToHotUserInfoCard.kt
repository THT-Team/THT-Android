package tht.feature.tohot.component.userinfo

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose_ui.component.text.headline.ThtHeadline3
import com.example.compose_ui.component.text.p.ThtP2
import tht.feature.tohot.R
import tht.feature.tohot.userData

@Composable
fun ToHotUserInfoCard(
    modifier: Modifier = Modifier,
    name: String,
    age: Int,
    address: String,
    onClick: () -> Unit = { }
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(
                enabled = true,
                onClick = onClick
            )
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            ThtHeadline3(
                text = "$name, $age",
                fontWeight = FontWeight.Medium,
                color = Color(0xFFF9FAFA)
            )

            Row(
                modifier = Modifier.padding(top = 6.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_location),
                    contentDescription = "location"
                )
                ThtP2(
                    modifier = Modifier.padding(start = 4.dp),
                    text = address,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFFF9FAFA)
                )
            }
        }

        Image(
            modifier = Modifier.padding(top = 8.dp),
            painter = painterResource(id = R.drawable.ic_detail),
            contentDescription = "detail_card"
        )
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFF000000)
private fun ToHotUserInfoCardPreview() {
    ToHotUserInfoCard(
        name = userData.nickname,
        age = 32,
        address = userData.address
    )
}
