package band.effective.office.tablet.ui.freeNegotiationsScreen.ui.freeNegotiationsScreen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import band.effective.office.tablet.domain.model.Booking
import band.effective.office.tablet.domain.model.EventInfo
import band.effective.office.tablet.ui.freeNegotiationsScreen.ui.freeNegotiationsScreen.store.FreeNegotiationsStore


@RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
@Composable
fun FreeNegotiationsScreen(component: FreeNegotiationsComponent) {
    val state by component.state.collectAsState()

    component.onIntent(FreeNegotiationsStore.Intent.SetBooking(Booking.default))

    when{
        state.isData -> {
            FreeNegotiationsView(
                listRooms = state.listRooms,
                nameRoomCurrent = state.nameCurrentRoom,
                showBookingModal = state.showBookingModal,
                selectRoomComponent = component.selectRoomComponent,
                onMainScreen = { reset: Boolean ->
                    component.onIntent(FreeNegotiationsStore.Intent.OnMainScreen(reset))},
                onBookRoom =  {
                        name: String, maxDuration: Int ->
                    component.onIntent(FreeNegotiationsStore.Intent.OnBookingRoom(name, maxDuration))
                }
            )
        }
        state.isLoad -> {}
        state.error != null -> {}
    }
}