package band.effective.office.tv.core.ui.screen_with_controls.components

import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.unit.dp
import band.effective.office.tv.R
import band.effective.office.tv.core.ui.screen_with_controls.components.ButtonControls

@Composable
fun SlideShowPhotoControl(
    currentListPosition: Int,
    countItems: Int,
    isPlay: Boolean,
    modifier: Modifier = Modifier,
    prevButton: FocusRequester,
    nextButton: FocusRequester,
    playButton: FocusRequester,
    backToPhoto: FocusRequester,
    onClickPlayButton: () -> Unit,
    onClickNextItemButton: () -> Unit,
    onClickPreviousItemButton: () -> Unit,
) {
    var focusPreviousButton by remember { mutableStateOf(false) }
    var focusNextButton by remember { mutableStateOf(false) }
    var focusPlayButton by remember { mutableStateOf(false) }

    Row(modifier = modifier
        .focusable()
    ) {
        if (currentListPosition > 0) {
            ButtonControls(
                isFocus = focusPreviousButton,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .focusRequester(prevButton)
                    .onFocusChanged { state ->
                        focusPreviousButton = state.isFocused
                    }
                    .focusProperties {
                        up = backToPhoto
                        down = backToPhoto
                        previous = backToPhoto
                        right = playButton
                        left = nextButton
                        next = playButton
                    }
                    .focusable()
                    .size(70.dp),
                idActiveIcon = R.drawable.ic_previous_active,
                idInactiveIcon = R.drawable.ic_previous_inactive,
                onClick = {
                    if(currentListPosition == 1) {
                        prevButton.freeFocus()
                        playButton.requestFocus()
                    }
                    onClickPreviousItemButton()
                }
            )
        } else
            Spacer(modifier = Modifier.width(70.dp))

        ButtonControls(
            isFocus = focusPlayButton,
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .focusRequester(playButton)
                .onFocusChanged { state ->
                    focusPlayButton = state.isFocused
                }
                .focusProperties {
                    up = backToPhoto
                    down = backToPhoto
                    previous = backToPhoto
                    left = prevButton
                    right = nextButton
                    next = nextButton
                }
                .focusable()
                .size(80.dp),
            idActiveIcon = if (!isPlay) R.drawable.ic_play_active else R.drawable.ic_pause_active,
            idInactiveIcon = if (!isPlay) R.drawable.ic_play_inactive else R.drawable.ic_pause_inactive,
            onClick = onClickPlayButton

        )
        if (currentListPosition < countItems - 1) {
            ButtonControls(
                isFocus = focusNextButton,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .focusRequester(nextButton)
                    .onFocusChanged { state ->
                        focusNextButton = state.isFocused
                    }
                    .focusProperties {
                        up = backToPhoto
                        down = backToPhoto
                        previous = backToPhoto
                        right = prevButton
                        left = playButton
                    }
                    .focusable()
                    .size(70.dp),
                idActiveIcon = R.drawable.ic_next_active,
                idInactiveIcon = R.drawable.ic_next_inactive,
                onClick = {
                    if (currentListPosition == countItems - 2){
                        nextButton.freeFocus()
                        playButton.requestFocus()
                    }
                    onClickNextItemButton()
                }
            )
        } else
            Spacer(modifier = Modifier.width(70.dp))
    }
}
