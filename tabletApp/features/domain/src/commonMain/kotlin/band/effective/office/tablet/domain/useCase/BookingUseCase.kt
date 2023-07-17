package band.effective.office.tablet.domain.useCase

import band.effective.office.tablet.network.repository.BookingRepository

/**Use case for booking room*/
class BookingUseCase(private val repository: BookingRepository) {
    suspend fun invoke() = repository.bookingRoom()
}