package band.effective.office.tv.core.ui.screen_with_controls

import band.effective.office.tv.BuildConfig
import kotlinx.coroutines.*
import javax.inject.Inject

class TimerSlideShow @Inject constructor() {
    private var timerJob: Job? = null

    private var scope: CoroutineScope? = null
    private lateinit var callbackToEnd: suspend () -> Unit
    var isPlay: Boolean = false

    fun init(
        scope: CoroutineScope,
        callbackToEnd: suspend () -> Unit,
        isPlay: Boolean = false
    ) {
        this.scope = scope
        this.callbackToEnd = callbackToEnd
        this.isPlay = isPlay
    }

    fun startTimer() {
        isPlay = true
        timerJob?.cancel()
        timerJob = scope?.launch(Dispatchers.Default) {
            while (isActive && isPlay){
                val startTime = System.currentTimeMillis()
                var currentTime = System.currentTimeMillis()
                while (isActive && currentTime - startTime < BuildConfig.slideShowPeriod * 1000)
                    currentTime = System.currentTimeMillis()
                if (isActive && isPlay)
                    callbackToEnd()
            }
        }
    }

    fun resetTimer() {
        timerJob?.cancel()
        timerJob = scope?.launch(Dispatchers.Default) {
            while (isActive && isPlay){
                val startTime = System.currentTimeMillis()
                var currentTime = System.currentTimeMillis()
                while (isActive && currentTime - startTime < BuildConfig.slideShowPeriod * 1000)
                    currentTime = System.currentTimeMillis()
                if (isActive && isPlay)
                    callbackToEnd()
            }
        }
    }

    fun stopTimer() {
        isPlay = false
        timerJob?.cancel()
    }
}