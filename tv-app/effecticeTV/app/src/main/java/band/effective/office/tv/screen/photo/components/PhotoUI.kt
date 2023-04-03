package band.effective.office.tv.screen.photo.components

import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import band.effective.office.tv.screen.photo.model.Photo
import coil.compose.SubcomposeAsyncImage

@Composable
fun PhotoUIItem(image: Photo) {
    SubcomposeAsyncImage(
        model = image.photoThumb,
        loading = { CircularProgressIndicator() },
        contentDescription = null
    )
}