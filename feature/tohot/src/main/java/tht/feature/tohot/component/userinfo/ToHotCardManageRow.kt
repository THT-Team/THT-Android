package tht.feature.tohot.component.userinfo

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import tht.feature.tohot.R

@Composable
fun ToHotCardManageRow(
    modifier: Modifier = Modifier,
    onInfoClick: () -> Unit = {},
    onLikeClick: () -> Unit = { },
    onUnLikeClick: () -> Unit = { }
) {
    val userInfoButtonInteractionSource = remember { MutableInteractionSource() }
    val userInfoButtonPressed by userInfoButtonInteractionSource.collectIsPressedAsState()

    val likeButtonInteractionSource = remember { MutableInteractionSource() }
    val likeButtonPressed by likeButtonInteractionSource.collectIsPressedAsState()

    val unlikeButtonInteractionSource = remember { MutableInteractionSource() }
    val unlikeButtonPressed by unlikeButtonInteractionSource.collectIsPressedAsState()

    Row(
        modifier = modifier.fillMaxWidth()
    ) {
        IconButton(
            onClick = onInfoClick,
            interactionSource = userInfoButtonInteractionSource
        ) {
            Icon(
                painter = painterResource(
                    id = if (userInfoButtonPressed) {
                        R.drawable.ic_user_info_pressed
                    } else {
                        R.drawable.ic_user_info_unpreseed
                    }
                ),
                contentDescription = "user_info_button_icon",
                tint = Color.Unspecified
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        IconButton(
            onClick = onLikeClick,
            interactionSource = likeButtonInteractionSource
        ) {
            Icon(
                painter = painterResource(
                    id = if (likeButtonPressed) {
                        R.drawable.ic_heart_pressed
                    } else {
                        R.drawable.ic_heart_unpressed
                    }
                ),
                contentDescription = "user_info_button_icon",
                tint = Color.Unspecified
            )
        }

        IconButton(
            modifier = Modifier.padding(start = 16.dp),
            interactionSource = unlikeButtonInteractionSource,
            onClick = onUnLikeClick
        ) {
            Icon(
                painter = painterResource(
                    id = if (unlikeButtonPressed) {
                        R.drawable.ic_unlike_pressed
                    } else {
                        R.drawable.ic_unlike_unpressed
                    }
                ),
                contentDescription = "user_info_button_icon",
                tint = Color.Unspecified
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun ToHotCardManageRowPreview() {
    ToHotCardManageRow()
}
