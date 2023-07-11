package band.effective.office.tablet.domain.useCase

import band.effective.office.tablet.network.repository.BookingRepository

class BookingUseCase(private val repository: BookingRepository) {
    suspend fun invoke() = repository.bookingRoom()
}