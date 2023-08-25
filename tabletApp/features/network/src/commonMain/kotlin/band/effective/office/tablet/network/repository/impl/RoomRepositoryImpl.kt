package band.effective.office.tablet.network.repository.impl

import band.effective.office.network.api.Api
import band.effective.office.network.dto.BookingDTO
import band.effective.office.network.dto.WorkspaceDTO
import band.effective.office.network.model.Either
import band.effective.office.network.model.ErrorResponse
import band.effective.office.tablet.domain.model.ErrorWithData
import band.effective.office.tablet.domain.model.EventInfo
import band.effective.office.tablet.domain.model.Organizer
import band.effective.office.tablet.domain.model.RoomInfo
import band.effective.office.tablet.network.repository.OrganizerRepository
import band.effective.office.tablet.network.repository.RoomRepository
import band.effective.office.tablet.utils.map
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date
import java.util.GregorianCalendar

class RoomRepositoryImpl(
    private val api: Api,
    private val organizerRepository: OrganizerRepository
) : RoomRepository {

    private val roomInfo: MutableStateFlow<Either<ErrorWithData<List<RoomInfo>>, List<RoomInfo>>> =
        MutableStateFlow(Either.Error(ErrorWithData<List<RoomInfo>>(ErrorResponse(0, ""), null)))

    private suspend fun loadEvents(roomId: String) =
        api.getBookingsByWorkspaces(roomId)

    override suspend fun getRoomInfo(room: String): Either<ErrorWithData<RoomInfo>, RoomInfo> =
        getRoomsInfo().map(errorMapper = {
            val save = it.saveData
            if (save == null) {
                ErrorWithData<RoomInfo>(it.error, null)
            } else {
                ErrorWithData<RoomInfo>(it.error, save.firstOrNull { it.name == room })
            }
        }, successMapper = {
            val room = it.firstOrNull { it.name == room }
            if (room == null) {
                RoomInfo.defaultValue// TODO fix return error
            } else room
        })

    override suspend fun getRoomsInfo(): Either<ErrorWithData<List<RoomInfo>>, List<RoomInfo>> =
        with(roomInfo.value) {
            if (this is Either.Error) {
                val response = loadRooms().map(
                    errorMapper = { ErrorWithData<List<RoomInfo>>(it, null) },
                    successMapper = { it })
                roomInfo.update { response }
                response
            } else this
        }

    suspend fun loadRooms() =
        api.getWorkspaces("meeting").run {
            when (this) {
                is Either.Error -> this
                is Either.Success -> {
                    val roomList = this.data
                    val ids = roomList.map { it.id }
                    val events = mutableListOf<List<EventInfo>>()
                    for (id in ids) {
                        val loadEvents = loadEvents(id)
                        if (loadEvents is Either.Success) {
                            events.add(loadEvents.data.map { it.toEventInfo() })
                        } else events.add(listOf())
                    }
                    Either.Success(roomList.mapIndexed { index, room ->
                        room.toRoomInfo().addEvents(events[index])
                    })
                }
            }
        }

    override fun subscribeOnUpdates(
        roomId: String,
        scope: CoroutineScope
    ): Flow<Either<ErrorWithData<RoomInfo>, RoomInfo>> =
        channelFlow {
            send(getRoomInfo(roomId))
            launch {
                api.subscribeOnWorkspaceUpdates(roomId, scope).collect {
                    roomInfo.update {
                        loadRooms().map(
                            errorMapper = { ErrorWithData<List<RoomInfo>>(it, null) },
                            successMapper = { it })
                    }
                    send(
                        it.convert(getRoomInfo(roomId)).addEvents(loadEvents(roomId))
                    )
                }
            }
            launch {
                api.subscribeOnBookingsList(roomId, scope).collect {
                    roomInfo.update {
                        loadRooms().map(
                            errorMapper = { ErrorWithData<List<RoomInfo>>(it, null) },
                            successMapper = { it })
                    }
                    send(
                        getRoomInfo(roomId).addEvents(it)
                    )
                }
            }
            awaitClose()
        }

    private fun Either<ErrorResponse, WorkspaceDTO>.convert(oldValue: Either<ErrorWithData<RoomInfo>, RoomInfo>): Either<ErrorWithData<RoomInfo>, RoomInfo> =
        map(
            errorMapper = {
                when (oldValue) {
                    is Either.Error -> ErrorWithData(it, oldValue.error.saveData)
                    is Either.Success -> ErrorWithData(it, oldValue.data)
                }
            },
            successMapper = { it.toRoomInfo() }
        )

    private suspend fun BookingDTO.toEventInfo() = let {
        EventInfo(
            startTime = GregorianCalendar().apply { time = Date(it.beginBooking) },
            finishTime = GregorianCalendar().apply { time = Date(it.endBooking) },
            organizer = getOrgById(it.owner.id),
            id = it.id!!
        )
    }

    private suspend fun getOrgById(ownerId: String): Organizer =
        with(organizerRepository.getOrganizersList()) {
            when {
                this is Either.Success -> data.firstOrNull { it.id == ownerId }
                this is Either.Error && error.saveData != null -> error.saveData!!.firstOrNull { it.id == ownerId }
                else -> null
            } ?: Organizer.default
        }

    private fun WorkspaceDTO.toRoomInfo() = RoomInfo(
        name = name,
        capacity = utilities.firstOrNull { it.name == "place" }?.count ?: 0,
        isHaveTv = utilities.firstOrNull { it.name == "tv" } != null,
        socketCount = utilities.firstOrNull { it.name == "lan" }?.count ?: 0,
        eventList = listOf(),
        currentEvent = null,
        id = id
    )

    private suspend fun Either<ErrorWithData<RoomInfo>, RoomInfo>.addEvents(loadEvents: Either<ErrorResponse, List<BookingDTO>>): Either<ErrorWithData<RoomInfo>, RoomInfo> =
        if (loadEvents is Either.Success) {
            when (this) {
                is Either.Error -> this
                is Either.Success -> if (data.eventList.isEmpty() && data.currentEvent == null) Either.Success(
                    data.addEvents(loadEvents.data.map { it.toEventInfo() })
                ) else this
            }
        } else this

    private fun RoomInfo.addEvents(events: List<EventInfo>): RoomInfo =
        with(GregorianCalendar()) {
            copy(
                eventList = events.filter { event -> !(this >= event.startTime && this <= event.finishTime) }
                    .sortedBy { it.startTime },
                currentEvent = events.firstOrNull { event -> this >= event.startTime && this <= event.finishTime })
        }
}

