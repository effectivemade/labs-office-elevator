package band.effective.office.tv.core.ui

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import coil.compose.rememberAsyncImagePainter
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import coil.size.Size

@Composable
fun ImageSVG(country: String) {
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .decoderFactory(SvgDecoder.Factory())
            .data("https://upload.wikimedia.org/wikipedia/commons/d/d7/Android_robot.svg")
            .size(Size.ORIGINAL) // Set the target size to load the image at.
            .build()
    )
    Image(
        painter = painter,
        contentDescription = null
    )
}