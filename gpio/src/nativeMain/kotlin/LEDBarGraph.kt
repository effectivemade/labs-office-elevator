import io.ktgp.Closeable
import io.ktgp.gpio.Gpio

/**
 * Controls a line of LEDs representing a bar graph. Positive [value] (0 to 1) light the LEDs from first to last.
 * Alternatively set [litCount] to control how many LEDs are lit.
 * @param activeHigh See [LED.activeHigh].
 * @param initialValue the initial [value] of the graph given as a float, in the range of 0..1.
 */
class LEDBarGraph(
    gpio: Gpio,
    pins: List<GpioPin>,
    val activeHigh: Boolean = true,
    initialValue: Float = 0f
) : LEDCollection, Closeable {
    private val ledboard = LEDBoard(gpio, pins, activeHigh)

    override val devices: List<LED>
        get() = ledboard.devices

    override var litCount: Int
        get() = ledboard.litCount
        set(value) {
            require(value in litCountRange) { "The value must be in range $litCountRange" }
            ledboard.setValue(0 until value)
        }

    /**
     * Allowed range of the [litCount] property.
     */
    val litCountRange: IntRange get() = 0..count

    /**
     * The value of the LED bar graph. When no LEDs are lit, the value is 0. When all LEDs are lit, the value is 1.
     * Values between 0 and 1 light LEDs linearly from first to last.
     */
    var value: Float
        get() = litCount.toFloat() / ledboard.count
        set(value) {
            require(value in 0f..1f) { "The value must be in range 0..1" }
            litCount = (value * ledboard.count).toInt()
        }

    init {
        value = initialValue
    }

    override fun close() {
        ledboard.close()
    }

    override fun toString(): String =
        "LEDBarGraph(${if (activeHigh) "active_high" else "active_low"}; $litCount lit out of ${leds.map { it.pin }})"
}

/**
 * Controls multiple LEDs on given [pins]. Don't forget to close the [LEDBarGraph] afterwards.
 * @param pins connect to LEDs on all of these pins.
 * @param activeHigh See [LED.activeHigh].
 * @param initialValue the initial [LEDBarGraph.value] of the graph given as a float, in the range of 0..1.
 */
fun Gpio.ledBarGraph(
    vararg pins: GpioPin,
    activeHigh: Boolean = true,
    initialValue: Float = 0f
) = LEDBarGraph(this, pins.toList(), activeHigh, initialValue)
