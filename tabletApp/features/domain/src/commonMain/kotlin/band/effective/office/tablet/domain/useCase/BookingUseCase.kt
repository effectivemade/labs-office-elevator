package band.effective.office.tablet.domain.useCase

import band.effective.office.tablet.domain.model.EventInfo
import band.effective.office.tablet.network.repository.BookingRepository

/**Use case for booking room*/
class BookingUseCase(private val repository: BookingRepository) {
    suspend operator fun invoke(eventInfo: EventInfo, room: String = "Sirius") =
        repository.bookingRoom(eventInfo, room)

    suspend fun update(eventInfo: EventInfo, room: String = "Sirius") =
        repository.updateBooking(
            eventInfo = eventInfo,
            roomId = room
        )
}