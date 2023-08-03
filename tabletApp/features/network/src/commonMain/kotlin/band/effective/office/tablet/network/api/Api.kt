package band.effective.office.tablet.network.api

import band.effective.office.tablet.domain.model.Either
import band.effective.office.tablet.domain.model.RoomInfo
import band.effective.office.tablet.network.model.WebServerEvent
import kotlinx.coroutines.CoroutineScope
import network.model.ErrorResponse
import java.util.Calendar

interface Api {
    //TODO(Maksim Mishenko): change return value
    suspend fun getRoomInfo(room: String): Either<ErrorResponse, RoomInfo>
    suspend fun getOrganizers(): Either<ErrorResponse, List<String>>
    suspend fun cancelEvent(): Either<ErrorResponse, String>
    fun subscribeOnWebHock(scope: CoroutineScope, handler: (event: WebServerEvent) -> Unit)
    suspend fun bookingRoom(
        begin: Calendar,
        end: Calendar,
        owner: String,
        room: String
    ): Either<ErrorResponse, String>
}