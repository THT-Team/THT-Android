package tht.feature.setting.composable.mypage

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.compose_ui.component.chip.ToHotEmojiChip
import com.example.compose_ui.component.text.p.ThtP1
import com.example.compose_ui.component.text.subtitle.ThtSubtitle1
import tht.feature.setting.R
import tht.feature.setting.uimodel.MyPageUserInfoUiModel

@Composable
fun ColumnScope.MyPageInfoRows(
    userInfo: MyPageUserInfoUiModel,
    onIntroduceClick: () -> Unit,
    onPreferredGenderClick: () -> Unit,
    onHeightClick: () -> Unit,
    onSmokeClick: () -> Unit,
    onDrinkClick: () -> Unit,
    onReligionClick: () -> Unit,
    onIdealTypeEditClick: () -> Unit,
    onInterestEditClick: () -> Unit,
) {
    Spacer(modifier = Modifier.height(32.dp))
    MyPageItemInfoRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        title = stringResource(id = R.string.my_page_birth_title),
        info = {
            ThtSubtitle1(
                text = userInfo.birth,
                fontWeight = FontWeight.Normal,
                color = colorResource(id = tht.core.ui.R.color.gray_8d8d8d),
                maxLines = 1
            )
        }
    )

    Spacer(modifier = Modifier.height(12.dp))
    MyPageItemInfoRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        title = stringResource(id = R.string.my_page_gender_title),
        info = {
            ThtSubtitle1(
                text = when (userInfo.gender) {
                    MyPageUserInfoUiModel.Gender.Male ->
                        stringResource(id = R.string.gender_male)
                    MyPageUserInfoUiModel.Gender.FeMale ->
                        stringResource(id = R.string.gender_female)
                    MyPageUserInfoUiModel.Gender.UnKnown ->
                        stringResource(id = R.string.gender_unknown)
                },
                fontWeight = FontWeight.Normal,
                color = colorResource(id = tht.core.ui.R.color.gray_8d8d8d),
                maxLines = 1
            )
        }
    )

    Spacer(modifier = Modifier.height(12.dp))
    MyPageItemInfoRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        title = stringResource(id = R.string.my_page_introduce_title),
        info = {
            ThtP1(
                text = userInfo.introduction,
                fontWeight = FontWeight.Normal,
                color = colorResource(id = tht.core.ui.R.color.yellow_f9cc2e),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        },
        onClick = onIntroduceClick
    )

    Spacer(modifier = Modifier.height(12.dp))
    MyPageItemInfoRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        title = stringResource(id = R.string.my_page_preferred_gender_title),
        info = {
            ThtP1(
                text = when (userInfo.preferredGender) {
                    MyPageUserInfoUiModel.Gender.Male ->
                        stringResource(id = R.string.gender_male)
                    MyPageUserInfoUiModel.Gender.FeMale ->
                        stringResource(id = R.string.gender_female)
                    MyPageUserInfoUiModel.Gender.UnKnown ->
                        stringResource(id = R.string.gender_unknown)
                },
                fontWeight = FontWeight.Normal,
                color = colorResource(id = tht.core.ui.R.color.yellow_f9cc2e),
                maxLines = 1
            )
        },
        onClick = onPreferredGenderClick
    )

    Spacer(modifier = Modifier.height(12.dp))
    MyPageItemInfoRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        title = stringResource(id = R.string.my_page_height_title),
        info = {
            ThtP1(
                text = "${userInfo.height}cm",
                fontWeight = FontWeight.Normal,
                color = colorResource(id = tht.core.ui.R.color.yellow_f9cc2e),
                maxLines = 1
            )
        },
        onClick = onHeightClick
    )

    Spacer(modifier = Modifier.height(12.dp))
    MyPageItemInfoRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        title = stringResource(id = R.string.my_page_smoke_title),
        info = {
            ThtP1(
                text = userInfo.smoke,
                fontWeight = FontWeight.Normal,
                color = colorResource(id = tht.core.ui.R.color.yellow_f9cc2e),
                maxLines = 1
            )
        },
        onClick = onSmokeClick
    )

    Spacer(modifier = Modifier.height(12.dp))
    MyPageItemInfoRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        title = stringResource(id = R.string.my_page_drink_title),
        info = {
            ThtP1(
                text = userInfo.drink,
                fontWeight = FontWeight.Normal,
                color = colorResource(id = tht.core.ui.R.color.yellow_f9cc2e),
                maxLines = 1
            )
        },
        onClick = onDrinkClick
    )

    Spacer(modifier = Modifier.height(12.dp))
    MyPageItemInfoRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        title = stringResource(id = R.string.my_page_religion_title),
        info = {
            ThtP1(
                text = userInfo.religion,
                fontWeight = FontWeight.Normal,
                color = colorResource(id = tht.core.ui.R.color.yellow_f9cc2e),
                maxLines = 1
            )
        },
        onClick = onReligionClick
    )

    Spacer(modifier = Modifier.height(12.dp))
    MyPageItemInfoRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        title = stringResource(id = R.string.my_page_interest_title),
        titleMinWidth = 0.dp,
        info = {
            Row {
                userInfo.interestsList.forEachIndexed { i, it ->
                    ToHotEmojiChip(
                        content = it.name,
                        emojiCode = it.emojiCode
                    )
                    if (i != userInfo.interestsList.size - 1) {
                        Spacer(modifier = Modifier.width(6.dp))
                    }
                }
            }
        },
        onClick = onInterestEditClick
    )

    Spacer(modifier = Modifier.height(12.dp))
    MyPageItemInfoRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        title = stringResource(id = R.string.my_page_ideal_type_title),
        titleMinWidth = 0.dp,
        info = {
            Row {
                userInfo.idealTypeList.forEachIndexed { i, it ->
                    ToHotEmojiChip(
                        content = it.name,
                        emojiCode = it.emojiCode
                    )
                    if (i != userInfo.idealTypeList.size - 1) {
                        Spacer(modifier = Modifier.width(6.dp))
                    }
                }
            }
        },
        onClick = onIdealTypeEditClick
    )

    Spacer(modifier = Modifier.height(42.dp))
}
