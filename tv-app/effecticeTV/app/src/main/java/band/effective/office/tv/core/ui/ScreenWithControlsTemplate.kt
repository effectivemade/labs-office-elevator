package band.effective.office.tv.core.ui

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
    isPlay: Boolean,
    isVisible: Boolean,
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
        Log.d("visible", "$isVisible")
        AnimatedVisibility(
            visible = isVisible,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 30.dp)
        ) {
            SlideShowPhotoControl(
                currentListPosition = currentListPosition,
                countItems = countItems,
                isPlay = isPlay,
                prevButton = prevButton,
                nextButton = nextButton,
                playButton = playButton,
                backToPhoto = backToPhoto,
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
}