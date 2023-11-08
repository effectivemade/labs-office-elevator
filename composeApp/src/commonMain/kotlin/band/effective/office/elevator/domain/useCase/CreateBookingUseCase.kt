package band.effective.office.elevator.domain.useCase

import band.effective.office.elevator.domain.models.CreatingBookModel
import band.effective.office.elevator.domain.repository.BookingRepository

class CreateBookingUseCase(private val repository: BookingRepository) {
    suspend fun execute(creatingBookModel: CreatingBookModel) =
        repository.createBook(creatingBookModel = creatingBookModel)
}