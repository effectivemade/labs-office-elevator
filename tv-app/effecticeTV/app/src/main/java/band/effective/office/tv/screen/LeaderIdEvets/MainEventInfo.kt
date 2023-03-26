package band.effective.office.tv.screen.LeaderIdEvets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.tv.material3.Text
import band.effective.office.tv.R
import band.effective.office.tv.domain.model.LeaderIdEventInfo

@Composable
fun MainEventInfo(eventInfo: LeaderIdEventInfo) {
    Column(
        modifier = Modifier.fillMaxWidth(0.5f)
    ) {
        Text(
            text = eventInfo.name,
            color = Color.White,
            fontSize = 30.sp,
            fontFamily = robotoFontFamily()
        )
        TextWithCaptionAndIcon(
            resourceId = R.drawable.user,
            text = eventInfo.organizer ?: "Какая-то неопознанная колебяка с района",
            caption = "Организатор",
            fontSize = 12.sp,
            iconSize = 19.dp,
            modifier = Modifier.padding(vertical = 14.dp)
        )
        if (eventInfo.speakers != null && eventInfo.speakers.isNotEmpty()) {
            var speakers = ""
            var speakerCounter = 0
            for (speaker in eventInfo.speakers) {
                if (speakerCounter >= 3) break
                speakers += "$speaker "
                speakerCounter++
            }
            TextWithCaptionAndIcon(
                resourceId = R.drawable.mic,
                text = speakers,
                caption = "Спикеры",
                fontSize = 12.sp,
                iconSize = 19.dp
            )
        }
    }
}