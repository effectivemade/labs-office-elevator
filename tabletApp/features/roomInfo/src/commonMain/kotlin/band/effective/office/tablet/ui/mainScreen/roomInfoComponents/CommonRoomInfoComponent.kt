package band.effective.office.tablet.ui.mainScreen.roomInfoComponents

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import band.effective.office.tablet.features.roomInfo.MainRes
import band.effective.office.tablet.ui.theme.h8
import io.github.skeptick.libres.compose.painterResource
import io.github.skeptick.libres.images.Image

@Composable
fun CommonRoomInfoComponent(
    modifier: Modifier = Modifier,
    name: String,
    capacity: Int,
    isHaveTv: Boolean,
    electricSocketCount: Int,
    backgroundColor: Color,
    content: @Composable () -> Unit
) {
    Surface(
        modifier = Modifier.background(color = backgroundColor).fillMaxWidth(),
        color = backgroundColor
    ) {
        Column(modifier = modifier) {
            Text(
                text = name,
                style = MaterialTheme.typography.h1
            )
            Spacer(modifier = Modifier.height(20.dp))
            content()
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
                if (electricSocketCount > 0) {
                    Spacer(modifier = Modifier.width(spaceBetweenProperty))
                    RoomPropertyComponent(
                        image = MainRes.image.ethernet,
                        text = "$electricSocketCount"
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
        Text(text = text, style = MaterialTheme.typography.h8)
    }
}