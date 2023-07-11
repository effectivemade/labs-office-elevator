package band.effective.office.tablet.ui.mainScreen.roomInfoComponents

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import band.effective.office.tablet.features.roomInfo.MainRes
import band.effective.office.tablet.utils.getCorrectDeclension
import io.github.skeptick.libres.compose.painterResource
import io.github.skeptick.libres.images.Image

@Composable
fun CommonRoomInfoComponent(
    modifier: Modifier = Modifier,
    name: String,
    capacity: Int,
    isHaveTv: Boolean,
    electricSocketCount: Int,
    roomOccupancy: String,
    backgroundColor: Color
) {
    Surface(
        modifier = Modifier.background(color = backgroundColor).fillMaxWidth(),
        color = backgroundColor
    ) {
        Column(modifier = modifier) {
            Text(
                text = name,
                color = Color(0xFFFAFAFA),
                fontSize = 60.sp
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = roomOccupancy,
                color = Color(0xFFFAFAFA),
                fontSize = 27.sp
            )
            Spacer(modifier = Modifier.height(25.dp))
            Row(modifier = Modifier.padding(horizontal = 10.dp)) {
                val spaceBetweenProperty = 40.dp
                RoomPropertyComponent(image = MainRes.image.quantity, text = "$capacity")
                if (isHaveTv) {
                    Spacer(modifier = Modifier.width(spaceBetweenProperty))
                    RoomPropertyComponent(
                        image = MainRes.image.tv,
                        text = MainRes.string.tv_property
                    )
                }
                //NOTE(Maksim Mishenko): designers have not fully defined all the properties, a condition will appear in if
                //TODO(Maksim Mishenko): replace condition in if
                if (true) {
                    Spacer(modifier = Modifier.width(spaceBetweenProperty))
                    RoomPropertyComponent(
                        image = MainRes.image.usb,
                        text = MainRes.string.usb_property
                    )
                }
                if (electricSocketCount > 0) {
                    Spacer(modifier = Modifier.width(spaceBetweenProperty))
                    RoomPropertyComponent(
                        image = MainRes.image.power_socket,
                        text = "$electricSocketCount ${
                            getCorrectDeclension(
                                number = electricSocketCount,
                                nominativeCase = MainRes.string.electric_socket_property_nominative,
                                genitive = MainRes.string.electric_socket_property_genitive,
                                genitivePlural = MainRes.string.electric_socket_property_plural
                            )
                        }"
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
fun RoomPropertyComponent(image: Image, text: String) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier,
            painter = painterResource(image),
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = text, color = Color(0xFFFAFAFA), fontSize = 16.sp)
    }
}