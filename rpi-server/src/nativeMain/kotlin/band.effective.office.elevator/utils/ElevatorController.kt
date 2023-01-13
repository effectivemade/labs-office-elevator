package band.effective.office.elevator.utils

object ElevatorController {

    fun call() {
        println("Requesting GPIO_01...")
        try {
            executeCommand("sudo python3 script.py")
            println("Successfully")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}