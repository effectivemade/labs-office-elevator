package band.effective.office.tablet.ui.freeNegotiationsScreen.ui.freeNegotiationsScreen.uiComponents

import java.util.Calendar

fun checkDuration(startEvent: Calendar, newEventDuration: Int): Boolean{
    val deviation = 5
    val currentTime = Calendar.getInstance()
    val ml = startEvent.timeInMillis - currentTime.timeInMillis
    val minutesDifferent = ml.toInt() / 60000
    return minutesDifferent >= newEventDuration - deviation
}

fun getDurationRelativeCurrentTime(time: Calendar): Int{
    val currentTime = Calendar.getInstance()
    val ml = time.timeInMillis - currentTime.timeInMillis
    return ml.toInt() / 60000 + (ml.toInt() % 60 > 30).toInt()
}

private fun Boolean.toInt() = if (this) 1 else 0