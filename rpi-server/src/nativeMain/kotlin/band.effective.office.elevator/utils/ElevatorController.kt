package band.effective.office.elevator.utils

object ElevatorController {

    fun call() {
        println("Requesting GPIO_01...")
        try {
            executeCommand("sudo ./ktgpio-example-app-main.kexe.kexe")
            println("Successfully")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}