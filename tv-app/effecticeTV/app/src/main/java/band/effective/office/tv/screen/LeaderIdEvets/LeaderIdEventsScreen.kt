package band.effective.office.tv.screen.LeaderIdEvets

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun LeaderIdEventsScreen(viewModel: LeaderIdEventsViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()
    if (state.isLoad&& state.eventsInfo.size > 10){
        Row{
            EventCard(eventInfo = state.eventsInfo[1],
                modifier = Modifier.padding(10.dp).fillMaxWidth(0.5f)
                    .fillMaxHeight())
            EventCard(eventInfo = state.eventsInfo[0],
                modifier = Modifier.padding(10.dp).fillMaxWidth()
                    .fillMaxHeight())
        }
    }
/*
    LazyVerticalGrid(
        columns = GridCells.Adaptive(200.dp)
    ){
        if (state.isError) {
            item {
                Text("error")
            }
        }
        if (!state.isLoad) item {
            Text("loading")
        }
        items(state.eventsInfo.size) {
            EventCard(state.eventsInfo[it])
        }
    }*/
}
