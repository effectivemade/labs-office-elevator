package band.effective.office.tv.screen.photo

import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.*
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.tv.foundation.lazy.list.TvLazyListState
import androidx.tv.foundation.lazy.list.rememberTvLazyListState
import band.effective.office.tv.core.ui.screen_with_controls.ScreenWithControlsTemplate
import band.effective.office.tv.domain.autoplay.model.NavigateRequests
import band.effective.office.tv.screen.load.LoadScreen
import band.effective.office.tv.screens.photo.components.PhotoSlideShow

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun BestPhotoScreen(viewModel: PhotoViewModel = hiltViewModel()) {
    val uiState by viewModel.state.collectAsState()

    val lazyListState: TvLazyListState = rememberTvLazyListState()
    val (contentFocus, playButton) = remember { FocusRequester.createRefs() }

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is BestPhotoEffect.ChangePlayState -> {}
                is BestPhotoEffect.ScrollToItem -> {
                    when (effect.index) {
                        uiState.photos.size -> viewModel.sendEvent(
                            BestPhotoEvent.OnRequestSwitchScreen(
                                NavigateRequests.Forward
                            )
                        )
                        -1 -> viewModel.sendEvent(
                            BestPhotoEvent.OnRequestSwitchScreen(
                                NavigateRequests.Back
                            )
                        )
                        else -> lazyListState.animateScrollToItem(effect.index)
                    }
                }
                is BestPhotoEffect.ScrollToNextItem -> {
                    if (uiState.photos.size == lazyListState.firstVisibleItemIndex + 1) {
                        viewModel.sendEvent(BestPhotoEvent.OnRequestSwitchScreen(NavigateRequests.Forward))
                    } else {
                        lazyListState.animateScrollToItem(
                            lazyListState.firstVisibleItemIndex + 1
                        )
                    }
                }
            }
        }
    }

    when {
        uiState.isError -> {
            Box(modifier = Modifier.fillMaxSize()) {
                Text(
                    text = uiState.error,
                    modifier = Modifier.align(Alignment.Center),
                    fontSize = 44.sp,
                    color = Color.Red
                )
            }
        }
        uiState.isLoading -> {
            LoadScreen()
        }
        else -> {
            ScreenWithControlsTemplate(
                currentListPosition = lazyListState.firstVisibleItemIndex,
                countItems = uiState.photos.size,
                isPlay = uiState.isPlay,
                contentFocus = contentFocus,
                playButton = playButton,
                content = {
                    PhotoSlideShow(photos = uiState.photos,
                        lazyListState,
                        modifierForFocus = Modifier
                            .focusRequester(contentFocus)
                            .focusProperties {
                                down = playButton
                            }
                            .focusable())
                },
                onClickPlayButton = { viewModel.sendEvent(BestPhotoEvent.OnClickPlayButton) },
                onClickNextItemButton = {
                    viewModel.sendEvent(
                        BestPhotoEvent.OnClickNextItem(
                            lazyListState.firstVisibleItemIndex
                        )
                    )
                },
                onClickPreviousItemButton = {
                    viewModel.sendEvent(
                        BestPhotoEvent.OnClickPreviousItem(
                            lazyListState.firstVisibleItemIndex
                        )
                    )
                },
            )
        }
    }
}
