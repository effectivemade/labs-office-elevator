package band.effective.office.tv.screen.LeaderIdEvets

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import band.effective.office.tv.domain.model.LeaderIdEventInfo
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun EventPhoto(eventInfo: LeaderIdEventInfo) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(eventInfo.photoUrl)
            .crossfade(true)
            .build(),
        contentDescription = eventInfo.name,
        Modifier
            .fillMaxHeight()
            .padding(bottom = 10.dp),
    )
}