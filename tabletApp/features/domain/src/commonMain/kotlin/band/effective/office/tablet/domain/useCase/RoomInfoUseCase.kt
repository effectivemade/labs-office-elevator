package band.effective.office.tablet.domain.useCase

import band.effective.office.network.model.Either
import band.effective.office.tablet.domain.model.ErrorWithData
import band.effective.office.tablet.domain.model.RoomInfo
import band.effective.office.tablet.network.repository.RoomRepository
import band.effective.office.tablet.utils.map
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.flow
import java.util.GregorianCalendar

/**Use case for get info about room*/
class RoomInfoUseCase(private val repository: RoomRepository) {
    suspend operator fun invoke(room: String = "Sirius") = repository.getRoomInfo(room).filter()


    /**Subscribe on changes information
     * @param scope scope for collect new information
     * @param handler handler for new information*/
    fun subscribe(
        roomId: String = "Sirius",
        scope: CoroutineScope
    ) = flow {
        repository.subscribeOnUpdates(roomId, scope).collect { emit(it.filter()) }
    }

    suspend fun getOtherRoom(roomId: String = "Sirius") = repository.getRoomsInfo().map(
        errorMapper = { it },
        successMapper = { it.filter { room -> room.id != roomId } })

    private fun Either<ErrorWithData<RoomInfo>, RoomInfo>.filter() = map(
        errorMapper = { error ->
            val save = error.saveData
            if (save != null) error.copy(saveData = save.copy(eventList = save.eventList.filter { it.startTime > GregorianCalendar() })) else error
        },
        successMapper = { it.copy(eventList = it.eventList.filter { it.startTime > GregorianCalendar() }) })
}