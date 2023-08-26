package tht.feature.tohot.component.card

import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import tht.feature.tohot.R
import tht.feature.tohot.userData

@Composable
fun <T> ToHotBlurCardImage(
    modifier: Modifier = Modifier,
    imageUrl: T,
    placeholder: Int = R.drawable.ic_user_card_placeholder,
    error: Int = R.drawable.ic_user_card_error,
    loadFinishListener: (Boolean, Throwable?) -> Unit = { _, _ -> }
) {
    val model = ImageRequest.Builder(LocalContext.current)
        .data(imageUrl)
        .size(Size.ORIGINAL)
        .crossfade(true)
        .placeholder(placeholder)
        .error(error)
        .build()
    val painter = rememberAsyncImagePainter(model = model)

    when (painter.state) {
        is AsyncImagePainter.State.Success -> loadFinishListener(true, null)

        is AsyncImagePainter.State.Error -> loadFinishListener(
            false,
            (painter.state as AsyncImagePainter.State.Error).result.throwable
        )

        else -> {}
    }

    if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.S_V2) {

    } else {

    }

    painter.state.painter?.let {
        Image(
            modifier = modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(12.dp)),
            painter = it,
            contentDescription = "card_image"
        )
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFF000000)
private fun ToHotBlurCardImagePreview() {
    ToHotBlurCardImage(
        imageUrl = userData.profileImgUrl.list.first()
    )
}
