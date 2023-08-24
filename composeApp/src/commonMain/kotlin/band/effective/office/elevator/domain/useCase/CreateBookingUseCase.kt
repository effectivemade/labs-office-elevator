package band.effective.office.elevator.domain.useCase

import band.effective.office.elevator.domain.models.CreatingBookModel
import band.effective.office.elevator.domain.repository.BookingRepository
import kotlinx.coroutines.CoroutineScope

class CreateBookingUseCase(private val repository: BookingRepository) {
    suspend fun execute(coroutineScope: CoroutineScope, creatingBookModel: CreatingBookModel) {
        repository.createBook(creatingBookModel = creatingBookModel)
    }
}