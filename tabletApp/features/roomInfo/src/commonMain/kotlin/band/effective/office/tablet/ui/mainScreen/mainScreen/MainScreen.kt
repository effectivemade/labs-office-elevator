package band.effective.office.tablet.ui.mainScreen.mainScreen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import band.effective.office.tablet.ui.loader.Loader
import band.effective.office.tablet.utils.oneDay
import java.util.Calendar
import java.util.GregorianCalendar

@RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
@Composable
fun MainScreen(component: MainComponent) {
    val state by component.state.collectAsState()
    when {
        state.isError -> {}
        state.isLoad -> {
            LoadMainScreen()
        }
        state.isData -> {
            MainScreenView(
                showBookingModal = state.showBookingModal,
                showFreeRoomModal = state.showFreeModal,
                showTimePickerModal = state.showTimePickerModal,
                mockComponent = component.mockSettingsComponent,
                bookingRoomComponent = component.bookingRoomComponent,
                selectRoomComponent = component.selectRoomComponent,
                roomInfoComponent = component.roomInfoComponent,
                freeSelectRoomComponent = component.freeSelectRoomComponent,
                showModal = state.showModal(),
                isDisconnect = state.isDisconnect
            )
        }
    }
}

@Composable
fun LoadMainScreen() {
    Box(
        modifier = Modifier.background(color = MaterialTheme.colors.background).fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Loader()
    }
}

private fun Calendar.isToday(): Boolean = oneDay(GregorianCalendar())
