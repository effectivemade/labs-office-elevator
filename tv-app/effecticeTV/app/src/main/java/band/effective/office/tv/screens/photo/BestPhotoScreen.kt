package band.effective.office.tv.screens.photo

import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.focusRequester
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.tv.foundation.lazy.list.TvLazyListState
import androidx.tv.foundation.lazy.list.rememberTvLazyListState
import band.effective.office.tv.screens.photo.components.PhotoSlideShow
import band.effective.office.tv.screens.photo.components.SlideShowPhotoControl

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun BestPhotoScreen( viewModel: PhotoViewModel = hiltViewModel()) {
    val uiState by viewModel.state.collectAsState()

    val lazyListState: TvLazyListState = rememberTvLazyListState()
    val (photo, prevButton, nextButton, playButton, controls) = remember { FocusRequester.createRefs() }

    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        PhotoSlideShow(photos = uiState.photos, lazyListState,
            modifier = Modifier
                .focusRequester(photo)
                .focusProperties {
                    down = playButton
                }
                .focusable()
        )

        SlideShowPhotoControl(
            currentListPosition = lazyListState.firstVisibleItemIndex,
            countItems = uiState.photos.size,
            controls = controls,
            prevButton = prevButton,
            nextButton = nextButton,
            playButton = playButton,
            backToPhoto = photo,
            modifier = Modifier
                .align(Alignment.BottomCenter),
            onClickPlayButton = { /*TODO*/ },
            onClickNextItemButton = { /*TODO*/ }) {
        }
    }
}