package band.effective.office.elevator.domain.useCase

import band.effective.office.elevator.domain.models.BookingInfoDomain
import band.effective.office.elevator.domain.repository.BookingRepository

class ChangeBookingUseCase(
    private val repository: BookingRepository
) {
    suspend fun execute(bookingInfoDomain: BookingInfoDomain) {
        repository.changeBooking(bookingInfoDomain = bookingInfoDomain)
    }
}