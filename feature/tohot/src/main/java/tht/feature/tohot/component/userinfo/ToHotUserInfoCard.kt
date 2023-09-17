package tht.feature.tohot.component.userinfo

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose_ui.extensions.noRippleClickable
import com.tht.tht.domain.signup.model.IdealTypeModel
import com.tht.tht.domain.signup.model.InterestModel
import tht.feature.tohot.model.ImmutableListWrapper
import tht.feature.tohot.userData

@Composable
fun ToHotUserInfoCard(
    modifier: Modifier = Modifier,
    isFullCardShow: Boolean,
    name: String,
    age: Int,
    address: String,
    interests: ImmutableListWrapper<InterestModel>,
    idealTypes: ImmutableListWrapper<IdealTypeModel>,
    introduce: String,
    onClick: () -> Unit = { },
    onReportClick: () -> Unit = { },
    onLikeClick: () -> Unit = { },
    onUnLikeClick: () -> Unit = { }
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(5.dp))
            .noRippleClickable(onClick)
    ) {
        AnimatedVisibility(
            visible = isFullCardShow
        ) {
            ToHotUserInfoFullCard(
                interests = interests,
                idealTypes = idealTypes,
                introduce = introduce,
                onClick = onClick,
                onReportClick = onReportClick
            )
        }
        ToHotUserInfoMinimumCard(
            name = name,
            age = age,
            address = address
        )
        ToHotCardManageRow(
            modifier = Modifier.padding(top = 10.dp),
            onInfoClick = onClick,
            onLikeClick = onLikeClick,
            onUnLikeClick = onUnLikeClick
        )
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFF000000, name = "fullMode")
private fun FullToHotUserInfoCardPreview() {
    ToHotUserInfoCard(
        modifier = Modifier.background(color = Color.White),
        isFullCardShow = true,
        name = userData.nickname,
        age = 32,
        address = userData.address,
        interests = userData.interests,
        idealTypes = userData.idealTypes,
        introduce = userData.introduce
    )
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFF000000, name = "minimumMode")
private fun MinimumToHotUserInfoCardPreview() {
    ToHotUserInfoCard(
        isFullCardShow = false,
        name = userData.nickname,
        age = 32,
        address = userData.address,
        interests = userData.interests,
        idealTypes = userData.idealTypes,
        introduce = userData.introduce
    )
}
