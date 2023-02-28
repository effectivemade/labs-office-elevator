import io.ktgp.Gpio
import io.ktgp.gpio.Bias
import io.ktgp.gpio.Gpio
import io.ktgp.use
import io.ktgp.util.sleep

fun main() {
    println("Hello, Kotlin/Native!")
    // you'll probably need to run this app with sudo, in order to be able to access
    // /dev/mem and /dev/gpiomem. See
    // https://raspberrypi.stackexchange.com/questions/40105/access-gpio-pins-without-root-no-access-to-dev-mem-try-running-as-root
    // for more details.
    Gpio().use { gpio ->
        // remember that the pin parameter of the output() function refers
        // to the GPIO number. E.g. passing in 17 will target GPIO17 which is
        // pin number 11 (at least on on Raspberry PI 3B).
        // See https://www.raspberrypi.org/documentation/computers/os.html#gpio-pinout for more details.
        //
        ledExample(gpio)
//        buttonExample(gpio)
//        buttonExample2(gpio)
//        example1(gpio)
//        example2(gpio)
//        example3(gpio)
//        remoteControlledCar(gpio)
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

/**
 * [Button example](https://gpiozero.readthedocs.io/en/stable/recipes.html#button)
 */
private fun buttonExample(gpio: Gpio) {
    val pin = 2
    println("Press the button on GPIO$pin 3 times")
    var cont = 0
    gpio.buttonListen(pin) {
        println("Pressed")
        cont++ < 3
    }
    println("Done")
}

/**
 * [Button example](https://gpiozero.readthedocs.io/en/stable/recipes.html#button)
 */
private fun buttonExample2(gpio: Gpio) {
    gpio.button(2).use { button ->
        println("Dumping $button state for 10 seconds")
        repeat(100) {
            print(if (button.isActive) 'x' else '.')
            sleep(100)
        }
    }
    println("\n\nDone")
}

/**
 * We're going to control three LEDs on adjacent GPIO ports: 17, 27 and 22.
 */
private fun example1(gpio: Gpio) {
    gpio.ledBarGraph(17, 27, 22).use { leds ->
        // it's probably a good idea to hook the LED to the pin first,
        // before setting it to HIGH...?
        println(leds)
        repeat(10) {
            leds.litCountRange.forEach {
                leds.litCount = it
                sleep(200)
            }
        }
    }
}

/**
 * Reads a text from stdin and animates it on a 7-segment character display.
 */
private fun example2(gpio: Gpio) {
    println("Type in text to display, then press Enter. Empty line ends the program.")
    gpio.ledCharDisplay(17, 27, 22, 13, 19, 26, 6, activeHigh = false).use { leds ->
        stdinLines().forEach {
            leds.show(it)
            println(leds)
        }
    }
}

private fun example3(gpio: Gpio) {
    gpio.robot(ForwardBackwardPin(14, 15), ForwardBackwardPin(3, 2)).use {
        println(it)
        it.forward()
        println(it)
        sleep(1000)
        it.reverse()
        println(it)
        sleep(1000)
        it.stop()
        println(it)
        sleep(1000)
    }
}

/**
 * ssh into your RPI, then run this program, then type in commands :-D
 */
private fun remoteControlledCar(gpio: Gpio) {
    gpio.robot(ForwardBackwardPin(3, 2), ForwardBackwardPin(14, 15)).use { car ->
        println("Type in program. D - drive, C - cuvaj, R - vpravo, L - vlavo, S - stop. Blank line terminates the program")
        stdinLines().forEach { line ->
            try {
                line.forEach { command ->
                    when (command.lowercaseChar()) {
                        'd' -> car.forward()
                        'c' -> car.backward()
                        'r' -> car.rightHalf()
                        'l' -> car.leftHalf()
                        's' -> car.stop()
                        else -> println("Unknown command: $command")
                    }
                    sleep(300)
                }
            } finally {
                car.stop()
            }
        }
    }
}
