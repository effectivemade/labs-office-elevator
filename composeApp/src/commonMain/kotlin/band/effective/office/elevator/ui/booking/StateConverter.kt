package band.effective.office.elevator.ui.booking

import band.effective.office.elevator.ui.booking.store.BookingStore
import band.effective.office.elevator.ui.bottomSheets.bookPeriodSheet.bookAccept.store.BookAcceptStore
import kotlinx.datetime.atTime

object StateConverter {
    fun BookingStore.State.toBookAcceptState(): BookAcceptStore.State = BookAcceptStore.State(
        bookingId = bookingInfo.id,
        seatName = "",
        dateOfStart = selectedStartDate.atTime(selectedStartTime),
        dateOfEnd = selectedFinishDate.atTime(selectedFinishTime),
        bookingPeriod = bookingPeriod,
        typeEndPeriodBooking = typeOfEnd,
        repeatBooking = repeatBooking,
        modalState = BookAcceptStore.ModalState.HIDDEN
    )
}