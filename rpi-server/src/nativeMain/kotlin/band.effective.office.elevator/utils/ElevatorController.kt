package band.effective.office.elevator.utils

import kotlinx.cinterop.cValue
import platform.posix.nanosleep
import platform.posix.timespec

object ElevatorController {
    val time = cValue<timespec> {
        tv_sec = 5
    }
    nanosleep(time, null)

    fun call() {
        println("-----------------[${DateUtils.now()}]-----------------")
        println("- Requesting GPIO_01...")
        try {

            //executeCommand("sudo python3 script.py")
            println("-- Successfully")
        } catch (e: Exception) {
            e.printStackTrace()
        }
        println("----------------------------[END_CALL]---------------------------")
    }
}