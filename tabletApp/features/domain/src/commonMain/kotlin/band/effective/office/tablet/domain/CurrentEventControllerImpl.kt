package band.effective.office.tablet.domain

import band.effective.office.tablet.domain.useCase.RoomInfoUseCase
import band.effective.office.tablet.network.repository.CancelRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.GregorianCalendar

/**Controller implementation following for current event*/
class CurrentEventControllerImpl(
    private val roomUseCase: RoomInfoUseCase,
    private val cancelRepository: CancelRepository
) : CurrentEventController(roomUseCase, cancelRepository) {
    override fun update() = scope.launch {
        while (true) {
            val roomInfo = roomUseCase() //get actual room info
            val nextEventTime =
                roomInfo.currentEvent?.finishTime ?: roomInfo.eventList.firstOrNull()?.startTime // get next update time
            if (nextEventTime != null) { // check new event exist
                val timeToUpdate = nextEventTime.time.time - GregorianCalendar().time.time // calc time to next update
                delay(timeToUpdate) // wait for update
                currentEvent = roomUseCase().currentEvent // update info
                handlersList.forEach { it() } // call handlers
            }
        }
    }
}