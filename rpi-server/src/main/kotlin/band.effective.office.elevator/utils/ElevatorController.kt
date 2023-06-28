package band.effective.office.elevator.utils

import band.effective.office.tablet.utils.DateUtils

object ElevatorController {
    fun call() {
        println("-----------------[${DateUtils.now()}]-----------------")
        println("- Requesting GPIO_17...")
        try {
            executeCommand("sudo /home/ubuntu/elevator/gpio.kexe")
            println("-- Successfully")
        } catch (e: Exception) {
            e.printStackTrace()
        }
        println("----------------------------[END_CALL]---------------------------")
    }
}