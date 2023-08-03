package band.effective.office.tablet.domain

import android.os.CountDownTimer
import band.effective.office.tablet.domain.model.Either
import band.effective.office.tablet.domain.useCase.RoomInfoUseCase
import band.effective.office.tablet.network.repository.CancelRepository
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.GregorianCalendar

/**Controller implementation following for current event*/
class CurrentEventControllerImpl(
    private val roomUseCase: RoomInfoUseCase,
    private val cancelRepository: CancelRepository
) : CurrentEventController(roomUseCase, cancelRepository) {
    private var timer: CountDownTimer? = null
    override fun update() = scope.launch {
            val roomInfo = when(val response = roomUseCase()){
                is Either.Error -> null
                is Either.Success -> response.data
            } //get actual room info
            val nextEventTime =
                roomInfo?.currentEvent?.finishTime
                    ?: roomInfo?.eventList?.firstOrNull()?.startTime // get next update time
            if (nextEventTime != null) { // if we have next event
                mutableTimeToUpdate.update { nextEventTime.time.time - GregorianCalendar().time.time }// calc time to next update
                timer = object : CountDownTimer(/* millisInFuture = */ timeToUpdate.value, /* countDownInterval = */1000) {

                    override fun onTick(millisUntilFinished: Long) {
                        mutableTimeToUpdate.update { millisUntilFinished }
                    }

                    override fun onFinish() {
                        handlersList.forEach { it() }
                    }
                }.apply { start() }
        }
    }

    override fun stopUpdate() {
        timer?.cancel()
    }
}