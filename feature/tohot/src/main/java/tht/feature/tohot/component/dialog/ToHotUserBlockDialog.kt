package tht.feature.tohot.component.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.compose_ui.component.text.headline.ThtHeadline5
import com.example.compose_ui.component.text.p.ThtP1
import com.example.compose_ui.component.text.subtitle.ThtSubtitle2
import tht.feature.tohot.R

@Composable
fun ToHotUserBlockDialog(
    modifier: Modifier = Modifier,
    isShow: Boolean,
    onBlockClick: () -> Unit = {},
    onCancelClick: () -> Unit = {},
    onDismiss: () -> Unit
) {
    if (!isShow) return
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        )
    ) {
        Column(
            modifier = modifier
                .clip(RoundedCornerShape(8.dp))
                .background(color = colorResource(id = tht.core.ui.R.color.black_222222))
        ) {
            ThtHeadline5(
                modifier = Modifier
                    .padding(top = 20.dp)
                    .fillMaxWidth(),
                text = stringResource(id = R.string.block_dialog_title),
                fontWeight = FontWeight.SemiBold,
                color = colorResource(id = tht.core.ui.R.color.white_f9fafa)
            )

            ThtP1(
                modifier = Modifier
                    .padding(top = 6.dp)
                    .fillMaxWidth(),
                text = stringResource(id = R.string.block_dialog_content),
                fontWeight = FontWeight.Normal,
                color = colorResource(id = tht.core.ui.R.color.gray_8d8d8d)
            )

            Box(
                modifier = Modifier
                    .padding(top = 4.dp)
                    .clickable(
                        enabled = true,
                        onClick = onBlockClick
                    )
            ) {
                ThtSubtitle2(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp, bottom = 16.dp),
                    text = stringResource(id = R.string.block_dialog_block),
                    fontWeight = FontWeight.SemiBold,
                    color = colorResource(id = tht.core.ui.R.color.white_f9fafa)
                )
            }

            Divider(
                modifier = Modifier.height(1.5.dp),
                color = colorResource(id = tht.core.ui.R.color.gray_666666)
            )

            Box(
                modifier = Modifier.clickable(
                    enabled = true,
                    onClick = onCancelClick
                )
            ) {
                ThtSubtitle2(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, bottom = 20.dp),
                    text = stringResource(id = R.string.cancel),
                    fontWeight = FontWeight.SemiBold,
                    color = colorResource(id = tht.core.ui.R.color.white_f9fafa)
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun ToHotUserBlockDialogPreview() {
    ToHotUserBlockDialog(
        isShow = true,
        onDismiss = { }
    )
}
