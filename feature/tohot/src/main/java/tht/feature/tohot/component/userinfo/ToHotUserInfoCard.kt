package tht.feature.tohot.component.userinfo

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import tht.feature.tohot.userData

@Composable
fun ToHotUserInfoCard(
    modifier: Modifier = Modifier,
    isFullCardShow: Boolean,
    name: String,
    age: Int,
    address: String,
    interests: List<Long>,
    idealTypes: List<Long>,
    introduce: String,
    onClick: (Boolean) -> Unit = { },
    onReportClick: () -> Unit = { }
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioLowBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
    ) {
        if (isFullCardShow) {
            ToHotUserInfoFullCard(
                interests = interests,
                idealTypes = idealTypes,
                introduce = introduce,
                onClick = { onClick(true) },
                onReportClick = onReportClick
            )
        }
        ToHotUserInfoMinimumCard(
            name = name,
            age = age,
            address = address,
            onClick = { onClick(false) }
        )
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFF000000, name = "fullMode")
private fun FullToHotUserInfoCardPreview() {
    ToHotUserInfoCard(
        isFullCardShow = true,
        name = userData.nickname,
        age = 32,
        address = userData.address,
        interests = userData.interestKeys,
        idealTypes = userData.idealTypeKeys,
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
        interests = userData.interestKeys,
        idealTypes = userData.idealTypeKeys,
        introduce = userData.introduce
    )
}
