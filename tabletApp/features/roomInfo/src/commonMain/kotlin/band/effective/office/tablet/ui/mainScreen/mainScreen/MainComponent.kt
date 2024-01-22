package band.effective.office.tablet.ui.mainScreen.mainScreen

import android.os.Build
import androidx.annotation.RequiresApi
import band.effective.office.tablet.domain.model.Booking
import band.effective.office.tablet.domain.model.EventInfo
import band.effective.office.tablet.domain.model.RoomInfo
import band.effective.office.tablet.ui.freeSelectRoom.FreeSelectRoomComponent
import band.effective.office.tablet.ui.mainScreen.mainScreen.store.MainFactory
import band.effective.office.tablet.ui.mainScreen.mainScreen.store.MainStore
import band.effective.office.tablet.ui.mainScreen.roomInfoComponents.RoomInfoComponent
import band.effective.office.tablet.ui.mainScreen.roomInfoComponents.store.RoomInfoStore
import band.effective.office.tablet.ui.mainScreen.slotComponent.SlotComponent
import band.effective.office.tablet.ui.mainScreen.slotComponent.store.SlotStore
import band.effective.office.tablet.ui.modal.ModalWindow
import band.effective.office.tablet.ui.updateEvent.UpdateEventComponent
import band.effective.office.tablet.utils.componentCoroutineScope
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.childContext
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.router.slot.dismiss
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.N)
class MainComponent(
    private val componentContext: ComponentContext,
    private val storeFactory: StoreFactory,
    private val OnSelectOtherRoomRequest: (() -> Booking) -> Unit,
    val onSettings: () -> Unit
) : ComponentContext by componentContext {

    val roomInfoComponent: RoomInfoComponent = RoomInfoComponent(
        componentContext = childContext(key = "roomInfoComponent"),
        storeFactory = storeFactory,
        onFreeRoomIntent = { mainStore.accept(MainStore.Intent.OnOpenFreeRoomModal) },
        room = { state.value.run { if (roomList.isNotEmpty()) roomList[indexSelectRoom] else RoomInfo.defaultValue } }
    )

    val slotComponent = SlotComponent(
        componentContext = componentContext,
        storeFactory = storeFactory,
        roomName = {
            state.value.run {
                with(if (roomList.isNotEmpty()) roomList[indexSelectRoom] else RoomInfo.defaultValue) { name }
            }
        },
        openBookingDialog = { event, room ->
            mainStore.accept(
                intent = MainStore.Intent.OnChangeEventRequest(
                    eventInfo = event
                )
            )
        }
    )

    private val navigation = SlotNavigation<ModalWindowsConfig>()
    val modalWindowSlot = childSlot(
        source = navigation,
        childFactory = ::childFactory
    )

    fun openModalWindow(dist: ModalWindowsConfig) {
        navigation.activate(dist)
    }

    fun closeModalWindow() {
        navigation.dismiss()
    }

    private fun childFactory(
        modalWindows: ModalWindowsConfig,
        componentContext: ComponentContext
    ): ModalWindow {
        return when (modalWindows) {
            is ModalWindowsConfig.FreeRoom -> FreeSelectRoomComponent(
                componentContext = componentContext,
                storeFactory = storeFactory,
                eventInfo = modalWindows.event,
                onCloseRequest = { closeModalWindow() })

            is ModalWindowsConfig.UpdateEvent -> UpdateEventComponent(
                componentContext = componentContext,
                storeFactory = storeFactory,
                event = modalWindows.event,
                room = modalWindows.room,
                onCloseRequest = { closeModalWindow() }
            )
        }
    }


    private val mainStore = instanceKeeper.getStore {
        MainFactory(
            storeFactory = storeFactory,
            navigate = ::openModalWindow,
            updateRoomInfo = { updateComponents(it) }
        ).create()
    }

    private fun updateComponents(roomInfo: RoomInfo) {
        roomInfoComponent.sendIntent(RoomInfoStore.Intent.OnUpdateRoomInfo(roomInfo))
        slotComponent.sendIntent(
            SlotStore.Intent.UpdateRequest(
                room = roomInfo.name,
                refresh = false
            )
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val state = mainStore.stateFlow

    fun sendIntent(intent: MainStore.Intent) {
        mainStore.accept(intent)
    }

    init {
        componentContext.componentCoroutineScope().launch {
            roomInfoComponent.state.collect {
                mainStore.accept(
                    MainStore.Intent.OnDisconnectChange(
                        it.isError
                    )
                )
            }
        }
    }

    sealed interface ModalWindowsConfig : Parcelable {
        @Parcelize
        data class UpdateEvent(
            val event: EventInfo,
            val room: String
        ) : ModalWindowsConfig

        @Parcelize
        data class FreeRoom(val event: EventInfo) : ModalWindowsConfig
    }
}