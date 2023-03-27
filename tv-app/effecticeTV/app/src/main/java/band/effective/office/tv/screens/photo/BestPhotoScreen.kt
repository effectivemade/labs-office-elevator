package band.effective.office.tv.screens.photo

import android.util.Log
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
import androidx.compose.ui.input.key.*
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
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun BestPhotoScreen( viewModel: PhotoViewModel = hiltViewModel()) {
    val uiState by viewModel.state.collectAsState()

    val scope = rememberCoroutineScope()
    val lazyListState: TvLazyListState = rememberTvLazyListState()
    val (photo, prevButton, nextButton, playButton) = remember { FocusRequester.createRefs() }
    var controlsVisible by remember { mutableStateOf(true) }

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
        isPlay = uiState.isPlay,
        isVisible = controlsVisible,
        nextButton = nextButton,
        prevButton = prevButton,
        playButton = playButton,
        backToPhoto = photo,
        content = {
            PhotoSlideShow(
                photos = uiState.photos, lazyListState,
                modifierForFocus = Modifier
                    .focusRequester(photo)
                    .focusProperties {
                        down = playButton
                    }
                    .focusable(),
                modifier = Modifier
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
        },
        modifier = Modifier
            .onKeyEvent {
            Log.d("BUTTON", "click button ${it.key.keyCode}")
            if ((!listOf(Key.DirectionCenter, Key.Enter).contains(it.key) ||
                it.key.keyCode == 98784247808L) && it.type == KeyEventType.KeyDown) { // NOTE: this number for emulator dpad
                controlsVisible = !controlsVisible
            }
            return@onKeyEvent true
        }
    )
}

