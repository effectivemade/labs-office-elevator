package band.effective.office.tv.screen.eventStory

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import band.effective.office.tv.R
import band.effective.office.tv.core.ui.screen_with_controls.ScreenWithControlsTemplate
import band.effective.office.tv.core.ui.screen_with_controls.model.MenuButton
import band.effective.office.tv.core.ui.screen_with_controls.model.MenuState
import band.effective.office.tv.screen.components.EventStoryScreenContent
import band.effective.office.tv.screen.components.NoStoriesScreen
import band.effective.office.tv.screen.load.LoadScreen
import kotlinx.coroutines.flow.update

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun EventStoryScreen(viewModel: EventStoryViewModel = hiltViewModel()) {

    val context = LocalContext.current
    val errorMessage = stringResource(id = R.string.error_occurred)

    val state by viewModel.state.collectAsState()
    val (contentFocus, playButton) = remember { FocusRequester.createRefs() }
    when {
        state.isLoading -> LoadScreen("Stories")
        state.isError -> showErrorMessage(context, errorMessage + state.errorText)
        state.isData -> if (state.eventsInfo.isEmpty()) NoStoriesScreen() else ScreenWithControlsTemplate(
            modifier = Modifier
                .fillMaxSize()
                .onFocusChanged { focusState ->
                    if (focusState.isFocused) MenuState.state.update { it.copy(selectButton = MenuButton.Nothink) }
                },
            currentListPosition = state.currentStoryIndex,
            countItems = state.eventsInfo.size,
            isPlay = state.isPlay,
            playButton = playButton,
            contentFocus = contentFocus,
            onClickPlayButton = { viewModel.sendEvent(EventStoryScreenEvents.OnClickPlayButton) },
            onClickNextItemButton = { viewModel.sendEvent(EventStoryScreenEvents.OnClickNextItem) },
            onClickPreviousItemButton = { viewModel.sendEvent(EventStoryScreenEvents.OnClickPreviousItem) }) {
            EventStoryScreenContent(state.eventsInfo,
                state.currentStoryIndex,
                Modifier
                    .fillMaxSize()
                    .focusRequester(contentFocus)
                    .focusProperties {
                        down = playButton
                    }
                    .focusable())

        }
    }

}

//TODO (ParkhomenkoEgor): Change toast to custom component
private fun showErrorMessage(context: Context, message: String) {
    Log.d("EventStoryScreen", message)
    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
}