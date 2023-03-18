import io.ktgp.gpio.Event
import io.ktgp.gpio.Gpio

class Button(gpio: Gpio, pin: GpioPin) : DigitalInputDevice(gpio, pin, name = "Button") {
    override fun toString() = "Button(gpio${pin}=${if (isActive) "pressed" else "_"})"
}

/**
 * Creates a button on given [pin].
 */
fun Gpio.button(pin: GpioPin): Button = Button(this, pin)

/**
 * Listens for button presses. Blocks and repeatedly waits for a button to be pressed.
 * @param onPressed invoked when a button is pressed. Return true to continue waiting
 * for more button presses, return false to bail out and return from this function.
 */
fun Gpio.buttonListen(pin: GpioPin, onPressed: () -> Boolean) {
    pin.requireInGpioRange()
    listen(pin, activeLow = true) { e ->
        if (e.type == Event.Type.RISING_EDGE) {
            onPressed()
        } else {
            true
        }
    }
}