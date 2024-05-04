package tht.feature.signin.terms.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose_ui.component.spacer.Spacer
import com.example.compose_ui.component.text.headline.ThtHeadline5
import tht.core.ui.R


@Composable
internal fun SignupLargeSelectButton(
    text: String,
    isSelect: Boolean,
    enable: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Button(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        enabled = enable,
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = colorResource(
                id = if (isSelect) {
                    R.color.black_2c2c2c
                } else {
                    R.color.black_222222
                }
            ),
            contentColor = Color.Transparent,
            disabledBackgroundColor = colorResource(id = R.color.black_222222),
            disabledContentColor = Color.Transparent
        ),
        contentPadding = PaddingValues(vertical = 16.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                modifier = Modifier.align(Alignment.CenterStart)
                    .padding(start = 20.dp),
                painter = painterResource(
                    id = if (isSelect) {
                        tht.feature.signin.R.drawable.ic_check_circle_selected
                    } else {
                        tht.feature.signin.R.drawable.ic_check_circle_unselected
                    }
                ),
                contentDescription = "ic_circle_check",
                tint = Color.Unspecified
            )
            ThtHeadline5(
                modifier = Modifier.align(Alignment.Center),
                text = text,
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.white_f9fafa)
            )
        }
    }
}

@Composable
@Preview
private fun SignupLargeSelectButtonPreview() {
    Column {
        SignupLargeSelectButton(
            modifier = Modifier,
            text = "전체 동의",
            isSelect = false,
            enable = true,
            onClick = {}
        )
        Spacer(space = 20.dp)
        SignupLargeSelectButton(
            modifier = Modifier,
            text = "전체 동의",
            isSelect = true,
            enable = true,
            onClick = {}
        )
        Spacer(space = 20.dp)
        SignupLargeSelectButton(
            modifier = Modifier,
            text = "전체 동의",
            isSelect = true,
            enable = false,
            onClick = {}
        )
    }
}
