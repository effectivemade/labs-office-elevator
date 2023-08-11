package band.effective.office.tablet.ui.freeNegotiationsScreen.ui.freeNegotiationsScreen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import band.effective.office.tablet.domain.model.Booking
import band.effective.office.tablet.ui.freeNegotiationsScreen.ui.freeNegotiationsScreen.roomUiState.RoomInfoUiState
import band.effective.office.tablet.ui.freeNegotiationsScreen.ui.freeNegotiationsScreen.store.FreeNegotiationsStore
import band.effective.office.tablet.ui.freeNegotiationsScreen.ui.freeNegotiationsScreen.uiComponents.LoaderView


@RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
@Composable
fun FreeNegotiationsScreen(component: FreeNegotiationsComponent) {
    val state by component.state.collectAsState()

    component.onIntent(FreeNegotiationsStore.Intent.SetBooking(Booking.default))

    when {
        state.isData -> {
            FreeNegotiationsView(
                listRooms = state.listRooms,
                nameRoomCurrent = state.nameCurrentRoom,
                showBookingModal = state.showBookingModal,
                selectRoomComponent = component.selectRoomComponent,
                onMainScreen = { reset: Boolean ->
                    component.onIntent(FreeNegotiationsStore.Intent.OnMainScreen(reset))
                },
                onBookRoom = { name: RoomInfoUiState, maxDuration: Int ->
                    component.onIntent(
                        FreeNegotiationsStore.Intent.OnBookingRoom(
                            name,
                            maxDuration
                        )
                    )
                }
            )
        }

        state.isLoad -> {
            LoaderView(
                nameRoomCurrent = state.nameCurrentRoom,
                onMainScreen = { reset: Boolean ->
                    component.onIntent(FreeNegotiationsStore.Intent.OnMainScreen(reset))
                },
            )
        }

        state.error != null -> {
            FreeNegotiationsErrorView(
                nameRoomCurrent = state.nameCurrentRoom,
                onMainScreen = { reset: Boolean ->
                    component.onIntent(FreeNegotiationsStore.Intent.OnMainScreen(reset))
                })
        }
    }
}