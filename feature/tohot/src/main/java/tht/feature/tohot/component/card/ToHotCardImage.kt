package tht.feature.tohot.component.card

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Size
import tht.feature.tohot.R
import tht.feature.tohot.userData

@Composable
fun ToHotCardImage(
    modifier: Modifier = Modifier,
    imageUrl: String,
    placeholder: Painter = painterResource(id = R.drawable.ic_location),
    error: Painter = painterResource(id = R.drawable.ic_location)
) {
    val model = ImageRequest.Builder(LocalContext.current)
        .data(imageUrl)
        .size(Size.ORIGINAL)
        .crossfade(true)
        .build()
    AsyncImage(
        modifier = modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(12.dp)),
        model = model,
        contentDescription = "card_image",
        placeholder = placeholder,
        error = error,
        contentScale = ContentScale.Crop
    )
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFF000000)
private fun ToHotCardImagePreview() {
    ToHotCardImage(
        imageUrl = userData.profileImgUrl.list.first()
    )
}
