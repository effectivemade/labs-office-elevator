import io.ktgp.Closeable
import platform.posix.fflush
import platform.posix.fprintf

private val STDERR = platform.posix.fdopen(2, "w")

/**
 * Prints a message to stderr. Appends a newline automatically
 */
fun printErr(message: String) {
    fprintf(STDERR, message)
    fprintf(STDERR, "\n")
    fflush(STDERR)
}

/**
 * Closes quietly - catches any exception and prints it to stderr.
 */
fun Closeable.closeQuietly() {
    try {
        close()
    } catch (t: Throwable) {
        println()
        t.printStackTrace()
    }
}

/**
 * Reads lines from stdin. The sequence ends once a blank line is received (user
 * simply presses Enter without typing anything).
 */
internal fun stdinLines(): Sequence<String> = generateSequence {
    var line = readLine(); if (line.isNullOrBlank()) line = null; line
}
