package band.effective.office.elevator.utils

fun executeCommand(command: String){
    Runtime.getRuntime().exec(command)
}