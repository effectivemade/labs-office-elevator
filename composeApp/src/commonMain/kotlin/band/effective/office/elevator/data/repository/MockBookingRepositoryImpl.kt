//package band.effective.office.elevator.data.repository
//
//import band.effective.office.elevator.domain.repository.BookingRepository
//import band.effective.office.elevator.domain.models.BookingInfo
//import band.effective.office.elevator.domain.models.CreatingBookModel
//import band.effective.office.elevator.domain.models.ErrorWithData
//import band.effective.office.elevator.ui.employee.aboutEmployee.models.BookingsFilter
//import band.effective.office.network.model.Either
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.IO
//import kotlinx.coroutines.flow.Flow
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.flow.update
//import kotlinx.datetime.LocalDate
//import kotlinx.datetime.LocalDateTime
//import kotlinx.datetime.LocalTime
//
//class MockBookingRepositoryImpl: BookingRepository {
//    private val scope = CoroutineScope(Dispatchers.IO)
//    private val initLis = listOf(
//        BookingInfo(
//            id = "2455L",
//            ownerId = "1L",
//            seatName = "Seat A1",
//            dateOfStart = LocalDateTime(
//                date = LocalDate(year = 2023, monthNumber = 7, dayOfMonth = 13),
//                time =  LocalTime(hour = 14, minute = 15, second = 0, nanosecond = 0)
//            ),
//            dateOfEnd = LocalDateTime(
//                date = LocalDate(year = 2023, monthNumber = 7, dayOfMonth = 13),
//                time =  LocalTime(hour = 14, minute = 30, second = 0, nanosecond = 0)
//            )
//        ),
//        BookingInfo(
//            id = "303040L",
//            ownerId = "1L",
//            seatName = "Seat A2",
//            dateOfStart = LocalDateTime(
//                date = LocalDate(year = 2023, monthNumber = 7, dayOfMonth = 13),
//                time =  LocalTime(hour = 14, minute = 15, second = 0, nanosecond = 0)
//            ),
//            dateOfEnd = LocalDateTime(
//                date = LocalDate(year = 2023, monthNumber = 7, dayOfMonth = 13),
//                time =  LocalTime(hour = 15, minute = 30, second = 0, nanosecond = 0)
//            )
//        ),
//        BookingInfo(
//            id = "8989L",
//            ownerId = "1L",
//            seatName = "Seat A2",
//            dateOfStart = LocalDateTime(
//                date = LocalDate(year = 2023, monthNumber = 7, dayOfMonth = 14),
//                time =  LocalTime(hour = 14, minute = 15, second = 0, nanosecond = 0)
//            ),
//            dateOfEnd = LocalDateTime(
//                date = LocalDate(year = 2023, monthNumber = 7, dayOfMonth = 14),
//                time =  LocalTime(hour = 15, minute = 30, second = 0, nanosecond = 0)
//            )
//        ),
//        BookingInfo(
//            id = "234L",
//            ownerId = "1L",
//            seatName = "Seat A2",
//            dateOfStart = LocalDateTime(
//                date = LocalDate(year = 2023, monthNumber = 7, dayOfMonth = 15),
//                time =  LocalTime(hour = 14, minute = 15, second = 0, nanosecond = 0)
//            ),
//            dateOfEnd = LocalDateTime(
//                date = LocalDate(year = 2023, monthNumber = 7, dayOfMonth = 15),
//                time =  LocalTime(hour = 15, minute = 30, second = 0, nanosecond = 0)
//            )
//        ),
//        BookingInfo(
//            id = "754L",
//            ownerId = "1L",
//            seatName = "Seat A2",
//            dateOfStart = LocalDateTime(
//                date = LocalDate(year = 2023, monthNumber = 8, dayOfMonth = 3),
//                time =  LocalTime(hour = 14, minute = 15, second = 0, nanosecond = 0)
//            ),
//            dateOfEnd = LocalDateTime(
//                date = LocalDate(year = 2023, monthNumber = 8, dayOfMonth = 3),
//                time =  LocalTime(hour = 15, minute = 30, second = 0, nanosecond = 0)
//            )
//        ),
//        BookingInfo(
//            id = "2222L",
//            ownerId = "1H",
//            seatName = "Seat A2",
//            dateOfStart = LocalDateTime(
//                date = LocalDate(year = 2023, monthNumber = 8, dayOfMonth = 3),
//                time =  LocalTime(hour = 14, minute = 15, second = 0, nanosecond = 0)
//            ),
//            dateOfEnd = LocalDateTime(
//                date = LocalDate(year = 2023, monthNumber = 8, dayOfMonth = 3),
//                time =  LocalTime(hour = 15, minute = 30, second = 0, nanosecond = 0)
//            )
//        ),
//        BookingInfo(
//            id = "358L",
//            ownerId = "1H",
//            seatName= "Переговорная Sun",
//            dateOfStart = LocalDateTime(
//                date = LocalDate(year = 2023, monthNumber = 8, dayOfMonth = 17),
//                time = LocalTime(hour = 14, minute = 10, second = 0, nanosecond = 0)
//            ),
//            dateOfEnd = LocalDateTime(
//                date = LocalDate(year = 2023, monthNumber = 8, dayOfMonth = 17),
//                time = LocalTime(hour = 16, minute = 10, second = 0, nanosecond = 0)
//            )
//        )
//    )
//
//    private val bookings = MutableStateFlow(initLis)
//
//    override suspend fun changeBooking(bookingInfo: BookingInfo) {
//
//    }
//
//    override suspend fun createBook(bookingInfo: CreatingBookModel) {
//
//    }
//
//    override suspend fun getBookingsForUser(ownerId:String, bookingsFilter: BookingsFilter): StateFlow<List<BookingInfo>> {
//        bookings.update {
//            initLis.filter {
//                it.ownerId == ownerId &&
//                        ((it.id.contains('M') && bookingsFilter.meetRoom) ||
//                        (it.id.contains('W') && bookingsFilter.workPlace))
//            }
//        }
//        return bookings
//    }
//
//    override suspend fun getBookingsByDate(date: LocalDate, ownerId:String, bookingsFilter: BookingsFilter): Flow<Either<ErrorWithData<List<BookingInfo>>, List<BookingInfo>>> {
//        bookings.update {
//            initLis.filter {
//                it.dateOfStart.date == date && it.ownerId == ownerId &&
//                        ((it.id.contains('M') && bookingsFilter.meetRoom) ||
//                        (it.id.contains('W') && bookingsFilter.workPlace))
//            }
//        }
//        return bookings
//    }
//
//}