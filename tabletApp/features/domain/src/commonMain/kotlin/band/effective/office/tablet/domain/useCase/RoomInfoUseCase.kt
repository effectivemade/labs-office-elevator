package band.effective.office.tablet.domain.useCase

import band.effective.office.tablet.network.repository.RoomRepository
import band.effective.office.tablet.utils.map

/**Use case for get info about room*/
class RoomInfoUseCase(private val repository: RoomRepository) {
    suspend operator fun invoke(room: String = "Sirius") = repository.getRoomInfo(room)

    /**Subscribe on changes information
     * @param scope scope for collect new information
     * @param handler handler for new information*/
    fun subscribe(
        roomId: String = "Sirius"
    ) = repository.subscribeOnUpdates(roomId)

    suspend fun getOtherRoom(roomId: String = "Sirius") = repository.getRoomsInfo().map(
        errorMapper = { it },
        successMapper = { it.filter { room -> room.id != roomId } })
}