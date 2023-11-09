package band.effective.office.elevator.ui.bottomSheets.bookPeriodSheet

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import band.effective.office.elevator.ui.booking.BookingComponent
import band.effective.office.elevator.ui.booking.components.modals.BookingPeriod
import band.effective.office.elevator.ui.booking.store.BookingStore
import band.effective.office.elevator.ui.bottomSheets.BottomSheet
import dev.icerock.moko.resources.compose.stringResource
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

class BookPeriodSheetComponent(
    private val startDate: LocalDate,
    private val startTime: LocalTime,
    private val finishDate: LocalDate,
    private val finishTime: LocalTime,
    private val repeatBooking: String,
    private val switchChecked: Boolean,
    private val closeClick: () -> Unit
) : BottomSheet {
    @Composable
    override fun SheetContent() {
        BookingPeriod(
            startDate = startDate,
            startTime = startTime,
            finishTime = finishTime,
            repeatBooking = repeatBooking,
            switchChecked = switchChecked,
            closeClick = closeClick,
            onSelectAllDay = {},
            bookDates = {},
            bookStartTime = {},
            bookFinishTime = {},
            bookingRepeat = {},
            onClickSearchSuitableOptions = { },
            finishDate = finishDate,
        )
    }

    @Composable
    override fun content() {
        TODO("Not yet implemented")
    }

}