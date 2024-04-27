package tht.feature.signin.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose_ui.component.text.p.ThtP2
import tht.feature.signin.R

@Composable
internal fun SignupDescription(
    description: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.Top
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_error),
            contentDescription = "ic_error"
        )
        ThtP2(
            modifier = Modifier
                .padding(start = 6.dp)
                .weight(1f),
            text = description,
            fontWeight = FontWeight.Medium,
            color = colorResource(id = tht.core.ui.R.color.gray_666666),
            textAlign = TextAlign.Start,
            includeFontPadding = false
        )
    }
}

@Composable
@Preview
private fun SignupDescriptionPreview() {
    SignupDescription(
        modifier = Modifier.fillMaxWidth(),
        description = "Description",
    )
}
