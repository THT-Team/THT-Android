package tht.feature.like.renewal.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.example.compose_ui.component.image.ThtImage
import com.example.compose_ui.component.text.p.ThtP2
import com.example.compose_ui.component.text.subtitle.ThtSubtitle2
import tht.feature.heart.R

@Preview
@Composable
internal fun LikeListItem(
    isNew: Boolean = true
) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(CornerSize(14.dp)))
            .background(Color(0xFF222222))
            .padding(12.dp)
    ) {
        ThtImage(
            modifier = Modifier,
            src = "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog" +
                ".kakaocdn.net%2Fdn%2Fbnhxb2%2FbtrNyEBYECa%2FnhgHKeKyGh3mTFFHY4MpD1%2Fimg.jpg",
            size = DpSize(84.dp, 84.dp)
        )
        Spacer(modifier = Modifier.width(14.dp))
        Column {
            Row {
                ThtSubtitle2(
                    text = "우리닉네임열두글자입니다, 24",
                    color = Color(0xFFF9FAFA),
                    fontWeight = FontWeight.W500
                )
                Spacer(modifier = Modifier.weight(1f))
                if(isNew) {
                    Box(
                        modifier = Modifier
                            .size(6.dp)
                            .clip(RoundedCornerShape(100.dp))
                            .background(Color(0xFFEF4444))
                    )
                }
            }
            Spacer(modifier = Modifier.height(3.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_map_pin),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(Color(0xFF666666))
                )
                ThtP2(
                    text = "서울시 강남구 대치동, 23km",
                    color = Color(0xFF666666),
                    fontWeight = FontWeight.W400,
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(CornerSize((16.5).dp)))
                        .background(Color(0xFF3D3D3D))
                        .padding(horizontal = 22.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = "다음에",
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { },
                        color = Color(0xFF8D8D8D)
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(CornerSize((16.5).dp)))
                        .background(Color(0xFFF9CC2E))
                        .padding(horizontal = 22.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = "대화하기",
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { },
                        color = Color(0xFF161616)
                    )
                }
            }
        }
    }
}
