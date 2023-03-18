/**
 * A basic collection of LEDs. See [LEDBoard] and [LEDBarGraph] for concrete implementations.
 */
interface LEDCollection : CompositeOutputDevice<LED> {
    /**
     * All leds contained within this collection.
     */
    val leds: List<LED>
        get() = devices

    /**
     * The number of LEDs on the bar graph actually lit up.
     */
    val litCount: Int
        get() = leds.count { it.isLit }

    /**
     * The number of LEDs controlled by this bar.
     */
    val count: Int get() = leds.size

    /**
     * Returns true if any of the LEDs is lit. Setting this to true or false
     * will turn on or off all LEDs.
     */
    var isLit: Boolean
        get() = leds.any { it.isLit }
        set(value) {
            leds.forEach { it.isLit = value }
        }
}
