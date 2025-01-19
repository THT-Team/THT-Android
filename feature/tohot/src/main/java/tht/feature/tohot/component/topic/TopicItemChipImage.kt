package tht.feature.tohot.component.topic

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import coil.size.Size
import tht.feature.tohot.R

@Composable
fun <T> TopicItemChipImage(
    modifier: Modifier = Modifier,
    imageUrl: T?,
    placeholder: Painter = painterResource(id = R.drawable.ic_topic_item_placeholder_48),
    error: Painter,
    loadFinishListener: (Boolean?, Throwable?) -> Unit = { _, _ -> }
) {
    if (imageUrl == null) {
        Image(
            modifier = modifier,
            painter = error,
            contentDescription = "topic_item"
        )
    } else {
        val context = LocalContext.current
        val imageLoader = remember {
            ImageLoader.Builder(context)
                .components {
                    add(SvgDecoder.Factory())
                }
                .build()
        }
        val model = remember(imageUrl) {
            ImageRequest.Builder(context)
                .data(imageUrl)
                .size(Size.ORIGINAL)
                .crossfade(true)
                .build()
        }
        AsyncImage(
            modifier = modifier,
            model = model,
            contentDescription = "topic_image",
            placeholder = placeholder,
            error = error,
            imageLoader = imageLoader,
            contentScale = ContentScale.Crop,
            onSuccess = { loadFinishListener(true, null) },
            onError = { loadFinishListener(null, it.result.throwable) }
        )
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFF000000)
private fun TopicItemChipImagePreview() {
    TopicItemChipImage(
        imageUrl = null,
        error = painterResource(id = R.drawable.ic_topic_item_fun_48)
    )
}
