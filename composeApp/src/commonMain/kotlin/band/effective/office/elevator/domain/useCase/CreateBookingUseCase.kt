package band.effective.office.elevator.domain.useCase

import band.effective.office.elevator.domain.models.CreatingBookModel
import band.effective.office.elevator.domain.repository.BookingRepository
import band.effective.office.network.model.Either
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.map

class CreateBookingUseCase(private val repository: BookingRepository) {
    suspend fun execute(creatingBookModel: CreatingBookModel) =
        repository.createBook(creatingBookModel = creatingBookModel)
}