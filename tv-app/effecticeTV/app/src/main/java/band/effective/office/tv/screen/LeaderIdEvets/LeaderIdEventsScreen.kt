package band.effective.office.tv.screen.LeaderIdEvets

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import band.effective.office.tv.model.LeaderIdEventInfo

@Composable
fun LeaderIdEventsScreen(viewModel: LeaderIdEventsViewModel = hiltViewModel()){
    val state by viewModel.apiResponse.collectAsState()
    when(val castState = state){
        is LeaderIdEventsUiState.Loading -> Text("Loading")
        is LeaderIdEventsUiState.Load ->{
            LazyColumn{
                items(castState.EventsList.size){
                    when(val event = castState.EventsList[it]){
                        is LeaderIdEventInfo.FullInfo -> FullInfoEventCard(eventInfo = event)
                        is LeaderIdEventInfo.PartInfo -> PartInfoEventCard(eventInfo = event)
                        is LeaderIdEventInfo.NullInfo -> NullInfoCard(eventInfo = event)
                    }
                }
            }
        }
    }

}