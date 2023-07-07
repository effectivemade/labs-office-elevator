package tablet.ui.selectRoomScreen.uiComponents

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import tablet.domain.model.Booking

@Composable
fun BookingButtonView(modifier: Modifier, color: Color, shape: RoundedCornerShape, booking: Booking) {
    val isPressed = remember { mutableStateOf(false) }
    val colorButton =  if(isPressed.value) Color(0xFFED6521) else color

    Button(
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(colorButton),
        shape = shape,
        onClick = { isPressed.value = !isPressed.value}
    ) {
        Box(contentAlignment = Alignment.Center)
        {
            Text(
                text = "Занять с ${booking.eventInfo.startTime.time24()} до ${booking.eventInfo.finishTime.time24()}",
                fontSize = 20.sp,
                fontWeight = FontWeight(500),
                fontFamily = FontFamily.SansSerif,
                color = Color(0xFFFAFAFA)
            )
        }
    }
}