package band.effective.office.tablet.ui.freeNegotiationsScreen.ui.freeNegotiationsScreen.uiComponents

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import band.effective.office.tablet.ui.freeNegotiationsScreen.ui.freeNegotiationsScreen.roomUiState.RoomInfoUiState
import band.effective.office.tablet.ui.freeNegotiationsScreen.ui.freeNegotiationsScreen.roomUiState.RoomState
import band.effective.office.tablet.ui.freeNegotiationsScreen.ui.freeNegotiationsScreen.uiComponents.roomCard.RoomCard
import band.effective.office.tablet.ui.theme.LocalCustomColorsPalette

@RequiresApi(Build.VERSION_CODES.GINGERBREAD)
@Composable
fun RoomsView(
    modifier: Modifier,
    listRooms: List<RoomInfoUiState>,
    onBookRoom: (name: String, maxDuration: Int) -> Unit
    ) {
    Box(
        modifier = modifier
    )
    {
        Row(
            modifier = Modifier
                .fillMaxHeight(0.7f)
                .fillMaxWidth()
                .padding(vertical = 25.dp, horizontal = 15.dp)
        ) {
            listRooms.forEach {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 10.dp)
                        .fillMaxHeight()
                ) {
                    RoomCard(
                        roomInfo = it,
                        modifier = Modifier
                            .fillMaxHeight(0.8f)
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(20))
                            .background(LocalCustomColorsPalette.current.mountainBackground)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    ButtonBookingView(
                        roomInfo = it,
                        onBookRoom = onBookRoom,
                        modifier = Modifier
                            .fillMaxSize()
                            .border(
                                2.dp,
                                if (it.state == RoomState.FREE) {
                                    MaterialTheme.colors.primary
                                } else {
                                    LocalCustomColorsPalette.current.disabledPrimaryButton
                                },
                                RoundedCornerShape(100.dp)
                            )
                    )
                }
            }
        }
    }
}