package band.effective.office.elevator.data.repository

import band.effective.office.elevator.domain.repository.BookingRepository
import band.effective.office.elevator.domain.models.BookingInfoDomain
import band.effective.office.elevator.domain.models.CreatingBookModel
import band.effective.office.network.api.Api
import band.effective.office.elevator.ui.employee.aboutEmployee.models.BookingsFilter
import band.effective.office.network.dto.BookingDTO
import band.effective.office.network.model.Either
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class MockBookingRepositoryImpl(private val api:Api): BookingRepository {
    private val scope = CoroutineScope(Dispatchers.IO)
    private val workZones= listOf("Sirius", "Antares")
    private val meetRooms = listOf("Pluto", "Sun")
    private val initLis = listOf(
        BookingInfoDomain(
            id = "2455W",
            ownerId = "1L",
            seatName = "Seat A1",
            dateOfStart = LocalDateTime(
                date = LocalDate(year = 2023, monthNumber = 7, dayOfMonth = 13),
                time =  LocalTime(hour = 14, minute = 15, second = 0, nanosecond = 0)
            ),
            dateOfEnd = LocalDateTime(
                date = LocalDate(year = 2023, monthNumber = 7, dayOfMonth = 13),
                time =  LocalTime(hour = 14, minute = 30, second = 0, nanosecond = 0)
            )
        ),
        BookingInfoDomain(
            id = "303040W",
            ownerId = "1L",
            seatName = "Seat A2",
            dateOfStart = LocalDateTime(
                date = LocalDate(year = 2023, monthNumber = 7, dayOfMonth = 13),
                time =  LocalTime(hour = 14, minute = 15, second = 0, nanosecond = 0)
            ),
            dateOfEnd = LocalDateTime(
                date = LocalDate(year = 2023, monthNumber = 7, dayOfMonth = 13),
                time =  LocalTime(hour = 15, minute = 30, second = 0, nanosecond = 0)
            )
        ),
        BookingInfoDomain(
            id = "8989W",
            ownerId = "1L",
            seatName = "Seat A2",
            dateOfStart = LocalDateTime(
                date = LocalDate(year = 2023, monthNumber = 7, dayOfMonth = 14),
                time =  LocalTime(hour = 14, minute = 15, second = 0, nanosecond = 0)
            ),
            dateOfEnd = LocalDateTime(
                date = LocalDate(year = 2023, monthNumber = 7, dayOfMonth = 14),
                time =  LocalTime(hour = 15, minute = 30, second = 0, nanosecond = 0)
            )
        ),
        BookingInfoDomain(
            id = "234W",
            ownerId = "1L",
            seatName = "Seat A2",
            dateOfStart = LocalDateTime(
                date = LocalDate(year = 2023, monthNumber = 7, dayOfMonth = 15),
                time =  LocalTime(hour = 14, minute = 15, second = 0, nanosecond = 0)
            ),
            dateOfEnd = LocalDateTime(
                date = LocalDate(year = 2023, monthNumber = 7, dayOfMonth = 15),
                time =  LocalTime(hour = 15, minute = 30, second = 0, nanosecond = 0)
            )
        ),
        BookingInfoDomain(
            id = "754W",
            ownerId = "1L",
            seatName = "Seat A2",
            dateOfStart = LocalDateTime(
                date = LocalDate(year = 2023, monthNumber = 8, dayOfMonth = 3),
                time =  LocalTime(hour = 14, minute = 15, second = 0, nanosecond = 0)
            ),
            dateOfEnd = LocalDateTime(
                date = LocalDate(year = 2023, monthNumber = 8, dayOfMonth = 3),
                time =  LocalTime(hour = 15, minute = 30, second = 0, nanosecond = 0)
            )
        ),
        BookingInfoDomain(
            id = "2222W",
            ownerId = "1H",
            seatName = "Arrakis",// | Seat 2
            dateOfStart = LocalDateTime(
                date = LocalDate(year = 2023, monthNumber = 8, dayOfMonth = 3),
                time =  LocalTime(hour = 14, minute = 15, second = 0, nanosecond = 0)
            ),
            dateOfEnd = LocalDateTime(
                date = LocalDate(year = 2023, monthNumber = 8, dayOfMonth = 3),
                time =  LocalTime(hour = 15, minute = 30, second = 0, nanosecond = 0)
            )
        ),
        BookingInfoDomain(
            id = "358M",
            ownerId = "1H",
            seatName= "Sun",// | Meeting room
            dateOfStart = LocalDateTime(
                date = LocalDate(year = 2023, monthNumber = 8, dayOfMonth = 17),
                time = LocalTime(hour = 14, minute = 10, second = 0, nanosecond = 0)
            ),
            dateOfEnd = LocalDateTime(
                date = LocalDate(year = 2023, monthNumber = 8, dayOfMonth = 17),
                time = LocalTime(hour = 16, minute = 10, second = 0, nanosecond = 0)
            )
        )
    )

    private val bookings = MutableStateFlow(initLis)

    override suspend fun changeBooking(bookingInfoDomain: BookingInfoDomain) {
        TODO("Not yet implemented")
    }

    override suspend fun createBook(bookingInfo: CreatingBookModel) {
        TODO("Not yet implemented")
    }

    override suspend fun getBookingsForUser(ownerId:String, bookingsFilter: BookingsFilter): StateFlow<List<BookingInfoDomain>> {
        val result = api.getBookingsByUser(ownerId)
        bookings.update {
            when(result){
                is Either.Success -> toBockingInfoDomain(result.data)
                else ->  initLis        //TODO():There we will catch errors and show error screens
            }.filter {
                //it.ownerId == ownerId && (
                                (it.seatName in meetRooms && bookingsFilter.meetRoom) ||
                                (it.seatName in workZones && bookingsFilter.workPlace)
                //)
            }
        }
        return bookings
    }

    override suspend fun getBookingsByDate(date: LocalDate, ownerId:String, bookingsFilter: BookingsFilter): StateFlow<List<BookingInfoDomain>> {
        bookings.update {
            initLis.filter {
                it.dateOfStart.date == date && it.ownerId == ownerId &&
                        ((it.id.contains('M') && bookingsFilter.meetRoom) ||
                        (it.id.contains('W') && bookingsFilter.workPlace))
            }
        }
        return bookings
    }

    private fun toBockingInfoDomain(bookingsInfo:List<BookingDTO>):List<BookingInfoDomain> {
        val domainBookingsInfo = mutableListOf<BookingInfoDomain>()
        bookingsInfo.forEach { bookingInfo ->
            domainBookingsInfo.add(
                BookingInfoDomain(
                    id=bookingInfo.id.toString(),
                    ownerId = bookingInfo.owner.id,
                    seatName = bookingInfo.workspace.id,
                    dateOfStart = toLocalDateTime(bookingInfo.beginBooking),
                    dateOfEnd = toLocalDateTime(bookingInfo.endBooking)
                )
            )
        }

        return domainBookingsInfo
    }
    private fun toLocalDateTime(time: Long): LocalDateTime{
        val instant=Instant.fromEpochMilliseconds(time)
        return instant.toLocalDateTime(TimeZone.UTC)
    }
}