package band.effective.office.tv.screen.LeaderIdEvets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import band.effective.office.tv.utils.getColorFromHex

@Composable
fun LeaderIdEventsScreen(viewModel: LeaderIdEventsViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()
    if (state.isLoad) {
        Box(
            modifier = Modifier.background(getColorFromHex("#282828"))
        ) {
            EventCard(
                eventInfo = state.eventsInfo[0],
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 30.dp, top = 40.dp, bottom = 50.dp)
            )
        }

    }
}
