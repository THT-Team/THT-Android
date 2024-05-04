package tht.feature.signin.terms.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose_ui.component.spacer.Spacer
import com.example.compose_ui.component.text.caption.ThtCaption1
import com.example.compose_ui.component.text.subtitle.ThtSubtitle1
import com.example.compose_ui.extensions.noRippleClickable
import tht.core.ui.R
import tht.feature.signin.terms.uimodel.TermsUiModel

@Composable
internal fun TermsRow(
    terms: TermsUiModel,
    onClick: () -> Unit,
    onRightArrowClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.noRippleClickable(onClick)
    ){
        Row {
            Icon(
                modifier = Modifier
                    .align(Alignment.Top)
                    .clickable(enabled = true, onClick = onClick),
                painter = if (!terms.isSelect) {
                    painterResource(id = tht.feature.signin.R.drawable.ic_check_unselected)
                } else {
                    painterResource(id = tht.feature.signin.R.drawable.ic_check_selected)
                },
                contentDescription = "ic_check",
                tint = Color.Unspecified
            )

            Spacer(space = 12.dp)

            ThtSubtitle1(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .weight(1f),
                text = terms.title,
                fontWeight = FontWeight.Normal,
                color = colorResource(id = R.color.white_f9fafa),
                textAlign = TextAlign.Start,
                lineHeight = 22.4.sp
            )


            if (!terms.link.isNullOrBlank()) {
                Spacer(space = 12.dp)
                Icon(
                    modifier = Modifier
                        .widthIn(18.dp)
                        .align(Alignment.Top)
                        .clickable(enabled = true, onClick = onRightArrowClick),
                    painter = painterResource(id = tht.feature.signin.R.drawable.ic_right_arrow_gray) ,
                    contentDescription = "ic_right_arrow_gray",
                    tint = Color.Unspecified
                )
            }
        }

        if (!terms.description.isNullOrBlank()) {
            Spacer(space = 2.dp)
            ThtCaption1(
                // icon size 25 + 12(padding)
                // constraint 로 변경할 필요가 있으려나?
                modifier = Modifier.padding(start = 37.dp),
                text = terms.description,
                fontWeight = FontWeight.Medium,
                color = colorResource(id = R.color.gray_666666),
                textAlign = TextAlign.Start,
                lineHeight = 15.4.sp
            )
        }
    }
}

@Composable
@Preview
private fun TermsRowPreview() {
    Column {
        TermsRow(
            modifier = Modifier.padding(horizontal = 28.dp),
            terms = TermsUiModel(
                title = "title",
                key = "key",
                description = null,
                require = true,
                link = null,
                isSelect = false
            ),
            onClick = {},
            onRightArrowClick = {}
        )
        Spacer(space = 20.dp)
        TermsRow(
            modifier = Modifier.padding(horizontal = 28.dp),
            terms = TermsUiModel(
                title = "long title long title long title long title long title long title long title",
                key = "key",
                description = null,
                require = true,
                link = "adsad",
                isSelect = true
            ),
            onClick = {},
            onRightArrowClick = {}
        )
        Spacer(space = 20.dp)
        TermsRow(
            modifier = Modifier.padding(horizontal = 28.dp),
            terms = TermsUiModel(
                title = "long title long title long title long title long title long title long title",
                key = "key",
                description = "폴링에서 제공하는 이벤트/혜택 등 다양한 정보를 Push 알림으로\n받아보실 수 있습니다.",
                require = true,
                link = "adsad",
                isSelect = true
            ),
            onClick = {},
            onRightArrowClick = {}
        )

    }
}
