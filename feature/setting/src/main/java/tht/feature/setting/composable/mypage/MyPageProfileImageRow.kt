package tht.feature.setting.composable.mypage

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import tht.feature.setting.uimodel.MyPageUserInfoUiModel

@Composable
fun MyPageProfileImageRow(
    userProfileImages: ImmutableList<MyPageUserInfoUiModel.UserProfilePhoto>,
    onPrimaryEditClick: (Int) -> Unit,
    onNonePrimaryAddClick: () -> Unit,
    onNonePrimaryRemoveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val imageUrls = remember(userProfileImages) {
        userProfileImages.sortedBy { it.priority }
    }
    Row(
        modifier = modifier
    ) {
        imageUrls.forEachIndexed { idx, profileImage ->
            MyPageProfileImageItem(
                modifier = Modifier.weight(1f),
                imageUrl = profileImage.url,
                isPrimary = profileImage.isPrimaryImage(),
                onPrimaryEditClick = remember(profileImage.url) {
                    { onPrimaryEditClick(profileImage.priority) }
                },
                onNonePrimaryAddClick = onNonePrimaryAddClick,
                onNonePrimaryRemoveClick = onNonePrimaryRemoveClick
            )
            if (idx != imageUrls.size - 1) {
                Spacer(modifier = Modifier.width(20.dp))
            }
        }
    }
}

@Composable
@Preview
private fun MyPageProfileImageRowPreview() {
    MyPageProfileImageRow(
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp)
            .padding(horizontal = 16.dp),
        userProfileImages = persistentListOf(
            MyPageUserInfoUiModel.UserProfilePhoto(
                priority = 0,
                url = "https://asdasd"
            ),
            MyPageUserInfoUiModel.UserProfilePhoto(
                priority = 1,
                url = "https://asd"
            ),
            MyPageUserInfoUiModel.UserProfilePhoto(
                priority = 2,
                url = ""
            )
        ).toImmutableList(),
        onPrimaryEditClick = {},
        onNonePrimaryRemoveClick = {},
        onNonePrimaryAddClick = {}
    )
}
