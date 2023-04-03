package band.effective.office.tv.screen.leaderIdEvents

import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import band.effective.office.tv.core.ui.screen_with_controls.ScreenWithControlsTemplate
import band.effective.office.tv.screen.load.LoadScreen

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LeaderIdEventsScreen(viewModel: LeaderIdEventsViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()
    val (contentFocus, playButton) = remember {FocusRequester.createRefs()}
    when{
        state.isError -> Text(state.errorText)
        state.isLoad -> {
            ScreenWithControlsTemplate(
                currentListPosition = state.curentEvent,
                countItems = state.eventsInfo.size,
                isPlay = state.isPlay,
                playButton = playButton,
                contentFocus = contentFocus,
                content = {
                    EventCard(
                        eventInfo = state.eventsInfo[state.curentEvent],
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(start = 30.dp, top = 40.dp, bottom = 50.dp)
                            .focusRequester(contentFocus)
                            .focusProperties {
                                down = playButton
                            }
                            .focusable()
                    )
                },
                onClickPlayButton = { viewModel.sendEvent(LeaderIdScreenEvents.OnClickPlayButton) },
                onClickNextItemButton = { viewModel.sendEvent(LeaderIdScreenEvents.OnClickNextItem) },
                onClickPreviousItemButton = { viewModel.sendEvent(LeaderIdScreenEvents.OnClickPreviousItem) },
                modifier = Modifier.fillMaxSize()
            )
        }
        else -> LoadScreen("Events")
    }
}
