package band.effective.office.elevator.ui.bottomSheets.bookPeriodSheet

import androidx.compose.runtime.Composable
import band.effective.office.elevator.domain.models.BookingInfo
import band.effective.office.elevator.domain.models.BookingPeriod
import band.effective.office.elevator.domain.models.TypeEndPeriodBooking
import band.effective.office.elevator.ui.booking.components.modals.BookAccept
import band.effective.office.elevator.ui.booking.store.BookingStore
import band.effective.office.elevator.ui.bottomSheets.BottomSheet
import dev.icerock.moko.resources.StringResource
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.atTime

class BookAcceptSheetComponent(
    private val bookingId: String,
    private val seatName: String,
    private val dateOfStart: LocalDateTime,
    private val dateOfEnd: LocalDateTime,
    private val bookingPeriod: BookingPeriod,
    private val typeEndPeriodBooking: TypeEndPeriodBooking,
    private val repeatBooking: StringResource
) : BottomSheet {
    @Composable
    override fun SheetContent() {
        BookAccept(
            onClickCloseBookAccept = { },
            confirmBooking = { },
            bookingInfo = BookingInfo(
                id = bookingId,
                ownerId = "",
                workSpaceId = "",
                seatName = seatName,
                dateOfStart = dateOfStart,
                dateOfEnd = dateOfEnd
            ),
            bookingPeriod = bookingPeriod,
            typeEndPeriodBooking = typeEndPeriodBooking,
            repeatBooking = repeatBooking
        )
    }
}