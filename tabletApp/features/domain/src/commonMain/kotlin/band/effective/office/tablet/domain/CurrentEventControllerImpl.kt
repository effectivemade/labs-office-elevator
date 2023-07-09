package band.effective.office.tablet.domain

import band.effective.office.tablet.domain.useCase.RoomInfoUseCase
import band.effective.office.tablet.network.repository.CancelRepository
import band.effective.office.tablet.network.repository.ServerUpdateRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.GregorianCalendar

class CurrentEventControllerImpl(
    private val roomUseCase: RoomInfoUseCase,
    private val serverUpdateRepository: ServerUpdateRepository,
    private val cancelRepository: CancelRepository
) : CurrentEventController(roomUseCase, serverUpdateRepository, cancelRepository) {
    override fun update() = scope.launch {
        while (true) {
            val roomInfo = roomUseCase()
            val nextEventTime =
                roomInfo.currentEvent?.finishTime ?: roomInfo.eventList.firstOrNull()?.startTime
            if (nextEventTime != null) {
                val timeToUpdate = nextEventTime.time.time - GregorianCalendar().time.time
                delay(timeToUpdate)
                currentEvent = roomUseCase().currentEvent
                handlersList.forEach { it() }
            }
        }
    }
}