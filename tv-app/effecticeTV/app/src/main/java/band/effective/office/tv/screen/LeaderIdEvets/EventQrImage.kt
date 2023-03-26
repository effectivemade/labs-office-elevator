package band.effective.office.tv.screen.LeaderIdEvets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.tv.material3.Text
import band.effective.office.tv.domain.model.LeaderIdEventInfo
import band.effective.office.tv.utils.getColorFromHex
import band.effective.office.tv.utils.rememberQrBitmapPainter

@Composable
fun EventQrImage(eventInfo: LeaderIdEventInfo) {
    Row() {
        Image(
            painter = rememberQrBitmapPainter("https://leader-id.ru/events/${eventInfo.id}"),
            contentDescription = eventInfo.name,
        )
        Text(
            text = "Регистрация\n" +
                    "на мероприятие",
            color = getColorFromHex("#9F9F9F"),
            fontSize = 14.sp,
            fontFamily = robotoFontFamily(),
            modifier = Modifier.padding(start = 30.dp)
        )
    }

}