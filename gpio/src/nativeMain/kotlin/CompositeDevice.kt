/**
 * Represents a device composed of multiple [devices] like simple HATs, H-bridge motor controllers,
 * robots composed of multiple motors, etc.
 */
interface CompositeDevice<D: Device> : Device {
    /**
     * The devices.
     */
    val devices: List<D>

    /**
     * Valid device indices range.
     */
    val indices: IntRange get() = devices.indices

    override fun close() {
        devices.forEach { it.closeQuietly() }
    }

    override val isClosed: Boolean
        get() = devices.any { it.isClosed }

    /**
     * Composite devices are considered “active” if any of their constituent devices is active.
     */
    override val isActive: Boolean
        get() = devices.any { it.isActive }
}

/**
 * Extends [CompositeDevice] with [on], [off], and [toggle] methods for controlling subordinate output devices.
 */
interface CompositeOutputDevice<D: OutputDevice> : CompositeDevice<D> {
    /**
     * Turn all the output devices off.
     */
    fun off() {
        devices.forEach { it.off() }
    }

    /**
     * Turn all the output devices on.
     */
    fun on() {
        devices.forEach { it.on() }
    }

    /**
     * Toggle all the output devices. For each device, if it’s on, turn it off; if it’s off, turn it on.
     */
    fun toggle() {
        devices.forEach { it.toggle() }
    }
}

/**
 * Creates a list of devices safely, one for each pin. If any of the device
 * fails to create, all already created devices are closed gracefully.
 */
fun <D: Device> buildDevicesSafely(block: MutableList<D>.() -> Unit) : List<D> {
    val list = mutableListOf<D>()
    var success = false
    try {
        block(list)
        success = true
        return list.toList()
    } finally {
        if (!success) {
            list.forEach { it.closeQuietly() }
        }
    }
}

/**
 * Creates a list of devices safely, one for each pin. If any of the device
 * fails to create, all already created devices are closed gracefully.
 * @param block called for each pin, to create a new device.
 * @return the devices created by [block], one for each pin.
 */
fun <D: Device> Iterable<GpioPin>.createDevicesSafely(block: (GpioPin) -> D) : List<D> =
    buildDevicesSafely {
        this@createDevicesSafely.toSet().forEach { add(block(it)) }
    }
