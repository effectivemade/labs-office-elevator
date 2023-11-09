package band.effective.office.elevator.ui.bottomSheets.bookPeriodSheet.bookAccept

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import band.effective.office.elevator.domain.models.BookingInfo
import band.effective.office.elevator.domain.models.BookingPeriod
import band.effective.office.elevator.domain.models.TypeEndPeriodBooking
import band.effective.office.elevator.ui.booking.components.modals.BookAccept
import band.effective.office.elevator.ui.booking.components.modals.BookingResult
import band.effective.office.elevator.ui.booking.store.BookingStore
import band.effective.office.elevator.ui.bottomSheets.BottomSheet
import band.effective.office.elevator.ui.bottomSheets.bookPeriodSheet.bookAccept.store.BookAcceptStore
import band.effective.office.elevator.ui.bottomSheets.bookPeriodSheet.bookAccept.store.BookAcceptStoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import dev.icerock.moko.resources.StringResource
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.atTime

class BookAcceptSheetComponent(
    initState: BookAcceptStore.State,
    close: () -> Unit,
    onMainScreen: () -> Unit
) : BottomSheet {
    val store = BookAcceptStoreFactory(
        storeFactory = DefaultStoreFactory(),
        initState = initState,
        close = close,
        onMainScreen = onMainScreen
    ).create()

    @Composable
    override fun SheetContent() {
        val state by store.stateFlow.collectAsState()
        BookAccept(
            onClickCloseBookAccept = { store.accept(BookAcceptStore.Intent.OnClose) },
            confirmBooking = { store.accept(BookAcceptStore.Intent.OnAccept) },
            bookingInfo = BookingInfo(
                id = state.bookingId,
                ownerId = "",
                workSpaceId = "",
                seatName = state.seatName,
                dateOfStart = state.dateOfStart,
                dateOfEnd = state.dateOfEnd
            ),
            bookingPeriod = state.bookingPeriod,
            typeEndPeriodBooking = state.typeEndPeriodBooking,
            repeatBooking = state.repeatBooking
        )
    }

    @Composable
    override fun content() {
        val state by store.stateFlow.collectAsState()
        if (state.modalState != BookAcceptStore.ModalState.HIDDEN) {
            Box() {
                Dialog(
                    content = {
                        BookingResult(
                            onMain = { store.accept(BookAcceptStore.Intent.SwitchOnMain) },
                            close = { store.accept(BookAcceptStore.Intent.CloseModal(state.modalState == BookAcceptStore.ModalState.SUCCESS)) },
                            modifier = Modifier.padding(horizontal = 16.dp).align(Alignment.Center),
                            isLoading = state.modalState == BookAcceptStore.ModalState.LOADING,
                            isError = state.modalState == BookAcceptStore.ModalState.ERROR
                        )
                    },
                    properties = DialogProperties(usePlatformDefaultWidth = false),
                    onDismissRequest = { store.accept(BookAcceptStore.Intent.CloseModal(false)) }
                )
            }
        }
    }
}