package band.effective.office.elevator.domain.useCase

import band.effective.office.elevator.domain.models.ErrorWithData
import band.effective.office.elevator.domain.models.WorkSpace
import band.effective.office.elevator.domain.models.toUIModel
import band.effective.office.elevator.domain.repository.WorkspaceRepository
import band.effective.office.network.model.Either
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.LocalDateTime

class WorkspacesUseCase (
    private val repository: WorkspaceRepository
) {
    suspend fun getZones() = repository.getZones()

    suspend fun getWorkSpaces(
        tag: String,
        freeFrom: LocalDateTime? = null,
        freeUntil: LocalDateTime? = null
    ) = repository.getWorkFreeWorkSpace(
        tag = tag,
        freeFrom = freeFrom,
        freeUntil = freeUntil
    ).map()

    private fun Flow<Either<ErrorWithData<List<WorkSpace>>, List<WorkSpace>>>.map() =
        this.map { response ->
            when(response) {
                is Either.Error -> Either.Error(
                    ErrorWithData(
                    error = response.error.error,
                    saveData = response.error.saveData?.toUIModel()
                )
                )
                is Either.Success -> Either.Success(response.data.toUIModel())
            }
        }
}