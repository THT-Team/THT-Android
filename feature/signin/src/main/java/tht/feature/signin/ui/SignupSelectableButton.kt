package tht.feature.signin.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose_ui.component.spacer.Spacer
import com.example.compose_ui.component.text.headline.ThtHeadline4

@Composable
internal fun SignupSelectableButton(
    isSelect: Boolean,
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(
                color = colorResource(
                    id = if (isSelect) {
                        tht.core.ui.R.color.yellow_f9cc2e
                    } else {
                        tht.core.ui.R.color.brown_26241f
                    }
                ),
                shape = RoundedCornerShape(10.dp)
            )
            .clickable(
                enabled = true,
                onClick = onClick
            )
            .padding(vertical = 12.dp, horizontal = 24.dp),
        contentAlignment = Alignment.Center
    ) {
        ThtHeadline4(
            text = title,
            fontWeight = FontWeight.SemiBold,
            color = colorResource(id = tht.core.ui.R.color.black_111111)
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun SignupSelectableButtonPreview() {
    Row(
        modifier = Modifier.fillMaxWidth()
            .padding(horizontal = 38.dp)
    ) {
        SignupSelectableButton(
            modifier = Modifier.weight(1f),
            isSelect = false,
            title = "가끔",
            onClick = {}
        )
        Spacer(space = 16.dp)
        SignupSelectableButton(
            modifier = Modifier.weight(1f),
            isSelect = true,
            title = "가끔",
            onClick = {}
        )
        Spacer(space = 16.dp)
        SignupSelectableButton(
            modifier = Modifier.weight(1f),
            isSelect = false,
            title = "가끔",
            onClick = {}
        )
    }
}
