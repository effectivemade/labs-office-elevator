import io.ktgp.gpio.Gpio

/**
 * Extends [CompositeDevice] to represent a generic dual-motor robot.
 * @param left the pins of the left motor's controller.
 * @param right the pins of the right motor's controller.
 */
class Robot(gpio: Gpio, left: ForwardBackwardPin, right: ForwardBackwardPin) : CompositeDevice<Motor> {
    /**
     * The motor on the left of the robot.
     */
    val leftMotor: Motor get() = devices[0]

    /**
     * The motor on the left of the robot.
     */
    val rightMotor: Motor get() = devices[1]

    override val devices: List<Motor> = buildDevicesSafely {
        add(Motor(gpio, left))
        add(Motor(gpio, right))
    }

    /**
     * Drive the robot backward by running both motors backward.
     */
    fun backward() {
        leftMotor.backward()
        rightMotor.backward()
    }

    /**
     * Drive the robot forward by running both motors forward.
     */
    fun forward() {
        leftMotor.forward()
        rightMotor.forward()
    }

    /**
     *  Make the robot turn left by running the right motor forward and left motor stopped.
     */
    fun leftHalf() {
        leftMotor.stop()
        rightMotor.forward()
    }

    /**
     * Make the robot turn right by running the left motor forward and right motor stopped.
     */
    fun rightHalf() {
        rightMotor.stop()
        leftMotor.forward()
    }

    /**
     * Reverse the robot’s current motor directions. If the robot is currently
     * running full speed forward, it will run full speed backward.
     * If the robot is turning left at half-speed, it will turn right at half-speed.
     * If the robot is currently stopped it will remain stopped.
     */
    fun reverse() {
        leftMotor.reverse()
        rightMotor.reverse()
    }

    /**
     * Stop the robot.
     */
    fun stop() {
        leftMotor.stop()
        rightMotor.stop()
    }

    override fun toString(): String =
        "Robot(left=$leftMotor, right=$rightMotor)"

    val isStopped: Boolean get() = !isActive
    val isForward: Boolean get() = leftMotor.isForward && rightMotor.isForward
    val isBackward: Boolean get() = leftMotor.isBackward && rightMotor.isBackward
}

/**
 * @param left the pins of the left motor's controller.
 * @param right the pins of the right motor's controller.
 */
fun Gpio.robot(left: ForwardBackwardPin, right: ForwardBackwardPin) = Robot(this, left, right)
