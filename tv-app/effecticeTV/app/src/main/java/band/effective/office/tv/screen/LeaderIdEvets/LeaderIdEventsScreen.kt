package band.effective.office.tv.screen.LeaderIdEvets

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun LeaderIdEventsScreen(viewModel: LeaderIdEventsViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()
    LazyVerticalGrid(
        columns = GridCells.Adaptive(200.dp)
    ){
        if (state.isError) {
            item {
                Text("error")
            }
        }
        items(state.eventsInfo.size) {
            FullInfoEventCard(state.eventsInfo[it])
        }
    }
}
