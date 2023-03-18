import io.ktgp.gpio.Gpio

/**
 * A single LED on given [pin]. The LED is off by default.
 *
 * Connect the cathode (short leg, flat side) of the LED to a ground pin; connect the anode
 * (longer leg) to a limiting resistor; connect the other side of the limiting resistor to a
 * GPIO pin (the limiting resistor can be placed either side of the LED).
 * @param activeHigh If `true` (the default), the LED will operate normally with the circuit described above.
 * If `false` you should wire the cathode to the GPIO pin, and the anode to a 3V3 pin (via a limiting resistor).
 * @param initialValue if `false` (the default), the LED will be off initially.
 * If `true`, the LED will be switched on initially.
 */
class LED(
    gpio: Gpio,
    pin: GpioPin,
    activeHigh: Boolean = true,
    initialValue: Boolean = false
) : DigitalOutputDevice(gpio, pin, activeHigh, initialValue, "LED") {

    /**
     * Whether the LED is on or not.
     */
    var isLit: Boolean
    get() = isActive
        set(value) {
            isActive = value
        }
}

/**
 * Controls a LED on given [pin]. Don't forget to close the [LED] afterwards.
 */
fun Gpio.led(pin: GpioPin) = LED(this, pin)
