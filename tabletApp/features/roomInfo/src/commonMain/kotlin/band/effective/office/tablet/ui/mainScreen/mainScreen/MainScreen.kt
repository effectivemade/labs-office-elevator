package band.effective.office.tablet.ui.mainScreen.mainScreen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import band.effective.office.tablet.utils.oneDay
import java.util.Calendar
import java.util.GregorianCalendar

@RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
@Composable
fun MainScreen(component: MainComponent) {
    val state by component.state.collectAsState()
    when {
        state.isError -> {}
        state.isLoad -> {}
        state.isData -> {
            MainScreenView(
                showBookingModal = state.showBookingModal,
                showFreeRoomModal = state.showFreeModal,
                mockComponent = component.mockSettingsComponent,
                bookingRoomComponent = component.bookingRoomComponent,
                selectRoomComponent = component.selectRoomComponent,
                roomInfoComponent = component.roomInfoComponent,
                onCloseFreeModalRequest = { component.closeAllModal() },
                onFreeRoomRequest = { component.onFreeRoom() }
            )
        }
    }
}

private fun Calendar.isToday(): Boolean = oneDay(GregorianCalendar())
