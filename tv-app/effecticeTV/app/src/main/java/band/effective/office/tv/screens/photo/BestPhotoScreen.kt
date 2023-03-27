package band.effective.office.tv.screens.photo

import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.tv.foundation.lazy.list.TvLazyListState
import androidx.tv.foundation.lazy.list.rememberTvLazyListState
import band.effective.office.tv.BuildConfig
import band.effective.office.tv.core.ui.ScreenWithControlsTemplate
import band.effective.office.tv.screens.photo.components.PhotoSlideShow
import band.effective.office.tv.screens.photo.components.SlideShowPhotoControl
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun BestPhotoScreen( viewModel: PhotoViewModel = hiltViewModel()) {
    val uiState by viewModel.state.collectAsState()

    val lazyListState: TvLazyListState = rememberTvLazyListState()
    val (photo, prevButton, nextButton, playButton) = remember { FocusRequester.createRefs() }

    LaunchedEffect(Unit) {
        viewModel.effect.collect{effect ->
            when(effect){
                is BestPhotoEffect.ChangePlayState -> {}
                is BestPhotoEffect.ScrollToItem -> lazyListState.animateScrollToItem(effect.index)
                is BestPhotoEffect.ScrollToNextItem -> lazyListState.animateScrollToItem(
                    lazyListState.firstVisibleItemIndex + 1
                )
            }
        }
    }

    ScreenWithControlsTemplate(
        currentListPosition = lazyListState.firstVisibleItemIndex,
        countItems = uiState.photos.size,
        nextButton = nextButton,
        prevButton = prevButton,
        playButton = playButton,
        backToPhoto = photo,
        content = {
            PhotoSlideShow(photos = uiState.photos, lazyListState,
                modifierForFocus = Modifier
                    .focusRequester(photo)
                    .focusProperties {
                        down = playButton
                    }
                    .focusable()
            )
        },
        onClickPlayButton = { viewModel.sendEvent(BestPhotoEvent.OnClickPlayButton) },
        onClickNextItemButton = { viewModel.sendEvent(BestPhotoEvent.OnClickNextItem(
            lazyListState.firstVisibleItemIndex
        )) },
        onClickPreviousItemButton = {
            viewModel.sendEvent(BestPhotoEvent.OnClickPreviousItem(
                lazyListState.firstVisibleItemIndex
            ))
        }
    )
}

