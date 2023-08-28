package band.effective.office.tablet.utils

import android.os.CountDownTimer

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