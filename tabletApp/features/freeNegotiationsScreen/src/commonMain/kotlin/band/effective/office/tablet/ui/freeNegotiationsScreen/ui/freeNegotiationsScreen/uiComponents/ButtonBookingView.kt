package band.effective.office.tablet.ui.freeNegotiationsScreen.ui.freeNegotiationsScreen.uiComponents

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import band.effective.office.tablet.features.freeNegotiationsScreen.MainRes
import band.effective.office.tablet.ui.freeNegotiationsScreen.ui.freeNegotiationsScreen.roomUiState.RoomInfoUiState
import band.effective.office.tablet.ui.freeNegotiationsScreen.ui.freeNegotiationsScreen.roomUiState.RoomState
import band.effective.office.tablet.ui.freeNegotiationsScreen.ui.freeNegotiationsScreen.uiComponents.roomCard.getDuration
import band.effective.office.tablet.ui.theme.LocalCustomColorsPalette
import band.effective.office.tablet.ui.theme.h7

@Composable
fun ButtonBookingView(
    modifier: Modifier,
    roomInfo: RoomInfoUiState,
    onBookRoom: (name: RoomInfoUiState, maxDuration: Int) -> Unit
) {
    Button(
        modifier = modifier,
        onClick = {
            onBookRoom(roomInfo, roomInfo.changeEventTime)
        },
        elevation = ButtonDefaults.elevation(0.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.background,
            contentColor = LocalCustomColorsPalette.current.primaryTextAndIcon,
            disabledBackgroundColor = MaterialTheme.colors.background,
            disabledContentColor = LocalCustomColorsPalette.current.disabledPrimaryButton
        ),
        shape = RoundedCornerShape(100.dp),
        enabled = roomInfo.state != RoomState.BUSY
    ) {
        Text(
            text = if(roomInfo.state == RoomState.SOON_BUSY) MainRes.string.occupy_on.format(
                getDuration(roomInfo.changeEventTime)
            ) else MainRes.string.occupy,
            style = MaterialTheme.typography.h7
        )
    }
}