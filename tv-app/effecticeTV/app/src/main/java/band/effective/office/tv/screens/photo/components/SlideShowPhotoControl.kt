package band.effective.office.tv.screens.photo.components

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import band.effective.office.tv.R

@Composable
fun SlideShowPhotoControl(
    currentListPosition: Int,
    countItems: Int,
    modifier: Modifier = Modifier,
    onClickPlayButton: () -> Unit,
    onClickNextItemButton: () -> Unit,
    onClickPreviousItemButton: () -> Unit,
) {
    var isPlay by remember { mutableStateOf(true) }
    var focusPreviousButton by remember { mutableStateOf(false) }
    var focusNextButton by remember { mutableStateOf(false) }
    var focusPlayButton by remember { mutableStateOf(false) }


    Row(modifier = modifier) {
        if (currentListPosition > 0) {
            ButtonControls(
                isFocus = focusPreviousButton,
                modifier = Modifier.align(Alignment.CenterVertically),
                idActiveIcon = R.drawable.ic_previous_active,
                idInactiveIcon = R.drawable.ic_previous_inactive,
                changeFocusState = { focusPreviousButton = it },
                onClick = onClickPreviousItemButton
            )
        }
        //TODO( think about the transfer of play state)
        ButtonControls(
            isFocus = focusPlayButton,
            modifier = Modifier.align(Alignment.CenterVertically),
            idActiveIcon = if (!isPlay) R.drawable.ic_play_active else R.drawable.ic_pause_active,
            idInactiveIcon = if (!isPlay) R.drawable.ic_play_inactive else R.drawable.ic_pause_inactive,
            changeFocusState = { focusPlayButton = it },
            onClick = {
                isPlay = !isPlay
                onClickPlayButton()
            }
        )

        if (currentListPosition != countItems) {
            ButtonControls(
                isFocus = focusNextButton,
                modifier = Modifier.align(Alignment.CenterVertically),
                idActiveIcon = R.drawable.ic_next_active,
                idInactiveIcon = R.drawable.ic_next_inactive,
                changeFocusState = { focusNextButton = it },
                onClick = onClickNextItemButton
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewSlideShowPhotoControl() {
    SlideShowPhotoControl(
        currentListPosition = 1,
        countItems = 3,
        onClickPlayButton = { /*TODO*/ },
        onClickNextItemButton = { /*TODO*/ },
        onClickPreviousItemButton = { /*TODO*/ })
}