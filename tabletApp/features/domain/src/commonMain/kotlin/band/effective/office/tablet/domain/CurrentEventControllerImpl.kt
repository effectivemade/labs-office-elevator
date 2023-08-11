package band.effective.office.tablet.domain

import android.os.CountDownTimer
import band.effective.office.network.model.Either
import band.effective.office.tablet.domain.model.Settings
import band.effective.office.tablet.domain.useCase.RoomInfoUseCase
import band.effective.office.tablet.network.repository.CancelRepository
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.GregorianCalendar

/**Controller implementation following for current event*/
class CurrentEventControllerImpl(
    private val roomUseCase: RoomInfoUseCase, private val cancelRepository: CancelRepository
) : CurrentEventController(roomUseCase, cancelRepository) {
    private val timer = SingletonCountDownTimer()
    override fun update() = scope.launch {
        val roomInfo = when (val response = roomUseCase(Settings.current.checkCurrentRoom())) {
            is Either.Error -> null
            is Either.Success -> response.data
        } //get actual room info
        val nextEventTime = roomInfo?.currentEvent?.finishTime
            ?: roomInfo?.eventList?.firstOrNull()?.startTime // get next update time
        if (nextEventTime != null) { // if we have next event
            mutableTimeToUpdate.update { nextEventTime.time.time - GregorianCalendar().time.time }// calc time to next update
            timer.start(
                millisInFuture = timeToUpdate.value,
                countDownInterval = 1000,
                onTick = { millisUntilFinished -> mutableTimeToUpdate.update { millisUntilFinished } },
                onFinish = { handlersList.forEach { it() } })
        } else timer.cancel()
    }
}

class SingletonCountDownTimer() {
    private var timer: CountDownTimer? = null
    fun start(
        millisInFuture: Long,
        countDownInterval: Long,
        onTick: (Long) -> Unit = {},
        onFinish: () -> Unit = {}
    ) {
        timer?.cancel()
        timer = object : CountDownTimer(millisInFuture, countDownInterval) {

            override fun onTick(millisUntilFinished: Long) {
                onTick(millisUntilFinished)
            }

            override fun onFinish() {
                onFinish()
            }
        }.apply { start() }
    }

    fun cancel() {
        timer?.cancel()
    }
}