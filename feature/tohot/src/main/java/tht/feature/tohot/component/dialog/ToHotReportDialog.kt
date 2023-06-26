package tht.feature.tohot.component.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import com.example.compose_ui.component.text.subtitle.ThtSubtitle2
import tht.feature.tohot.R

@Composable
fun ToHotUseReportDialog(
    modifier: Modifier = Modifier,
    reportReason: List<String>,
    isShow: Boolean,
    onReportClick: (Int) -> Unit = {},
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
                    .fillMaxWidth()
                    .padding(top = 20.dp),
                text = stringResource(id = R.string.report_dialog_title),
                fontWeight = FontWeight.SemiBold,
                color = colorResource(id = tht.core.ui.R.color.white_f9fafa)
            )

            Spacer(modifier = Modifier.height(14.dp))

            reportReason.forEachIndexed { idx, reason ->
                Box(
                    modifier = Modifier
                        .clickable(
                            enabled = true,
                            onClick = { onReportClick(idx) }
                        )
                ) {
                    ThtSubtitle2(
                        modifier = if (idx == reportReason.size - 1) {
                            Modifier
                                .fillMaxWidth()
                                .padding(top = 10.dp, bottom = 16.dp)
                        } else {
                            Modifier
                                .fillMaxWidth()
                                .padding(vertical = 10.dp)
                        },
                        text = reason,
                        fontWeight = FontWeight.Normal,
                        color = colorResource(id = tht.core.ui.R.color.white_f9fafa)
                    )
                }
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
fun ToHotUseReportDialogPreview() {
    ToHotUseReportDialog(
        isShow = true,
        reportReason = listOf(
            "불쾌한 사진",
            "허위 프로필",
            "사진 도용",
            "욕설 및 비방",
            "불법 촬영물 공유"
        ),
        onDismiss = { }
    )
}
