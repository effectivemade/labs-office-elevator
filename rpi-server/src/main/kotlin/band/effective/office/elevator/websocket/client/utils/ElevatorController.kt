package band.effective.office.elevator.websocket.client.utils


object ElevatorController {

    fun call() {
        try {
            println("Successfully")
            Runtime.getRuntime().exec("sudo python3 script.py")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}