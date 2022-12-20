package band.effective.office.elevator.websocket.client.plugins

import band.effective.office.elevator.websocket.client.utils.DateUtils
import band.effective.office.elevator.websocket.client.utils.ElevatorController
import band.effective.office.elevator.websocket.client.utils.toGMTDate
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {

    routing {
        get("/") {
            call.respond("Raspberry Client API")
        }

        post("/office-elevator") {
            val time = call.request.queryParameters["time"]
            when {
                call.request.queryParameters["command"] != "go" -> call.respond(HttpStatusCode.NotFound)
                !DateUtils.isCorrectTime(time?.toGMTDate()) -> call.respond(
                    HttpStatusCode.Forbidden, "Incorrect request time",
                )
                else -> {
                    call.respond(HttpStatusCode.OK, "Elevator called")
                    ElevatorController.call()
                }
            }
        }
    }
}