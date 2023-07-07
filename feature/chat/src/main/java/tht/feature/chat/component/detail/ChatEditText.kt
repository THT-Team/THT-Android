package tht.feature.chat.component.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose_ui.component.spacer.Spacer
import com.example.compose_ui.extensions.noRippleClickable
import tht.feature.chat.R

@Composable
fun ChatEditTextContainer(
    modifier: Modifier,
    text: String,
    onChangedText: (String) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(59.dp)
            .background(Color(0xFF161616))
            .drawBehind {
                drawLine(
                    color = Color(0xFF222222),
                    strokeWidth = 1f,
                    start = Offset(0f, 0f),
                    end = Offset(size.width, 0f),
                )
            }
            .padding(horizontal = 17.dp, vertical = 12.dp)
            .then(modifier),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        Image(
            modifier = Modifier
                .noRippleClickable { },
            painter = painterResource(id = R.drawable.ic_attachment),
            contentDescription = "파일 첨부 버튼",
        )
        Spacer(space = 10.dp)
        ChatEditText(
            modifier = Modifier.weight(1f),
            text = text,
            onChangedText = onChangedText,
        )
        Spacer(space = 10.dp)
        Image(
            modifier = Modifier
                .noRippleClickable { },
            painter = painterResource(id = R.drawable.ic_sent),
            contentDescription = "보내기 버튼",
        )
    }
}

@Composable
fun ChatEditText(
    modifier: Modifier,
    text: String,
    onChangedText: (String) -> Unit,
) {
    BasicTextField(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(17.dp))
            .border(color = Color(0xFF222222), width = 1.dp, shape = RoundedCornerShape(17.dp))
            .background(Color(0xFF222222))
            .padding(horizontal = 12.dp, vertical = 6.dp)
            .then(modifier),
        value = text,
        onValueChange = onChangedText,
        textStyle = TextStyle(color = Color.White),
    )
}

@Composable
@Preview(name = "채팅창 하단 컴포넌트")
fun ChatEditTextContainer() {
    ChatEditTextContainer(
        modifier = Modifier,
        text = "테스트",
        onChangedText = {},
    )
}
