package band.effective.office.tv.core.ui.screen_with_controls

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.input.key.*
import androidx.compose.ui.unit.dp
import band.effective.office.tv.core.ui.screen_with_controls.components.SlideShowPhotoControl

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ScreenWithControlsTemplate(
    currentListPosition: Int,
    countItems: Int,
    isPlay: Boolean,
    playButton: FocusRequester,
    contentFocus: FocusRequester,
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit,
    onClickPlayButton: () -> Unit,
    onClickNextItemButton: () -> Unit,
    onClickPreviousItemButton: () -> Unit
) {
    var controlsVisible by remember { mutableStateOf(false) }
    val (prevButton, nextButton) = remember { FocusRequester.createRefs() }

    Box(
        modifier = modifier
            .onKeyEvent {
                Log.d("BUTTON", "click button ${it.key.keyCode}")
                if ((listOf(Key.DirectionCenter, Key.Enter, Key.DirectionUp).contains(it.key)
                            || (it.key == Key.NavigatePrevious && controlsVisible))
                    && it.type == KeyEventType.KeyDown
                ) {
                    controlsVisible = !controlsVisible
                }
                return@onKeyEvent false
            }
            .fillMaxSize(),
    ) {
        content()
        AnimatedVisibility(
            visible = controlsVisible,
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
                backToPhoto = contentFocus,
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