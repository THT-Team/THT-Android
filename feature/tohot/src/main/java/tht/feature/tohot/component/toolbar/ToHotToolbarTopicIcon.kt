package tht.feature.tohot.component.toolbar

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpSize
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Size
import tht.feature.tohot.R

@Composable
fun ToHotToolbarTopicIcon(
    modifier: Modifier = Modifier,
    size: DpSize,
    topicIconUrl: String,
    topicIconRes: Int,
) {
    val model = ImageRequest.Builder(LocalContext.current)
        .data(topicIconUrl)
        .size(Size.ORIGINAL)
        .crossfade(true)
        .build()
    AsyncImage(
        modifier = modifier.size(size),
        model = model,
        contentDescription = "toolbar_topic_icon",
        placeholder = painterResource(id = R.drawable.ic_topic_placeholder_38),
        error = painterResource(topicIconRes),
        contentScale = ContentScale.Crop
    )
}
