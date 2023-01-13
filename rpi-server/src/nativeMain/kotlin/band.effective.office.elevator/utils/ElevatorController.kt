package band.effective.office.elevator.utils

object ElevatorController {

    fun call() {
        println("Requesting GPIO_01...")
        try {

            println("Successfully")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}