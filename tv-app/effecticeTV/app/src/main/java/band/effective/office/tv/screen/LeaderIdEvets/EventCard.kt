package band.effective.office.tv.screen.LeaderIdEvets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.tv.material3.Text
import band.effective.office.tv.domain.model.LeaderIdEventInfo
import band.effective.office.tv.utils.rememberQrBitmapPainter
import coil.compose.AsyncImage
import coil.request.ImageRequest
import java.util.Calendar
import java.util.GregorianCalendar
import band.effective.office.tv.R

@Composable
fun EventCard(eventInfo: LeaderIdEventInfo, modifier: Modifier = Modifier){
    Column(modifier = modifier

        .border(1.dp, getColor("#434040"), RoundedCornerShape(3))
        .background(getColor("#434040"), RoundedCornerShape(3))
        .padding(10.dp,0.dp,10.dp,10.dp)){
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(eventInfo.photoUrl)
                .crossfade(true)
                .build(),
            contentDescription = eventInfo.name,
            Modifier.fillMaxHeight(0.5f)
                .fillMaxWidth().padding(bottom = 10.dp),
        //    contentScale = ContentScale.Crop
        )
        Row{
            Column(modifier = Modifier
                .fillMaxWidth(0.65f)){
                if (eventInfo.speakers!=null && eventInfo.speakers.isNotEmpty()){
                    Row{
                        for (speaker in eventInfo.speakers){
                            Text(
                                text = speaker,
                                color = Color.White,
                                fontSize = 10.sp,
                                fontFamily = robotoFontFamily()
                            )
                        }
                    }
                }
                Text(
                    text = eventInfo.name,
                    color = Color.White,
                    fontSize = 20.sp,
                    fontFamily = robotoFontFamily()
                )
                Text(
                    text = dateToString(eventInfo.dateTime),
                    color = Color.White,
                    fontSize = 10.sp,
                    fontFamily = robotoFontFamily()
                )
            }
            Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Bottom){
                Image(
                    painter = rememberQrBitmapPainter("https://leader-id.ru/events/${eventInfo.id}"),
                    contentDescription = eventInfo.name,
                )
            }
        }
    }
}
fun getColor(colorString: String): Color {
    return Color(android.graphics.Color.parseColor(colorString))
}

fun dateToString(dateTime: GregorianCalendar): String =
    "${dateTime.get(Calendar.YEAR)}.${dateTime.get(Calendar.MONTH)}.${dateTime.get(Calendar.DAY_OF_MONTH)} ${dateTime.get(Calendar.HOUR_OF_DAY)}:${dateTime.get(Calendar.MINUTE)}"

fun robotoFontFamily(): FontFamily =
    FontFamily(
        Font(R.font.roboto_black),
        Font(R.font.roboto_black, weight = FontWeight.Bold),
        Font(R.font.roboto_black, weight = FontWeight.Light),
        Font(R.font.roboto_black, weight = FontWeight.Thin),
        Font(R.font.roboto_black, weight = FontWeight.Normal, style = FontStyle.Italic)
    )