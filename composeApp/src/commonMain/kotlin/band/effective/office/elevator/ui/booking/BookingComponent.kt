package band.effective.office.elevator.ui.booking

import band.effective.office.elevator.MainRes
import band.effective.office.elevator.ui.booking.models.WorkSpaceType
import band.effective.office.elevator.ui.booking.store.BookingStore
import band.effective.office.elevator.ui.booking.store.BookingStoreFactory
import band.effective.office.elevator.ui.bottomSheets.BottomSheet
import band.effective.office.elevator.ui.bottomSheets.bookPeriodSheet.BookAcceptSheetComponent
import band.effective.office.elevator.ui.bottomSheets.bookPeriodSheet.BookPeriodSheetComponent
import band.effective.office.elevator.ui.bottomSheets.bookPeriodSheet.BookRepeatSheetComponent
import band.effective.office.elevator.ui.bottomSheets.bookPeriodSheet.ChooseZoneSheet.ChooseZoneSheetComponent
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.router.slot.dismiss
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.datetime.atTime

class BookingComponent(
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
    private val output: (BookingComponent.Output) -> Unit
) :
    ComponentContext by componentContext {

    private val navigation = SlotNavigation<Config>()
    val slot: Value<ChildSlot<Config, BottomSheet>> = childSlot(
        source = navigation,
        childFactory = { bottomSheet, componentContext ->
            when (bottomSheet) {
                Config.BookAccept -> BookAcceptSheetComponent(
                    bookingId = state.value.bookingInfo.id,
                    seatName = "",
                    dateOfStart = state.value.selectedStartDate.atTime(state.value.selectedStartTime),
                    dateOfEnd = state.value.selectedFinishDate.atTime(state.value.selectedFinishTime),
                    bookingPeriod = state.value.bookingPeriod,
                    typeEndPeriodBooking = state.value.typeOfEnd,
                    repeatBooking = state.value.repeatBooking
                )

                Config.BookPeriod -> BookPeriodSheetComponent(
                    startDate = state.value.selectedStartDate,
                    startTime = state.value.selectedStartTime,
                    finishDate = state.value.selectedFinishDate,
                    finishTime = state.value.selectedFinishTime,
                    repeatBooking = "",
                    switchChecked = state.value.wholeDay,
                    closeClick = { closeSheet() }
                )

                Config.ChooseZone -> ChooseZoneSheetComponent(
                    sheetTile = if (state.value.workSpacesType == WorkSpaceType.WORK_PLACE) MainRes.strings.selection_zones
                    else MainRes.strings.selection_rooms,
                    workSpacesZone = state.value.currentWorkspaceZones,
                    closeSheet = { closeSheet() },
                    confirm = { onEvent(BookingStore.Intent.ChangeSelectedWorkSpacesZone(it)) }
                )

                Config.BookRepeat -> BookRepeatSheetComponent(state.value.dateOfEndPeriod)
            }
        }
    )

    private val bookingStore = instanceKeeper.getStore {
        BookingStoreFactory(
            storeFactory = storeFactory
        ).create()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val state: StateFlow<BookingStore.State> = bookingStore.stateFlow

    val label: Flow<BookingStore.Label> = bookingStore.labels
    fun onEvent(event: BookingStore.Intent) {
        bookingStore.accept(event)
    }

    fun onOutput(output: BookingComponent.Output) {
        output(output)
    }

    sealed class Output {
        object OpenMainTab : Output()
    }

    fun openSheet(config: Config) {
        navigation.activate(config)
    }

    fun closeSheet() {
        navigation.dismiss()
    }

    sealed interface Config : Parcelable {
        @Parcelize
        object BookAccept : Config

        @Parcelize
        object ChooseZone : Config

        @Parcelize
        object BookPeriod : Config

        @Parcelize
        object BookRepeat : Config
//        object RepeatDialog : Dialog//
//        object Calendar : Dialog//
//        object Confirm : Dialog//
//        object TimePicker : Dialog
//        object CalendarForEndDate : Dialog

    }
}