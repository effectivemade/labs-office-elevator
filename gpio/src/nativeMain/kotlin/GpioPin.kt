/**
 * Represents a Raspberry PI GPIO pin. This number doesn't correspond to the physical
 * pin number. For example, GPIO pin 17 (GPIO17) is physical
 * pin number 11 (at least on on Raspberry PI 3B).
 * See https://www.raspberrypi.org/documentation/computers/os.html#gpio-pinout for more details.
 */
typealias GpioPin = Int

internal fun GpioPin.requireInGpioRange() {
    require(this in 0..27) { "Invalid gpio number $this: must be 0..27" }
}
