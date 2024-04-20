package tht.feature.setting.screen

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose_ui.component.text.ThtText
import com.example.compose_ui.component.text.caption.ThtCaption1
import com.example.compose_ui.component.text.headline.ThtHeadline4
import com.example.compose_ui.component.text.subtitle.ThtSubtitle1
import com.example.compose_ui.component.toolbar.ThtToolbar
import com.example.compose_ui.extensions.dpTextUnit
import tht.core.ui.R
import tht.feature.setting.composable.mypage.MyPageInfoRows
import tht.feature.setting.composable.mypage.MyPageItemInfoRow
import tht.feature.setting.composable.mypage.MyPageNicknameRow
import tht.feature.setting.composable.mypage.MyPageProfileImageRow
import tht.feature.setting.uimodel.MyPageUserInfoUiModel

@Composable
fun MyPageScreen(
    userInfo: MyPageUserInfoUiModel,
    onSettingClick: () -> Unit,
    onNicknameEditClick: () -> Unit,
    onPrimaryProfileEditClick: (Int) -> Unit,
    onNonePrimaryProfileAddClick: () -> Unit,
    onNonePrimaryProfileRemoveClick: () -> Unit,
    onIntroduceClick: () -> Unit,
    onPreferredGenderClick: () -> Unit,
    onHeightClick: () -> Unit,
    onSmokeClick: () -> Unit,
    onDrinkClick: () -> Unit,
    onReligionClick: () -> Unit,
    onIdealTypeEditClick: () -> Unit,
    onInterestEditClick: () -> Unit,
    modifier: Modifier = Modifier,
    scrollState: ScrollState = rememberScrollState()
) {
    Column(
        modifier = modifier
            .background(color = colorResource(id = R.color.black_161616))
            .verticalScroll(scrollState)
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

        Spacer(modifier = Modifier.height(12.dp))
        MyPageNicknameRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            nickname = userInfo.username,
            onEditClick = onNicknameEditClick
        )

        Spacer(modifier = Modifier.height(12.dp))
        MyPageProfileImageRow(
            modifier = Modifier
                .fillMaxWidth()
                .height(174.dp)
                .padding(horizontal = 16.dp),
            userProfileImages = userInfo.userProfilePhotos,
            onPrimaryEditClick = onPrimaryProfileEditClick,
            onNonePrimaryAddClick = onNonePrimaryProfileAddClick,
            onNonePrimaryRemoveClick = onNonePrimaryProfileRemoveClick
        )

        Spacer(modifier = Modifier.height(8.dp))
        ThtCaption1(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            textAlign = TextAlign.Start,
            text = stringResource(id = tht.feature.setting.R.string.my_page_profile_description),
            fontWeight = FontWeight.Normal,
            color = colorResource(id = R.color.gray_8d8d8d)
        )

        MyPageInfoRows(
            userInfo = userInfo,
            onIntroduceClick = onIntroduceClick,
            onPreferredGenderClick = onPreferredGenderClick,
            onHeightClick = onHeightClick,
            onSmokeClick = onSmokeClick,
            onDrinkClick = onDrinkClick,
            onReligionClick = onReligionClick,
            onIdealTypeEditClick = onIdealTypeEditClick,
            onInterestEditClick = onInterestEditClick
        )
    }
}

@Composable
@Preview
private fun MyPageScreenPreview() {
    MyPageScreen(
        modifier = Modifier.fillMaxSize(),
        userInfo = MyPageUserInfoUiModel.EMPTY,
        onSettingClick = {},
        onNicknameEditClick = {},
        onIntroduceClick = {},
        onPrimaryProfileEditClick = {},
        onNonePrimaryProfileAddClick = {},
        onNonePrimaryProfileRemoveClick = {},
        onPreferredGenderClick = {},
        onHeightClick = {},
        onDrinkClick = {},
        onReligionClick = {},
        onSmokeClick = {},
        onIdealTypeEditClick = {},
        onInterestEditClick = {},
    )
}
