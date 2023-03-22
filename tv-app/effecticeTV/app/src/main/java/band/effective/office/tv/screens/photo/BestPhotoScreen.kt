package band.effective.office.tv.screens.photo

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.tv.foundation.lazy.list.TvLazyListState
import androidx.tv.foundation.lazy.list.TvLazyRow
import androidx.tv.foundation.lazy.list.items
import androidx.tv.foundation.lazy.list.rememberTvLazyListState
import band.effective.office.tv.screens.photo.components.PhotoSlideShow
import band.effective.office.tv.screens.photo.components.PhotoUIItem
import band.effective.office.tv.screens.photo.components.SlideShowPhotoControl

@Composable
fun BestPhotoScreen( viewModel: PhotoViewModel = hiltViewModel()) {
    val uiState by viewModel.state.collectAsState()

    val lazyListState: TvLazyListState = rememberTvLazyListState()

    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        PhotoSlideShow(photos = uiState.photos, lazyListState)

        SlideShowPhotoControl(
            currentListPosition = lazyListState.firstVisibleItemIndex,
            countItems = uiState.photos.size,
            modifier = Modifier
                .align(Alignment.BottomCenter),
            onClickPlayButton = { /*TODO*/ },
            onClickNextItemButton = { /*TODO*/ }) {

        }
    }
}