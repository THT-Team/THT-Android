package tht.feature.tohot.component.userinfo

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose_ui.component.text.p.ThtP2
import com.tht.tht.domain.signup.model.IdealTypeModel
import com.tht.tht.domain.signup.model.InterestModel
import tht.feature.tohot.R
import tht.feature.tohot.component.chip.ToHotEmojiChip
import tht.feature.tohot.model.ImmutableListWrapper
import tht.feature.tohot.userData

@Composable
fun ToHotUserInfoFullCard(
    modifier: Modifier = Modifier,
    interests: ImmutableListWrapper<InterestModel>,
    idealTypes: ImmutableListWrapper<IdealTypeModel>,
    introduce: String,
    onClick: () -> Unit = { },
    onReportClick: () -> Unit = { }
) {
    Box(
        modifier = modifier
        .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 338.dp)
                .verticalScroll(rememberScrollState())
                .clip(RoundedCornerShape(6.dp))
                .background(
                    color = Color(0xFF282828).copy(alpha = 0.5f)
                )
                .clickable(
                    enabled = true,
                    onClick = onClick
                )
        ) {
            ThtP2(
                modifier = Modifier.padding(start = 12.dp, top = 12.dp),
                text = stringResource(id = R.string.user_info_interest_title),
                fontWeight = FontWeight.Medium,
                color = Color(0xFFF9FAFA)
            )

            Row(
                modifier = Modifier
                    .padding(start = 12.dp, top = 4.dp)
            ) {
                interests.list.forEach { interest ->
                    ToHotEmojiChip(
                        content = interest.title,
                        emojiCode = interest.emojiCode
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                }
            }

            ThtP2(
                modifier = Modifier.padding(start = 12.dp, top = 6.dp),
                text = stringResource(id = R.string.user_info_ideal_type_title),
                fontWeight = FontWeight.Medium,
                color = Color(0xFFF9FAFA)
            )

            Row(
                modifier = Modifier
                    .padding(start = 12.dp, top = 4.dp)
            ) {
                idealTypes.list.forEach { ideal ->
                    ToHotEmojiChip(
                       content = ideal.title,
                       emojiCode = ideal.emojiCode
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                }
            }

            ThtP2(
                modifier = Modifier.padding(start = 12.dp, top = 6.dp),
                text = stringResource(id = R.string.user_info_introduce_title),
                fontWeight = FontWeight.Medium,
                color = Color(0xFFF9FAFA)
            )

            Box(
                modifier = Modifier
                    .padding(start = 12.dp, top = 4.dp, end = 12.dp, bottom = 12.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(
                        color = Color(0xFF111111).copy(alpha = 0.5f)
                    )
            ) {
                ThtP2(
                    modifier = Modifier
                        .padding(10.dp),
                    textAlign = TextAlign.Start,
                    text = introduce,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFFF9FAFA)
                )
            }
        }

        Image(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 12.dp, end = 12.dp)
                .clickable(
                    enabled = true,
                    onClick = onReportClick
                ),
            painter = painterResource(id = R.drawable.ic_report),
            contentDescription = "report_user"
        )
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
private fun ToHotUserInfoFullCardPreview() {
    ToHotUserInfoFullCard(
        interests = userData.interests,
        idealTypes = userData.idealTypes,
        introduce = userData.introduce
    )
}
