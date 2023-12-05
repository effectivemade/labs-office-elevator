package band.effective.office.tv.utils

import java.util.Calendar
import java.util.GregorianCalendar

/**Get tomorrow date. Time: 23:59*/
fun tomorrow(): GregorianCalendar {
    val result = GregorianCalendar()
    result.set(Calendar.DAY_OF_MONTH, result.get(Calendar.DAY_OF_MONTH) + 1)
    result.set(Calendar.HOUR, 23)
    result.set(Calendar.MINUTE, 59)
    return result
}

/**Get date with time: 23:59*/
fun GregorianCalendar.fullDay(): GregorianCalendar {
    val result = this
    result.set(Calendar.HOUR, 23)
    result.set(Calendar.MINUTE, 59)
    return result
}