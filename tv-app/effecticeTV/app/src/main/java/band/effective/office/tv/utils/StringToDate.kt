package band.effective.office.tv.utils

import java.util.*

fun String.getDate(): GregorianCalendar {
    val components = split(".")
    if (components.size < 3) return GregorianCalendar()
    return GregorianCalendar(
        components[2].toInt(),
        components[1].toInt() - 1,
        components[0].toInt()
    )
}