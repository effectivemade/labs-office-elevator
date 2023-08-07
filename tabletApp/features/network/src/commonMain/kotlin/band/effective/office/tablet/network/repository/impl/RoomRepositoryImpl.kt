package band.effective.office.tablet.network.repository.impl

import band.effective.office.network.api.Api
import band.effective.office.network.dto.BookingInfo
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

    private val roomInfo: MutableStateFlow<Either<ErrorWithData<RoomInfo>, RoomInfo>> =
        MutableStateFlow(Either.Error(ErrorWithData<RoomInfo>(ErrorResponse(0, ""), null)))

    private suspend fun loadRoomInfo(roomId: String): Either<ErrorWithData<RoomInfo>, RoomInfo> =
        with(roomInfo.value) {
            val events = loadEvents(roomId)
            roomInfo.update { api.getWorkspace(roomId).convert(this).addEvents(events) }
            roomInfo.value
        }

    private suspend fun loadEvents(roomId: String) =
        api.getBookingsByWorkspaces(roomId)

    override suspend fun getRoomInfo(room: String): Either<ErrorWithData<RoomInfo>, RoomInfo> =
        with(roomInfo.value) {
            if (this is Either.Error && error.error.code == 0) loadRoomInfo(room)
            else apply { loadRoomInfo(room) }
        }

    override suspend fun getRoomsInfo(): Either<ErrorResponse, List<RoomInfo>> =
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
                        }
                    }
                    Either.Success(roomList.mapIndexed { index, room ->
                        room.toRoomInfo().addEvents(events[index])
                    })
                }
            }
        }

    override fun subscribeOnUpdates(
        roomId: String
    ): Flow<Either<ErrorWithData<RoomInfo>, RoomInfo>> =
        channelFlow {
            send(loadRoomInfo(roomId))
            launch {
                api.subscribeOnWorkspaceUpdates(roomId).collect {
                    send(
                        it.convert(roomInfo.value).addEvents(loadEvents(roomId))
                            .apply { roomInfo.update { this } })
                }
            }
            launch {
                api.subscribeOnBookingsList(roomId).collect {
                    send(
                        getRoomInfo(roomId).addEvents(it)
                            .apply { roomInfo.update { this } })
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

    private suspend fun BookingInfo.toEventInfo() = let {
        EventInfo(
            startTime = GregorianCalendar().apply { time = Date(it.begin) },
            finishTime = GregorianCalendar().apply { time = Date(it.end) },
            organizer = getOrgById(it.ownerId),
            id = it.id
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

    private suspend fun Either<ErrorWithData<RoomInfo>, RoomInfo>.addEvents(loadEvents: Either<ErrorResponse, List<BookingInfo>>): Either<ErrorWithData<RoomInfo>, RoomInfo> =
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

