package band.effective.office.tv.screen.photo.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import band.effective.office.tv.core.network.UnsafeOkHttpClient
import band.effective.office.tv.screen.load.LoadScreen
import band.effective.office.tv.screen.photo.model.Photo
import coil.ImageLoader
import coil.compose.SubcomposeAsyncImage


@Composable
fun PhotoUIItem(image: Photo, modifier: Modifier = Modifier) {

    val photoWith = LocalConfiguration.current.screenWidthDp.dp
    val photoHeight = LocalConfiguration.current.screenHeightDp.dp

    val imageLoader = ImageLoader.Builder(LocalContext.current)
        .okHttpClient {
            UnsafeOkHttpClient.getUnsafeOkHttpClient()
                .build()
        }
        .build()

    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        SubcomposeAsyncImage(
            modifier = Modifier.size(
                width = photoWith,
                height = photoHeight,
            ),
            imageLoader = imageLoader,
            model = image.photoThumb,
            loading = { LoadScreen("Best photo") },
            contentDescription = null,
            contentScale = ContentScale.Crop,
            error = {
                Text(
                    text = "Мы пока не поддердиваем этот формат фото, позже все будет ок)",
                    fontSize = 40.sp,
                    color = Color.Red,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxSize()
                )
            }
        )
    }
}