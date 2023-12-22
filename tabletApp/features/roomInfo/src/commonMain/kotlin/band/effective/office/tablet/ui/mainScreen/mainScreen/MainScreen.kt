package band.effective.office.tablet.ui.mainScreen.mainScreen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import band.effective.office.tablet.ui.common.ErrorMainScreen
import band.effective.office.tablet.ui.mainScreen.mainScreen.store.MainStore
import band.effective.office.tablet.ui.mainScreen.mainScreen.uiComponents.LoadMainScreen
import band.effective.office.tablet.ui.mainScreen.settingsComponents.SettingsScreen
import band.effective.office.tablet.ui.mainScreen.settingsComponents.SettingsView

@RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
@Composable
fun MainScreen(component: MainComponent) {
    val state by component.state.collectAsState()
    when {
        state.isError -> {
            ErrorMainScreen { component.sendIntent(MainStore.Intent.RebootRequest) }
        }

        state.isLoad -> {
            LoadMainScreen()
        }

        state.isData -> {
            MainScreenView(
                showBookingModal = state.showBookingModal,
                showFreeRoomModal = state.showFreeModal,
                showDateTimePickerModal = state.showDateTimePickerModal,
                bookingRoomComponent = component.bookingRoomComponent,
                selectRoomComponent = component.selectRoomComponent,
                roomInfoComponent = component.roomInfoComponent,
                freeSelectRoomComponent = component.freeSelectRoomComponent,
                dateTimePickerComponent = component.dateTimePickerComponent,
                showModal = state.showModal(),
                isDisconnect = state.isDisconnect,
                onEventUpdateRequest = {
                    component.sendIntent(
                        MainStore.Intent.OnChangeEventRequest(
                            eventInfo = it
                        )
                    )
                },
                updatedEvent = state.updatedEvent,
                showUpdateModal = state.showUpdateModal,
                updateEventComponent = component.updateEventComponent,
                closeModal = { component.sendIntent(MainStore.Intent.CloseModal) },
                onSettings = { component.onSettings() }
            )
        }

        state.isSettings -> {
            component.onSettings()
        }
    }
}
