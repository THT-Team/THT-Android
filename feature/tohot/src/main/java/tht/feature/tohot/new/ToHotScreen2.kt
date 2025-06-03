package tht.feature.tohot.new

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
internal fun ToHotScreen2(modifier: Modifier = Modifier) {
    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        items(1000) {
            Text(text = "$it 아이템입니다.", color = Color.Gray)
        }
    }
}
