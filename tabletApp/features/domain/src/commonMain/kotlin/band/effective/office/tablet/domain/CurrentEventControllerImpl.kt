package band.effective.office.tablet.domain

import android.util.Log
import band.effective.office.tablet.domain.useCase.RoomInfoUseCase
import band.effective.office.tablet.network.repository.CancelRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Calendar
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
                roomInfo.currentEvent?.finishTime
                    ?: roomInfo.eventList.firstOrNull()?.startTime // get next update time
            if (nextEventTime != null) { // if we have next event
                mutableTimeToUpdate.update { nextEventTime.time.time - GregorianCalendar().time.time }// calc time to next update
                while (timeToUpdate.value > 1000) {
                    val timeMils =
                        if (timeToUpdate.value > 60000) (60 - GregorianCalendar().get(Calendar.SECOND)) * 1000L else timeToUpdate.value
                    //TODO(del log)
                    Log.e("time", "${timeToUpdate.value/60000}")
                    delay(timeMils) // wait for update
                    mutableTimeToUpdate.update { nextEventTime.time.time - GregorianCalendar().time.time }// calc time to next update
                }
                currentEvent = roomUseCase().currentEvent // update info
                handlersList.forEach { it() } // call handlers
            }
        }
    }
}