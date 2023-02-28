import io.ktgp.gpio.Gpio

/**
 * @property forward The GPIO pin that the forward input of the motor driver chip is connected to.
 * See [GpioPin] for valid pin numbers.
 * @property backward The GPIO pin that the backward input of the motor driver chip is
 * connected to. See [GpioPin] for valid pin numbers.
 */
data class ForwardBackwardPin(
    val forward: GpioPin, val backward: GpioPin
) {
    init {
        forward.requireInGpioRange()
        backward.requireInGpioRange()
        require(forward != backward) { "Provide separate pins: $forward $backward" }
    }
}

/**
 * Extends CompositeDevice and represents a generic motor connected to a
 * bi-directional motor driver circuit (i.e. an [H-bridge](https://en.wikipedia.org/wiki/H_bridge).
 *
 * Attach an [H-bridge](https://en.wikipedia.org/wiki/H_bridge) motor controller to your Pi;
 * connect a power source (e.g. a battery pack or the 5V pin) to the controller;
 * connect the outputs of the controller board to the two terminals of the motor;
 * connect the inputs of the controller board to two GPIO pins.
 * @param pins pins
 */
class Motor(gpio: Gpio, pins: ForwardBackwardPin): CompositeDevice<DigitalOutputDevice> {
    private val forward: DigitalOutputDevice get() = devices[0]
    private val backward: DigitalOutputDevice get() = devices[1]

    override val devices: List<DigitalOutputDevice> = buildDevicesSafely {
        add(DigitalOutputDevice(gpio, pins.forward, name = "Forward"))
        add(DigitalOutputDevice(gpio, pins.backward, name = "Backward"))
    }

    /**
     * Drive the motor backwards.
     */
    fun backward() {
        forward.off()
        backward.on()
    }

    /**
     * Drive the motor forwards.
     */
    fun forward() {
        backward.off()
        forward.on()
    }

    /**
     * Reverse the current direction of the motor. If the motor is currently idle this
     * does nothing. Otherwise, the motor’s direction will be reversed at the current speed.
     */
    fun reverse() {
        if (isBackward) {
            forward()
        } else if (isForward) {
            backward()
        }
    }

    /**
     * Stop the motor.
     */
    fun stop() {
        backward.off()
        forward.off()
    }

    val isStopped: Boolean get() = !isActive
    val isForward: Boolean get() = forward.isActive
    val isBackward: Boolean get() = backward.isActive

    override fun toString(): String =
        "Motor($forward, $backward)"
}

fun Gpio.motor(pins: ForwardBackwardPin) = Motor(this, pins)
