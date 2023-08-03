package band.effective.office.elevator.data

import band.effective.office.elevator.domain.BookingRepository
import band.effective.office.elevator.domain.models.BookingIfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime

class MockBookingRepositoryImpl: BookingRepository {
    private val scope = CoroutineScope(Dispatchers.IO)
    private val initLis = listOf(
        BookingIfo(
            id = 2455L,
            ownerId = 1L,
            seatName = "Seat A1",
            dateOfStart = LocalDateTime(
                date = LocalDate(year = 2023, monthNumber = 7, dayOfMonth = 13),
                time =  LocalTime(hour = 14, minute = 0, second = 0, nanosecond = 0)
            ),
            dateOfEnd = LocalDateTime(
                date = LocalDate(year = 2023, monthNumber = 7, dayOfMonth = 13),
                time =  LocalTime(hour = 14, minute = 30, second = 0, nanosecond = 0)
            )
        ),
        BookingIfo(
            id = 303040L,
            ownerId = 1L,
            seatName = "Seat A2",
            dateOfStart = LocalDateTime(
                date = LocalDate(year = 2023, monthNumber = 7, dayOfMonth = 13),
                time =  LocalTime(hour = 14, minute = 0, second = 0, nanosecond = 0)
            ),
            dateOfEnd = LocalDateTime(
                date = LocalDate(year = 2023, monthNumber = 7, dayOfMonth = 13),
                time =  LocalTime(hour = 15, minute = 30, second = 0, nanosecond = 0)
            )
        ),
        BookingIfo(
            id = 8989L,
            ownerId = 1L,
            seatName = "Seat A2",
            dateOfStart = LocalDateTime(
                date = LocalDate(year = 2023, monthNumber = 7, dayOfMonth = 14),
                time =  LocalTime(hour = 14, minute = 0, second = 0, nanosecond = 0)
            ),
            dateOfEnd = LocalDateTime(
                date = LocalDate(year = 2023, monthNumber = 7, dayOfMonth = 14),
                time =  LocalTime(hour = 15, minute = 30, second = 0, nanosecond = 0)
            )
        ),
        BookingIfo(
            id = 234L,
            ownerId = 1L,
            seatName = "Seat A2",
            dateOfStart = LocalDateTime(
                date = LocalDate(year = 2023, monthNumber = 7, dayOfMonth = 15),
                time =  LocalTime(hour = 14, minute = 0, second = 0, nanosecond = 0)
            ),
            dateOfEnd = LocalDateTime(
                date = LocalDate(year = 2023, monthNumber = 7, dayOfMonth = 15),
                time =  LocalTime(hour = 15, minute = 30, second = 0, nanosecond = 0)
            )
        ),
        BookingIfo(
            id = 754L,
            ownerId = 1L,
            seatName = "Seat A2",
            dateOfStart = LocalDateTime(
                date = LocalDate(year = 2023, monthNumber = 8, dayOfMonth = 3),
                time =  LocalTime(hour = 14, minute = 0, second = 0, nanosecond = 0)
            ),
            dateOfEnd = LocalDateTime(
                date = LocalDate(year = 2023, monthNumber = 8, dayOfMonth = 3),
                time =  LocalTime(hour = 15, minute = 30, second = 0, nanosecond = 0)
            )
        ),
        BookingIfo(
            id = 2222L,
            ownerId = 1L,
            seatName = "Seat A2",
            dateOfStart = LocalDateTime(
                date = LocalDate(year = 2023, monthNumber = 8, dayOfMonth = 3),
                time =  LocalTime(hour = 14, minute = 0, second = 0, nanosecond = 0)
            ),
            dateOfEnd = LocalDateTime(
                date = LocalDate(year = 2023, monthNumber = 8, dayOfMonth = 3),
                time =  LocalTime(hour = 15, minute = 30, second = 0, nanosecond = 0)
            )
        )
    )

    private val bookings = MutableStateFlow(initLis)

    override suspend fun changeBooking(bookId: Long, bookingIfo: BookingIfo) {
        TODO("Not yet implemented")
    }

    override suspend fun createBook(bookingIfo: BookingIfo) {
        TODO("Not yet implemented")
    }

    override suspend fun getBookingsForUser(): StateFlow<List<BookingIfo>> {
        TODO("Not yet implemented")
    }

    override suspend fun getBookingsByDate(date: LocalDate): StateFlow<List<BookingIfo>> {
        bookings.update { initLis.filter { it.dateOfStart.date == date } }
        return bookings
    }

}