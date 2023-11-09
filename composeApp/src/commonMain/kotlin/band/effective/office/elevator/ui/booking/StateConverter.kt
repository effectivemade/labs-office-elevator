package band.effective.office.elevator.ui.booking

import band.effective.office.elevator.ui.booking.store.BookingStore
import band.effective.office.elevator.ui.bottomSheets.bookPeriodSheet.bookAccept.store.BookAcceptStore
import band.effective.office.elevator.ui.bottomSheets.bookPeriodSheet.bookPeriod.store.BookPeriodStore
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

    fun BookingStore.State.toBookPeriodState(): BookPeriodStore.State =
        BookPeriodStore.State(
            startDate = selectedStartDate,
            startTime = selectedStartTime,
            finishDate = selectedFinishDate,
            finishTime = selectedFinishTime,
            repeatBooking = repeatBooking,
            switchChecked = wholeDay,
            dateOfEndPeriod = dateOfEndPeriod,
            bookingPeriod = bookingPeriod,
            endPeriodBookingType = typeOfEnd
        )
}