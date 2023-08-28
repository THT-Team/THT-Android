package com.example.compose_ui.component.image

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size

@Composable
fun transBitmap(
    imageUrl: String,
    placeholder: Int,
    error: Int,
): Bitmap? {
    val model = ImageRequest.Builder(LocalContext.current)
        .data(imageUrl)
        .size(Size.ORIGINAL)
        .crossfade(true)
        .placeholder(placeholder)
        .error(error)
        .build()
    val painter = rememberAsyncImagePainter(model = model)
    return ((painter.state as? AsyncImagePainter.State.Success)?.result?.drawable as BitmapDrawable).bitmap
}
