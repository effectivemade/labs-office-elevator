package band.effective.office.tv.screens.photo.components

import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import band.effective.office.tv.screens.photo.model.Photo
import coil.compose.SubcomposeAsyncImage

@Composable
fun PhotoUIItem(image: Photo) {
    SubcomposeAsyncImage(
        model = image.url,
        loading = { CircularProgressIndicator() },
        contentDescription = null
    )
}