package com.example.compose_ui.component.image

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest

@Composable
fun ThtImage(
    modifier: Modifier,
    src: String?,
    size: DpSize,
    errorImage: (@Composable () -> Unit)? = null,
) {
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(src)
            .build(),
    )
    when (painter.state) {
        is AsyncImagePainter.State.Error -> {
            if (errorImage != null) {
                errorImage()
            }
        }

        else -> {
            Image(
                modifier = modifier
                    .size(size)
                    .clip(RoundedCornerShape(12.dp)),
                painter = painter,
                contentDescription = null,
                contentScale = ContentScale.Crop,
            )
        }
    }
}
