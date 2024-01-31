package tht.feature.chat.chat.screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import com.tht.tht.data.di.websocket.AllMarketTickerResponseItem
import tht.feature.chat.chat.viewmodel.ChatDetailViewModel

@Composable
internal fun ChatDetailScreen(
    viewModel: ChatDetailViewModel = hiltViewModel()
) {
//    LaunchedEffect(key1 = Unit) {
//        viewModel.getChatList()
//    }
//
//    val state = viewModel.collectAsState().value
//    val currentText = viewModel.currentText.collectAsState().value
//    Box(modifier = Modifier.fillMaxSize()) {
//        Column(modifier = Modifier.fillMaxSize()) {
//            ChatDetailTopAppBar(
//                title = "마음",
//                onClickBack = { },
//                onClickReport = {},
//                onClickLogout = {}
//            )
//            Box(modifier = Modifier.weight(1f)) {
//                when (state) {
//                    is ChatDetailState.ChatList -> ChatDetailList(state.chatList)
//                }
//            }
//        }
//        ChatEditTextContainer(
//            modifier = Modifier.align(Alignment.BottomCenter),
//            text = currentText,
//            onChangedText = viewModel::updateCurrentText
//        )
//    }
}

@Composable
@Preview(showBackground = true)
fun ChatDetailScreenPreview() {
    ChatDetailScreen()
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CryptoPriceItem(
    item: AllMarketTickerResponseItem
) {
    Row(
        modifier = Modifier.padding(top = 10.dp, bottom = 10.dp, end = 4.dp, start = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = item.s,
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
        )
        Spacer(modifier = Modifier.weight(1f))
        Column(
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = item.P + "%",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = if (item.P.contains("-")) Color.Blue else Color.Red
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "$" + item.c,
                fontSize = 14.sp,
            )
        }
    }
    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(Color.Gray)
    )
}
