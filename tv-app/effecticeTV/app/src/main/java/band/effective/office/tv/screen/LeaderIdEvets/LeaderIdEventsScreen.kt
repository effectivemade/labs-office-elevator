package band.effective.office.tv.screen.LeaderIdEvets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.tv.foundation.lazy.list.TvLazyColumn

@Composable
fun LeaderIdEventsScreen(viewModel: LeaderIdEventsViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()
    LazyColumn{
        item {
            if (state.isError) {
                Text("error")
            }
        }
        items(state.eventsInfo.size) {
            FullInfoEventCard(state.eventsInfo[it])
        }
    }
}