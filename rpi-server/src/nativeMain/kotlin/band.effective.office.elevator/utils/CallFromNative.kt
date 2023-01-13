package band.effective.office.elevator.utils

import kotlinx.cinterop.CPointer
import kotlinx.cinterop.refTo
import kotlinx.cinterop.toKString
import platform.posix.*

fun executeCommand(command: String): String{
    val fp: CPointer<FILE>? = popen(command, "r")
    val buffer = ByteArray(4096)
    val returnString = StringBuilder()

    /* Open the command for reading. */
    if (fp == NULL) {
        printf("Failed to run command\n" )
        exit(1)
    }

    /* Read the output a line at a time - output it. */
    var scan = fgets(buffer.refTo(0), buffer.size, fp)
    if(scan != null) {
        while (scan != NULL) {
            returnString.append(scan!!.toKString())
            scan = fgets(buffer.refTo(0), buffer.size, fp)
        }
    }
    /* close */
    pclose(fp)
    return returnString.trim().toString()
}

fun executeCommand2(
    command: String,
    trim: Boolean = true,
    redirectStderr: Boolean = true
): String {
    val commandToExecute = if (redirectStderr) "$command 2>&1" else command
    val fp = popen(commandToExecute, "r") ?: error("Failed to run command: $command")

    val stdout = buildString {
        val buffer = ByteArray(4096)
        while (true) {
            val input = fgets(buffer.refTo(0), buffer.size, fp) ?: break
            append(input.toKString())
        }
    }

    val status = pclose(fp)
    if (status != 0) {
        error("Command `$command` failed with status $status${if (redirectStderr) ": $stdout" else ""}")
    }

    return if (trim) stdout.trim() else stdout
}