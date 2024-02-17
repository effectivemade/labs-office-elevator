package band.effective.office.tablet.ui.mainScreen.mainScreen

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import band.effective.office.tablet.domain.model.EventInfo
import band.effective.office.tablet.domain.model.RoomInfo
import band.effective.office.tablet.features.roomInfo.MainRes
import band.effective.office.tablet.ui.mainScreen.mainScreen.uiComponents.Disconnect
import band.effective.office.tablet.ui.mainScreen.roomInfoComponents.RoomInfoComponent
import band.effective.office.tablet.ui.mainScreen.roomInfoComponents.uiComponent.RoomPropertyComponent
import band.effective.office.tablet.ui.mainScreen.slotComponent.SlotComponent
import band.effective.office.tablet.ui.mainScreen.slotComponent.SlotList
import band.effective.office.tablet.ui.theme.LocalCustomColorsPalette
import band.effective.office.tablet.ui.theme.roomInfoColor
import java.util.Calendar

@SuppressLint("NewApi", "StateFlowValueCalledInComposition")
@RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
@Composable
fun MainScreenView(
    slotComponent: SlotComponent,
    isDisconnect: Boolean,
    roomList: List<RoomInfo>,
    indexSelectRoom: Int,
    timeToNextEvent: Int,
    onRoomButtonClick: (Int) -> Unit,
    onEventUpdateRequest: (EventInfo) -> Unit,
    onSettings: () -> Unit,
    onCancelEventRequest: () -> Unit,
    onFastBooking: (Int) -> Unit,
    onUpdate: () -> Unit,
    onOpenDateTimePickerModalRequest: () -> Unit,
    onIncrementData: () -> Unit,
    onDecrementData: () -> Unit,
    selectDate: Calendar,
    onResetDate: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize().background(color = MaterialTheme.colors.background)) {
        /*NOTE(Maksim Mishenko):
        * infoViewWidth is part of the width occupied by roomInfoView
        * infoViewWidth = infoViewFrame.width / mainScreenFrame.width
        * where infoViewFrame, mainScreenFrame is frames from figma and all width I get from figma*/
        val infoViewWidth = 627f / 1133f
        Row(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.fillMaxHeight().fillMaxWidth(infoViewWidth)) {
                RoomInfoComponent(
                    modifier = Modifier,
                    room = roomList[indexSelectRoom],
                    onOpenFreeRoomModalRequest = { onCancelEventRequest() },
                    timeToNextEvent = timeToNextEvent,
                    isError = false, //TODO()
                    onSettings = onSettings,
                    onOpenDateTimePickerModalRequest = onOpenDateTimePickerModalRequest,
                    onIncrementDate = onIncrementData,
                    onDecrementDate = onDecrementData,
                    selectDate = selectDate,
                    onResetDate = onResetDate
                )
                SlotList(slotComponent)
                Button(onUpdate) {}
            }
            Box(modifier = Modifier.fillMaxSize()) {
                Row {
                    Spacer(Modifier.fillMaxWidth(0.15f))
                    Column(
                        modifier = Modifier.fillMaxHeight().padding(
                            start = 0.dp,
                            top = 40.dp,
                            end = 40.dp,
                            bottom = 40.dp
                        ),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        FastBookingButtons(onBooking = onFastBooking)
                        RoomList(
                            list = roomList,
                            indexSelectRoom = indexSelectRoom,
                            onClick = onRoomButtonClick
                        )
                    }
                }
                Box() {
                    Disconnect(visible = isDisconnect)
                }
            }
        }
    }
}

@Composable
fun FastBookingButtons(onBooking: (Int) -> Unit) {
    Column {
        Text(
            text = "Занять любую переговорку на:", //TODO
            color = MaterialTheme.colors.onPrimary,
            style = MaterialTheme.typography.h5
        )
        Spacer(Modifier.height(10.dp))
        Row {
            val buttonModifier = Modifier.fillMaxWidth().weight(1f)
            Button(
                modifier = buttonModifier,
                onClick = { onBooking(15) },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.White) //TODO
            ) {
                Text(
                    text = "15 мин", //TODO
                    color = Color.Black,
                )
            }
            Spacer(Modifier.width(10.dp))
            Button(
                modifier = buttonModifier,
                onClick = { onBooking(30) },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)
            ) {
                Text(
                    text = "30 мин", //TODO
                    color = Color.Black
                )
            }
            Spacer(Modifier.width(10.dp))
            Button(
                modifier = buttonModifier,
                onClick = { onBooking(60) },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)
            ) {
                Text(
                    text = "60 мин", //TODO
                    color = Color.Black
                )
            }
        }
    }
}

@Composable
fun RoomList(list: List<RoomInfo>, indexSelectRoom: Int, onClick: (Int) -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        list.forEachIndexed() { index, roomInfo ->
            RoomButton(
                modifier = Modifier
                    .background(
                        color = if (index == indexSelectRoom) MaterialTheme.colors.surface else Color.Transparent,
                        shape = CircleShape
                    )
                    .clickable { onClick(index) }
                    .fillMaxWidth()
                    .clip(CircleShape)
                    .padding(10.dp),
                room = roomInfo
            )
            Spacer(Modifier.height(5.dp))
        }

    }
}

@Composable
fun RoomButton(modifier: Modifier, room: RoomInfo) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .height(10.dp)
                    .width(10.dp)
                    .background(
                        color = if (room.currentEvent == null) LocalCustomColorsPalette.current.freeStatus else LocalCustomColorsPalette.current.busyStatus,
                        shape = CircleShape
                    )
            )
            Spacer(Modifier.width(10.dp))
            Text(
                text = room.name,
                color = MaterialTheme.colors.onPrimary,
                style = MaterialTheme.typography.h5
            )
        }
        RoomPropertyComponent(
            image = MainRes.image.quantity,
            text = "${room.capacity}",
            color = roomInfoColor
        )
    }
}