package band.effective.office.elevator.common.compose.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.toComposeImageBitmap
import androidx.compose.ui.layout.ContentScale
import band.effective.office.elevator.common.compose.utils.toSkiaImage
import platform.UIKit.UIImage

@Composable
actual fun LocalImage(
    imageResourceName: String, modifier: Modifier, contentDescription: String?
) {
    val painter = remember {
        UIImage.imageNamed(imageResourceName)?.toSkiaImage()?.toComposeImageBitmap()
            ?.let(::BitmapPainter)
    }
    if (painter != null) {
        androidx.compose.foundation.Image(
            modifier = modifier,
            painter = painter,
            contentDescription = contentDescription,
            contentScale = ContentScale.FillWidth,
        )
    }
}