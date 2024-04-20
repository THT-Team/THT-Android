package tht.feature.setting.composable.mypage

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalMinimumInteractiveComponentEnforcement
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Size
import tht.feature.setting.R

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MyPageProfileImageItem(
    imageUrl: String?,
    isPrimary: Boolean,
    onPrimaryEditClick: () -> Unit,
    onNonePrimaryAddClick: () -> Unit,
    onNonePrimaryRemoveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    //TODO: PlaceHolder, Error 처리
    val context = LocalContext.current
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .then(
                if (isPrimary) {
                    Modifier.border(
                        width = 1.dp,
                        brush = SolidColor(colorResource(id = tht.core.ui.R.color.yellow_f9cc2e)),
                        shape = RoundedCornerShape(12.dp)
                    )
                } else {
                    Modifier
                }
            )
            .background(color = colorResource(id = tht.core.ui.R.color.black_222222))
    ) {
        val model = remember(imageUrl) {
            ImageRequest.Builder(context)
                .data(imageUrl)
                .size(Size.ORIGINAL)
                .crossfade(true)
                .build()
        }
        AsyncImage(
            modifier = modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(12.dp)),
            model = model,
            contentDescription = "profile_image",
            contentScale = ContentScale.Crop,
        )
        // remove default icon padding
        CompositionLocalProvider(
            LocalMinimumInteractiveComponentEnforcement provides false,
        ) {
            if (isPrimary) {
                IconButton(
                    modifier = Modifier.align(Alignment.BottomEnd)
                        .padding(end = 6.dp, bottom = 6.dp),
                    onClick = onPrimaryEditClick
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_profile_image_edit),
                        contentDescription = "ic_profile_image_edit",
                        tint = Color.Unspecified
                    )
                }
            } else {
                if (imageUrl.isNullOrBlank()) {
                    IconButton(
                        modifier = Modifier.align(Alignment.Center),
                        onClick = onNonePrimaryAddClick
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_profile_image_add),
                            contentDescription = "ic_profile_image_edit",
                            tint = Color.Unspecified
                        )
                    }
                } else {
                    IconButton(
                        modifier = Modifier.align(Alignment.BottomEnd)
                            .padding(end = 6.dp, bottom = 6.dp),
                        onClick = onNonePrimaryRemoveClick
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_profile_image_delete),
                            contentDescription = "ic_profile_image_edit",
                            tint = Color.Unspecified
                        )
                    }
                }
            }
        }
    }
}

@Composable
@Preview
private fun MyPageProfileImageItemPreview() {
    MyPageProfileImageItem(
        modifier = Modifier,
        imageUrl = "https://adasd",
        isPrimary = true,
        onPrimaryEditClick = {},
        onNonePrimaryAddClick = {},
        onNonePrimaryRemoveClick = {}
    )
}

@Composable
@Preview
private fun MyPageNonePrimaryProfileImageItemPreview() {
    MyPageProfileImageItem(
        modifier = Modifier,
        imageUrl = "https://asdasdsa",
        isPrimary = false,
        onPrimaryEditClick = {},
        onNonePrimaryAddClick = {},
        onNonePrimaryRemoveClick = {}
    )
}

@Composable
@Preview
private fun MyPageNonePrimaryNoneProfileImageItemPreview() {
    MyPageProfileImageItem(
        modifier = Modifier,
        imageUrl = null,
        isPrimary = false,
        onPrimaryEditClick = {},
        onNonePrimaryAddClick = {},
        onNonePrimaryRemoveClick = {}
    )
}
