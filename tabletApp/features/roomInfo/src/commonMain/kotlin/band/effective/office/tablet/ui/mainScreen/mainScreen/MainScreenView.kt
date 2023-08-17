package band.effective.office.tablet.ui.mainScreen.mainScreen

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import band.effective.office.tablet.domain.model.EventInfo
import band.effective.office.tablet.ui.bookingComponents.pickerDateTime.DateTimePickerComponent
import band.effective.office.tablet.ui.bookingComponents.pickerDateTime.DateTimePickerModalView
import band.effective.office.tablet.ui.freeSelectRoom.FreeSelectRoomComponent
import band.effective.office.tablet.ui.freeSelectRoom.FreeSelectRoomView
import band.effective.office.tablet.ui.mainScreen.bookingRoomComponents.BookingRoomComponent
import band.effective.office.tablet.ui.mainScreen.bookingRoomComponents.BookingRoomView
import band.effective.office.tablet.ui.mainScreen.mainScreen.uiComponents.Disconnect
import band.effective.office.tablet.ui.mainScreen.roomInfoComponents.RoomInfoComponent
import band.effective.office.tablet.ui.selectRoomScreen.SelectRoomComponent
import band.effective.office.tablet.ui.selectRoomScreen.SelectRoomScreen
import band.effective.office.tablet.ui.updateEvent.UpdateEventComponent
import band.effective.office.tablet.ui.updateEvent.UpdateEventView

@SuppressLint("NewApi", "StateFlowValueCalledInComposition")
@RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
@Composable
fun MainScreenView(
    showBookingModal: Boolean,
    showFreeRoomModal: Boolean,
    showDateTimePickerModal: Boolean,
    bookingRoomComponent: BookingRoomComponent,
    selectRoomComponent: SelectRoomComponent,
    freeSelectRoomComponent: FreeSelectRoomComponent,
    dateTimePickerComponent: DateTimePickerComponent,
    roomInfoComponent: RoomInfoComponent,
    updateEventComponent: UpdateEventComponent,
    showModal: Boolean,
    isDisconnect: Boolean,
    onEventUpdateRequest: (EventInfo) -> Unit,
    showUpdateModal: Boolean,
    updatedEvent: EventInfo,
    closeModal: () -> Unit,
    onSettings: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize().background(color = MaterialTheme.colors.background)) {
        /*NOTE(Maksim Mishenko):
        * infoViewWidth is part of the width occupied by roomInfoView
        * infoViewWidth = infoViewFrame.width / mainScreenFrame.width
        * where infoViewFrame, mainScreenFrame is frames from figma and all width I get from figma*/
        val infoViewWidth = 627f / 1133f
        Row(modifier = Modifier.fillMaxSize()) {
            RoomInfoComponent(
                modifier = Modifier.fillMaxHeight().fillMaxWidth(infoViewWidth),
                roomInfoComponent = roomInfoComponent,
                onEventUpdateRequest = onEventUpdateRequest,
                onSettings = onSettings
            )
            Box(modifier = Modifier.fillMaxSize()) {
                BookingRoomView(
                    modifier = Modifier
                        .background(color = MaterialTheme.colors.surface)
                        .fillMaxSize()
                        .padding(25.dp),
                    bookingRoomComponent = bookingRoomComponent,
                    dateTimePickerComponent = dateTimePickerComponent,
                )
                Box() {
                    Disconnect(visible = isDisconnect)
                }
            }
        }
        Box(
            modifier = Modifier.fillMaxSize()
                .background(color = if (showModal) Color.Black.copy(alpha = 0.9f) else Color.Transparent)
        ) {
            when {
                showBookingModal -> SelectRoomScreen(component = selectRoomComponent)
                showFreeRoomModal -> FreeSelectRoomView(freeSelectRoomComponent = freeSelectRoomComponent)
                showDateTimePickerModal -> DateTimePickerModalView(
                    dateTimePickerComponent = dateTimePickerComponent,
                    currentDate = bookingRoomComponent.state.value.selectDate
                )

                showUpdateModal -> UpdateEventView(
                    component = updateEventComponent,
                    event = updatedEvent,
                    onCloseRequest = closeModal)
            }
        }
    }
}
