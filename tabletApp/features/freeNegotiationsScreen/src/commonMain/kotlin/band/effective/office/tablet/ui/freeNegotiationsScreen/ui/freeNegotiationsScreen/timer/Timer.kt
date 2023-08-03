package band.effective.office.tablet.ui.freeNegotiationsScreen.ui.freeNegotiationsScreen.timer

import android.os.CountDownTimer

class Timer{
    private var timer: CountDownTimer? = null
    private var isClose: Boolean = false
    private val handlersList: MutableList<() -> Unit> = mutableListOf()

    fun startTimer(){
        timer = object : CountDownTimer(/* millisInFuture = */ 60000, /* countDownInterval = */1000) {

            override fun onTick(millisUntilFinished: Long) {

            }

            override fun onFinish() {
                handlersList.forEach { it() }
                if(timer != null && !isClose) {
                    startTimer()
                }
            }
        }.apply { start() }
    }

    fun close(){
        timer = null
        isClose = true
    }

    fun subscribe(onEvent: () -> Unit) {
        handlersList.add(onEvent)
    }
}