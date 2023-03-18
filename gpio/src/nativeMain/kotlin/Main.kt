import io.ktgp.Gpio
import io.ktgp.gpio.Gpio
import io.ktgp.use

fun main() {
    // you'll probably need to run this app with sudo, in order to be able to access
    // /dev/mem and /dev/gpiomem. See
    // https://raspberrypi.stackexchange.com/questions/40105/access-gpio-pins-without-root-no-access-to-dev-mem-try-running-as-root
    // for more details.
    Gpio().use { gpio ->
        ledExample(gpio)
    }
}

/**
 * [LED example](https://gpiozero.readthedocs.io/en/stable/recipes.html#led)
 */
private fun ledExample(gpio: Gpio) {
    gpio.led(17).use { led ->
        println("Blinking $led")
        led.blink()
    }
}