package band.effective.office.tablet.ui.freeNegotiationsScreen.ui.freeNegotiationsScreen.uiComponents.roomCard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import band.effective.office.tablet.domain.model.RoomInfo
import band.effective.office.tablet.features.freeNegotiationsScreen.MainRes
import band.effective.office.tablet.ui.freeNegotiationsScreen.ui.freeNegotiationsScreen.roomUiState.RoomInfoUiState
import band.effective.office.tablet.ui.theme.LocalCustomColorsPalette
import band.effective.office.tablet.ui.theme.h8

@Composable
fun IconCharacteristicsRoom(
    modifier: Modifier,
    roomInfo: RoomInfoUiState
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        if (roomInfo.room.capacity > 0) {
            IconCharacteristic(
                icon = ImageVector.vectorResource(MainRes.image.capacity),
                value = roomInfo.room.capacity,
                description = "capacity"
            )
            Spacer(modifier = Modifier.width(10.dp))
        }
        if (roomInfo.room.socketCount > 0) {
            IconCharacteristic(
                icon = ImageVector.vectorResource(MainRes.image.port),
                value = roomInfo.room.socketCount,
                description = "ports"
            )
            Spacer(modifier = Modifier.width(10.dp))
        }
    }
}

@Composable
fun IconCharacteristic(icon: ImageVector, value: Int, description: String) {
    Icon(
        imageVector = icon,
        contentDescription = description,
        modifier = Modifier.size(30.dp),
        tint = LocalCustomColorsPalette.current.primaryTextAndIcon
    )
    Spacer(modifier = Modifier.width(5.dp))
    Text(
        text = value.toString(),
        style = MaterialTheme.typography.h8,
        color = LocalCustomColorsPalette.current.primaryTextAndIcon
    )
}