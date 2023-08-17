package band.effective.office.tablet.ui.freeNegotiationsScreen.ui.freeNegotiationsScreen.uiComponents

import java.util.Calendar
import kotlin.math.abs

fun checkCurrentTime(startTime: Calendar): Boolean {
    val currentTime = Calendar.getInstance()
    val ml = startTime.timeInMillis - currentTime.timeInMillis
    return abs(ml) < 60000
}

fun checkDuration(startEvent: Calendar, newEventDuration: Int): Boolean{
    val currentTime = Calendar.getInstance()
    val ml = startEvent.timeInMillis - currentTime.timeInMillis
    val minutesDifferent = ml.toInt() / 60000
    return minutesDifferent >= newEventDuration
}

fun getDurationRelativeCurrentTime(time1: Calendar, time2: Calendar): Int{
    val ml = time2.timeInMillis - time1.timeInMillis
    return ml.toInt() / 60000 + (ml.toInt() % 60 > 30).toInt()
}

private fun Boolean.toInt() = if (this) 1 else 0