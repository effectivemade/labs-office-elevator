package band.effective.office.tablet.domain.useCase

import band.effective.office.network.model.Either
import band.effective.office.network.model.ErrorResponse
import band.effective.office.tablet.domain.model.EventInfo
import band.effective.office.tablet.domain.model.RoomInfo
import band.effective.office.tablet.network.repository.BookingRepository
import band.effective.office.tablet.network.repository.RoomRepository
import band.effective.office.tablet.utils.map

/**Use case for booking room*/
class BookingUseCase(
    private val repository: BookingRepository,
    private val roomRepository: RoomRepository
) {
    var rooms: Either<ErrorResponse, List<RoomInfo>>? = null
    suspend fun getRoom(room: String) = with(rooms) {
        if (this == null) rooms = roomRepository.getRoomsInfo()
        rooms?.map(
            errorMapper = { it },
            successMapper = { it.first { it.name == room } })
    }

    suspend operator fun invoke(
        eventInfo: EventInfo,
        room: String = "Sirius"
    ): Either<ErrorResponse, String> =
        with(getRoom(room)) {
            when (this) {
                null -> Either.Error(ErrorResponse(404, "Room not found"))
                is Either.Error -> this
                is Either.Success -> repository.bookingRoom(eventInfo, data)
            }
        }

    suspend fun update(eventInfo: EventInfo, room: String = "Sirius") =
        with(getRoom(room)) {
            when (this) {
                null -> Either.Error(ErrorResponse(404, "Room not found"))
                is Either.Error -> this
                is Either.Success -> repository.updateBooking(
                    eventInfo = eventInfo,
                    room = data
                )
            }
        }
}