package band.effective.office.tv.screens.photo.components

import android.util.Log
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import band.effective.office.tv.R

@Composable
fun SlideShowPhotoControl(
    currentListPosition: Int,
    countItems: Int,
    modifier: Modifier = Modifier,
    prevButton: FocusRequester,
    nextButton: FocusRequester,
    playButton: FocusRequester,
    backToPhoto: FocusRequester,
    onClickPlayButton: () -> Unit,
    onClickNextItemButton: () -> Unit,
    onClickPreviousItemButton: () -> Unit,
) {
    var isPlay by remember { mutableStateOf(true) }
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
                        previous = backToPhoto
                        right = playButton
                        next = playButton
                    }
                    .focusable(),
                idActiveIcon = R.drawable.ic_previous_active,
                idInactiveIcon = R.drawable.ic_previous_inactive,
                onClick = onClickPreviousItemButton
            )
        }
        //TODO( think about the transfer of play state)
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
                    previous = backToPhoto
                    left = prevButton
                    right = nextButton
                    next = nextButton
                }
                .focusable(),
            idActiveIcon = if (!isPlay) R.drawable.ic_play_active else R.drawable.ic_pause_active,
            idInactiveIcon = if (!isPlay) R.drawable.ic_play_inactive else R.drawable.ic_pause_inactive,
            onClick = {
                isPlay = !isPlay
                onClickPlayButton()
            }
        )
        if (currentListPosition != countItems) {
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
                        previous = backToPhoto
                        right = prevButton
                        left = playButton
                    }
                    .focusable(),
                idActiveIcon = R.drawable.ic_next_active,
                idInactiveIcon = R.drawable.ic_next_inactive,
                onClick = onClickNextItemButton
            )
        }
    }
}
