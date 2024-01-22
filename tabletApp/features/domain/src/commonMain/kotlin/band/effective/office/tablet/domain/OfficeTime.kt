package band.effective.office.tablet.domain

import java.util.Calendar
import java.util.GregorianCalendar

object OfficeTime {
    fun startWorkTime() = GregorianCalendar().apply {
        set(Calendar.HOUR_OF_DAY, 8)
        set(Calendar.MINUTE, 0)
    }

    fun finishWorkTime() = GregorianCalendar().apply {
        set(Calendar.HOUR_OF_DAY, 22)
        set(Calendar.MINUTE, 0)
    }
}