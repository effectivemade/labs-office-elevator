package band.effective.office.tv.screens.photo.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import band.effective.office.tv.screens.photo.model.Photo
import coil.compose.SubcomposeAsyncImage

@Composable
fun PhotoUIItem(image: Photo, modifier: Modifier = Modifier) {

    val photoWith = LocalConfiguration.current.screenWidthDp.dp
    val photoHeight = LocalConfiguration.current.screenHeightDp.dp

    Box(
        modifier = modifier
            .fillMaxSize()
            .clickable { }
    ) {
        SubcomposeAsyncImage(
            modifier = Modifier.size(
                width = photoWith,
                height = photoHeight,
            ),
            model = image.photoThumb,
            loading = { CircularProgressIndicator() },
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
        )
    }
}