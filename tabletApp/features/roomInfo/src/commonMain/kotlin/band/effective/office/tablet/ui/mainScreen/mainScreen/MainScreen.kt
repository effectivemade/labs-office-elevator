package band.effective.office.tablet.ui.mainScreen.mainScreen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import band.effective.office.tablet.ui.common.ErrorMainScreen
import band.effective.office.tablet.ui.freeSelectRoom.FreeSelectRoomComponent
import band.effective.office.tablet.ui.freeSelectRoom.FreeSelectRoomView
import band.effective.office.tablet.ui.mainScreen.mainScreen.store.MainStore
import band.effective.office.tablet.ui.mainScreen.mainScreen.uiComponents.LoadMainScreen
import band.effective.office.tablet.ui.updateEvent.UpdateEventComponent
import band.effective.office.tablet.ui.updateEvent.UpdateEventView
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState

@RequiresApi(Build.VERSION_CODES.O)
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
                roomInfoComponent = component.roomInfoComponent,
                isDisconnect = state.isDisconnect,
                onEventUpdateRequest = {
                    component.sendIntent(
                        MainStore.Intent.OnChangeEventRequest(
                            eventInfo = it
                        )
                    )
                },
                onSettings = { component.onSettings() },
                slotComponent = component.slotComponent
            )
        }

        state.isSettings -> {
            component.onSettings()
        }
    }
    val activeWindowSlot by component.modalWindowSlot.subscribeAsState()
    Box(
        modifier = Modifier.fillMaxSize()
            .background(color = if (activeWindowSlot.child != null) Color.Black.copy(alpha = 0.9f) else Color.Transparent)
    ) {
        when(val activeComponent = activeWindowSlot.child?.instance){
            is FreeSelectRoomComponent -> FreeSelectRoomView(freeSelectRoomComponent = activeComponent)
            is UpdateEventComponent -> UpdateEventView(component = activeComponent)
        }
    }
}
