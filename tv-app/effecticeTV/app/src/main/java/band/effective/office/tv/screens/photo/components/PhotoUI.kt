package band.effective.office.tv.screens.photo.components

import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import coil.compose.SubcomposeAsyncImage

@Composable
fun PhotoUI(image: String) {
    SubcomposeAsyncImage(
        model = image,
        loading = { CircularProgressIndicator() },
        contentDescription = null
    )
}