package band.effective.office.tablet.domain.useCase

import band.effective.office.network.model.Either
import band.effective.office.network.model.ErrorResponse
import band.effective.office.tablet.domain.model.EventInfo
import band.effective.office.tablet.network.repository.BookingRepository
import band.effective.office.tablet.network.repository.RoomRepository
import band.effective.office.tablet.utils.map

/**Use case for booking room*/
class BookingUseCase(
    private val repository: BookingRepository,
    private val roomRepository: RoomRepository
) {
    suspend fun getRoom(room: String) = try {
        roomRepository.getRoomsInfo().map(
            errorMapper = { it.error },
            successMapper = { it.first { roomInfo -> roomInfo.name == room } }
        )
    } catch (e: NoSuchElementException) {
        Either.Error(ErrorResponse.getResponse(404))
    }

    suspend operator fun invoke(
        eventInfo: EventInfo,
        room: String
    ): Either<ErrorResponse, String> =
        with(getRoom(room)) {
            when (this) {
                is Either.Error -> this
                is Either.Success -> repository.bookingRoom(eventInfo, data)
            }
        }

    suspend fun update(eventInfo: EventInfo, room: String) =
        with(getRoom(room)) {
            when (this) {
                is Either.Error -> this
                is Either.Success -> repository.updateBooking(
                    eventInfo = eventInfo,
                    room = data
                )
            }
        }
}