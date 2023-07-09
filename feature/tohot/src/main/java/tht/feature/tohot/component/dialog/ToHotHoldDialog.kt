package tht.feature.tohot.component.dialog

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.compose_ui.component.text.headline.ThtHeadline4
import com.example.compose_ui.component.text.headline.ThtHeadline5
import com.example.compose_ui.component.text.p.ThtP1
import tht.feature.tohot.R

@Composable
fun ToHotHoldDialog(
    modifier: Modifier = Modifier,
    isShow: Boolean,
    onRestartClick: () -> Unit = {},
    onDismiss: () -> Unit = {}
) {
    if (!isShow) return
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false
        )
    ) {
        Column(
            modifier = modifier
                .wrapContentHeight()
                .clip(RoundedCornerShape(12.dp))
                .background(color = colorResource(id = tht.core.ui.R.color.black_222222))
        ) {
            Image(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 53.dp, start = 67.dp, end = 67.dp),
                painter = painterResource(id = R.drawable.ic_mudy_sleep),
                contentDescription = "mudy_sleep"
            )
            ThtHeadline4(
                modifier = Modifier
                    .padding(top = 15.dp)
                    .fillMaxWidth(),
                text = stringResource(id = R.string.to_hot_hold_dialog_title),
                fontWeight = FontWeight.SemiBold,
                color = colorResource(id = tht.core.ui.R.color.white_f9fafa)
            )

            ThtP1(
                modifier = Modifier
                    .padding(top = 6.dp)
                    .fillMaxWidth(),
                text = stringResource(id = R.string.to_hot_hold_dialog_content),
                fontWeight = FontWeight.Medium,
                color = colorResource(id = tht.core.ui.R.color.gray_8d8d8d)
            )

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 28.dp, vertical = 32.dp)
                    .clip(RoundedCornerShape(12.dp)),
                onClick = onRestartClick,
                contentPadding = PaddingValues(0.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = colorResource(id = tht.core.ui.R.color.yellow_f9cc2e)
                )
            ) {
                ThtHeadline5(
                    modifier = Modifier.padding(vertical = 15.dp),
                    text = stringResource(id = R.string.restart),
                    fontWeight = FontWeight.Bold,
                    color = colorResource(id = tht.core.ui.R.color.black_222222)
                )
            }
        }
    }
}

@Composable
@Preview
fun ToHotHoldDialog() {
    ToHotHoldDialog(
        isShow = true
    )
}
