package band.effective.office.elevator.ui.bottomSheets.bookPeriodSheet

import androidx.compose.runtime.Composable
import band.effective.office.elevator.ui.booking.components.modals.BookingRepeat
import band.effective.office.elevator.ui.bottomSheets.BottomSheet
import kotlinx.datetime.LocalDate

class BookRepeatSheetComponent(private val selectedDayOfEnd: LocalDate) : BottomSheet {
    @Composable
    override fun SheetContent() {
        BookingRepeat(
            backButtonClicked = { },
            confirmBooking = { _, _ -> },
            onSelected = {},
            onClickOpenCalendar = { },
            selectedDayOfEnd = selectedDayOfEnd
        )
    }
}