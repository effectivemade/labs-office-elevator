package band.effective.office.tv.screen.LeaderIdEvets

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun LeaderIdEventsScreen(viewModel: LeaderIdEventsViewModel = hiltViewModel()){
    val state by viewModel.state.collectAsState()
    if (state.isLoad){
        LazyColumn {
            items(state.eventsInfo.size) {
                FullInfoEventCard(state.eventsInfo[it])
            }
        }
    }
    else Text("loading")

}