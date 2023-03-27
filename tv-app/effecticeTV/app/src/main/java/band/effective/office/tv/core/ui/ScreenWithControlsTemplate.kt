package band.effective.office.tv.core.ui

import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.unit.dp
import band.effective.office.tv.screens.photo.components.SlideShowPhotoControl

@Composable
fun ScreenWithControlsTemplate(
    currentListPosition: Int,
    countItems: Int,
    nextButton: FocusRequester,
    prevButton: FocusRequester,
    playButton: FocusRequester,
    backToPhoto: FocusRequester,
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit,
    onClickPlayButton: () -> Unit,
    onClickNextItemButton: () -> Unit,
    onClickPreviousItemButton: () -> Unit
) {
    Box(
        modifier = modifier.fillMaxSize(),
    ) {
        content()
        SlideShowPhotoControl(
            currentListPosition = currentListPosition,
            countItems = countItems,
            prevButton = prevButton,
            nextButton = nextButton,
            playButton = playButton,
            backToPhoto = backToPhoto,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 30.dp),
            onClickPlayButton = {
                onClickPlayButton()
            },
            onClickNextItemButton = {
                onClickNextItemButton()
            },
            onClickPreviousItemButton = {
                onClickPreviousItemButton()
            }
        )
    }
}