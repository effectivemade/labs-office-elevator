package band.effective.office.tv.screen.LeaderIdEvets

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.tv.material3.Text
import band.effective.office.tv.model.LeaderIdEventInfo
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun FullInfoEventCard(eventInfo: LeaderIdEventInfo){
    Text(eventInfo.name)
    Text(eventInfo.isOnline.toString())
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(eventInfo.photoUrl)
            .crossfade(true)
            .build(),
        contentDescription = eventInfo.name
    )
}