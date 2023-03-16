package band.effective.office.tv.view

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.tv.material3.Text
import band.effective.office.tv.model.LeaderIdEventInfo
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun ListView(viewModel: ListViewModel = hiltViewModel()){
    val response by viewModel.apiResponse.collectAsState()
        LazyColumn(){
            if (response.isNotEmpty())
            items(7){index ->
                val event = response[index]
                when (event){
                    is LeaderIdEventInfo.PartInfo -> {
                        Text(event.name)
                        Text(event.isOnline.toString())
                        Text(event.errorText)
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(event.photoUrl)
                                .crossfade(true)
                                .build(),
                            contentDescription = event.name
                        )
                    }
                    is LeaderIdEventInfo.FullInfo->{
                        Text(event.name)
                        Text(event.isOnline.toString())
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(event.photoUrl)
                                .crossfade(true)
                                .build(),
                            contentDescription = event.name
                        )
                    }
                    is LeaderIdEventInfo.NullInfo -> Text(event.errorText)
                }
            }
        }
}