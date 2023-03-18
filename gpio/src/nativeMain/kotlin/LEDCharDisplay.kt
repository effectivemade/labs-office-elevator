import io.ktgp.Closeable
import io.ktgp.gpio.Gpio
import io.ktgp.util.sleep

/**
 * [Multi-segment LED displays](https://en.wikipedia.org/wiki/Seven-segment_display) typically have 7 pins (labelled “a” through “g”) representing 7 LEDs
 * layed out in a figure-of-8 fashion. Frequently, an
 * eigth pin labelled “dp” is included for a trailing decimal-point:
 *
 * ```
 *      a
 *    ━━━━━
 * f ┃     ┃ b
 *   ┃  g  ┃
 *    ━━━━━
 * e ┃     ┃ c
 *   ┃     ┃
 *    ━━━━━ • dp
 *      d
 * ```
 * Other common layouts are 9, 14, and 16 segment displays which include additional segments permitting more accurate renditions of alphanumerics. For example:
 * ```
 *      a
 *    ━━━━━
 * f ┃╲i┃j╱┃ b
 *   ┃ ╲┃╱k┃
 *   g━━ ━━h
 * e ┃ ╱┃╲n┃ c
 *   ┃╱l┃m╲┃
 *    ━━━━━ • dp
 *      d
 * ```
 * Such displays have either a common anode, or common cathode pin. This class defaults to the latter;
 * when using a common anode display active_high should be set to False.
 * @param pins Specify the GPIO pins that the multi-segment display is attached to. Pins should be in the
 * LED segment order A, B, C, D, E, F, G, and will be named automatically by the class.
 * If a decimal-point pin is present, specify it separately as the dp parameter.
 * @param dp If a decimal-point segment is present, specify it as this named parameter.
 * @property font A mapping of values (typically characters, but may also be numbers) to tuples of LED states.
 * A default mapping for ASCII characters is provided for 7 and 14 segment displays.
 *
 * The font is mutable after construction. You can assign a tuple of LED states to a character to
 * modify the font, delete an existing character in the font, or assign a mapping of characters to tuples to replace the entire font.
 *
 * Note that modifying the font never alters the underlying LED states. Only assignment to value, or calling the inherited
 * LEDCollection methods ([on], [off], etc.) modifies LED states. However, modifying the font may alter the character returned by querying value.
 * @property activeHigh See [LED.activeHigh].
 * @param initialValue the initial [value] of the graph given as a [Char], defaults to space `' '`
 * which typically maps to all LEDs being inactive.
 */
class LEDCharDisplay(
    gpio: Gpio,
    pins: List<GpioPin>,
    dp: GpioPin? = null,
    var font: Map<Char, List<Int>>? = null,
    val activeHigh: Boolean = true,
    initialValue: Char = ' '
) : LEDCollection, Closeable {
    private val _leds = LEDBoard(gpio, pins + (if (dp != null) listOf(dp) else listOf()), activeHigh, false)

    override val devices: List<LED>
        get() = _leds.devices

    /**
     * The decimal-point segment (DP) LED.
     */
    val dpled: LED? = if (dp == null) null else _leds.leds.last()

    override fun close() {
        _leds.close()
    }

    override val leds: List<LED>
        get() = _leds.leds

    var value: Char = ' '
        set(value) {
            field = value
            val character: List<Int> = font?.get(value) ?: font?.get(value.lowercaseChar()) ?: font?.get(value.uppercaseChar()) ?: listOf()
            _leds.setValue(*character.toIntArray())
        }

    /**
     * Gradually shows all characters from given [text], each character shown for
     * [durationMillis]. Repeats [n] times.
     */
    fun show(text: String, durationMillis: Long = 1000, n: Int = 1) {
        repeat(n) {
            text.forEach { value = it; sleep(durationMillis) }
        }
    }

    override fun toString(): String {
        val pins = buildString {
            _leds.leds.forEachIndexed { index, led ->
                if (led != dpled) {
                    append('a' + index)
                    append("/gpio")
                    append(led.pin)
                    append('=')
                    append(if (led.isLit) "on" else "off")
                    append(",")
                }
            }
        }
        return "LEDCharDisplay(${if (activeHigh) "active_high" else "active_low"}, '$value'; $pins dp=$dpled)"
    }

    init {
        value = initialValue
        if (font == null) {
            if (pins.size == 7) {
                font = font7Segment
            }
        }
    }
}

private val font7Segment: Map<Char, List<Int>> = mapOf(
    '0' to listOf(1, 1, 1, 1, 1, 1, 0),
    '1' to listOf(0, 1, 1, 0, 0, 0, 0),
    '2' to listOf(1, 1, 0, 1, 1, 0, 1),
    '3' to listOf(1, 1, 1, 1, 0, 0, 1),
    '4' to listOf(0, 1, 1, 0, 0, 1, 1),
    '5' to listOf(1, 0, 1, 1, 0, 1, 1),
    '6' to listOf(1, 0, 1, 1, 1, 1, 1),
    '7' to listOf(1, 1, 1, 0, 0, 0, 0),
    '8' to listOf(1, 1, 1, 1, 1, 1, 1),
    '9' to listOf(1, 1, 1, 1, 0, 1, 1),
    'a' to listOf(1, 1, 1, 0, 1, 1, 1),
    'b' to listOf(0, 0, 1, 1, 1, 1, 1),
    'c' to listOf(0, 0, 0, 1, 1, 0, 1),
    'C' to listOf(1, 0, 0, 1, 1, 1, 0),
    'd' to listOf(0, 1, 1, 1, 1, 0, 1),
    'e' to listOf(1, 0, 0, 1, 1, 1, 1),
    'f' to listOf(1, 0, 0, 0, 1, 1, 1),
    'g' to listOf(1, 0, 1, 1, 1, 1, 0),
    'H' to listOf(0, 1, 1, 0, 1, 1, 1),
    'h' to listOf(0, 0, 1, 0, 1, 1, 1),
    'i' to listOf(0, 0, 0, 0, 1, 1, 0),
    'j' to listOf(0, 1, 1, 1, 1, 0, 0),
    'k' to listOf(0, 1, 0, 0, 1, 1, 1),
    'l' to listOf(0, 0, 0, 1, 1, 1, 0),
    'n' to listOf(0, 0, 1, 0, 1, 0, 1),
    'o' to listOf(0, 0, 1, 1, 1, 0, 1),
    'O' to listOf(1, 1, 1, 1, 1, 1, 0),
    'p' to listOf(1, 1, 0, 0, 1, 1, 1),
    'q' to listOf(1, 1, 1, 0, 0, 1, 1),
    'r' to listOf(0, 0, 0, 0, 1, 0, 1),
    's' to listOf(1, 0, 1, 1, 0, 1, 1),
    't' to listOf(0, 0, 0, 1, 1, 1, 1),
    'u' to listOf(0, 0, 1, 1, 1, 0, 0),
    'U' to listOf(0, 1, 1, 1, 1, 1, 0),
    'v' to listOf(0, 0, 1, 1, 1, 1, 0),
    'x' to listOf(0, 0, 1, 0, 0, 1, 1),
    'y' to listOf(0, 1, 1, 1, 0, 1, 1),
    'z' to listOf(1, 1, 0, 1, 1, 0, 1),
    '_' to listOf(0, 0, 0, 1, 0, 0, 0),
    '-' to listOf(0, 0, 0, 0, 0, 0, 1),
    '=' to listOf(0, 0, 0, 1, 0, 0, 1),
)

/**
 * Creates a character display. See [LEDCharDisplay] for more info.
 * @param pins Specify the GPIO pins that the multi-segment display is attached to. Pins should be in the
 * LED segment order A, B, C, D, E, F, G, and will be named automatically by the class.
 * If a decimal-point pin is present, specify it separately as the dp parameter.
 * @property dp If a decimal-point segment is present, specify it as this named parameter.
 * @property font A mapping of values (typically characters, but may also be numbers) to tuples of LED states.
 * See [LEDCharDisplay.font] for more info.
 * @property activeHigh See [LED.activeHigh].
 * @param initialValue the initial [LEDCharDisplay.value] of the graph given as a [Char], defaults to space `' '`
 * which typically maps to all LEDs being inactive.
 */
fun Gpio.ledCharDisplay(
    vararg pins: GpioPin,
    dp: GpioPin? = null,
    font: Map<Char, List<Int>>? = null,
    activeHigh: Boolean = true,
    initialValue: Char = ' '
) = LEDCharDisplay(this, pins.toList(), dp, font, activeHigh, initialValue)
