package tht.feature.tohot.component.dialog

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import tht.feature.tohot.tohot.screen.ToHotMatchingScreen

@Composable
fun ToHotUserMatchingDialog(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    onChatClick: () -> Unit,
    isShow: Boolean,
    imageUrl: String
) {
    AnimatedVisibility(
        visible = isShow,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Dialog(
            onDismissRequest = onDismissRequest,
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = false,
                usePlatformDefaultWidth = false
            )
        ) {
            ToHotMatchingScreen(
                modifier = modifier.fillMaxSize(),
                imageUrl = imageUrl,
                onChatClick = onChatClick,
                onCloseClick = onDismissRequest
            )
        }
    }
}

@Composable
@Preview
private fun ToHotUserMatchingDialogPreview() {
    ToHotUserMatchingDialog(
        isShow = true,
        imageUrl = "https://www.naver.com",
        onDismissRequest = {},
        onChatClick = {}
    )
}
