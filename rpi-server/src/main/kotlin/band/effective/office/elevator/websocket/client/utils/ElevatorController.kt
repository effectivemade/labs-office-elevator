package band.effective.office.elevator.websocket.client.utils

import com.pi4j.io.gpio.RaspiPin
import mhashim6.pi4k.digitalOutput

object ElevatorController {

    val pin = digitalOutput(RaspiPin.GPIO_01)

    fun call() {
        println("Requesting GPIO_01...")
        try {
            pin.toggle()
            println("Successfully")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}