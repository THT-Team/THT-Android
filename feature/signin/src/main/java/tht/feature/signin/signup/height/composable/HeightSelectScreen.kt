package tht.feature.signin.signup.height.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.compose_ui.component.spacer.Spacer
import com.example.compose_ui.component.text.ThtText
import com.example.compose_ui.component.text.headline.ThtHeadline5
import tht.core.ui.R
import tht.feature.signin.signup.height.HeightSelectNumberPicker

@Composable
internal fun HeightSelectScreen(
    minValue: Int,
    maxValue: Int,
    initialValue: Int,
    onSelectHeight: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectHeight by remember { mutableStateOf(initialValue) }
    Column(
        modifier = modifier
//            .height(200.dp)
            .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
            .background(
                color = colorResource(id = R.color.black_222222),
                shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
            )
            .padding(horizontal = 38.dp)
    ) {
        Spacer(space = 32.dp)
        Row {
            androidx.compose.foundation.layout.Spacer(modifier = Modifier.weight(1f))
            AndroidView(
                modifier = Modifier
                    .height(100.dp)
                    .width(78.dp),
                factory = { context ->
                    HeightSelectNumberPicker(context).apply {
                        this.minValue = minValue
                        this.maxValue = maxValue
                        value = initialValue
                        setOnValueChangedListener { _, _, newVal ->
                            selectHeight = newVal
                        }
                    }
                }
            )
            Spacer(space = 18.dp)
            ThtText(
                modifier = Modifier.align(Alignment.CenterVertically),
                text = "cm",
                fontWeight = FontWeight.Normal,
                textSize = 20.sp,
                color = colorResource(id = R.color.gray_686868)
            )
            androidx.compose.foundation.layout.Spacer(modifier = Modifier.weight(1f))
        }
        Spacer(space = 32.dp)
        Button(
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            onClick = {
                onSelectHeight(selectHeight)
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = colorResource(id = R.color.yellow_f9cc2e),
                contentColor = Color.Transparent,
                disabledBackgroundColor = colorResource(id = R.color.brown_26241f),
                disabledContentColor = Color.Transparent
            ),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            ThtHeadline5(
                text = stringResource(id = tht.feature.signin.R.string.do_auth),
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.black_222222)
            )
        }
        Spacer(space = 32.dp)
    }
}

@Composable
@Preview
private fun HeightSelectScreenPreview() {
    HeightSelectScreen(
        modifier = Modifier.fillMaxWidth(),
        minValue = 100,
        maxValue = 200,
        initialValue = 170,
        onSelectHeight = {}
    )
}
