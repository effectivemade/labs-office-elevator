package band.effective.office.tv.screen.LeaderIdEvets

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import androidx.tv.material3.Text
import band.effective.office.tv.model.LeaderIdEventInfo
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun FullInfoEventCard(eventInfo: LeaderIdEventInfo){
    Column(modifier = Modifier.fillMaxSize()){
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(eventInfo.photoUrl)
                .crossfade(true)
                .build(),
            contentDescription = eventInfo.name
        )
        Text(
            text = eventInfo.name,
            fontSize = 10.sp
        )
    }
}