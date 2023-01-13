package band.effective.office.elevator.plugins

import band.effective.office.elevator.utils.DateUtils
import band.effective.office.elevator.utils.ElevatorController
import band.effective.office.elevator.utils.HashUtil
import band.effective.office.elevator.utils.toGMTDate
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.date.*

fun Application.configureRouting() {

    routing {
        get("/") {
            call.respond("Raspberry Client API")
            ElevatorController.call()
        }


        post("/office-elevator") {
            val time = call.request.queryParameters["time"]?.toGMTDate()
            val token = call.request.queryParameters["token"]

            when {
                call.request.queryParameters["command"] != "go" -> call.respond(HttpStatusCode.NotFound)
                !DateUtils.isCorrectTime(time) -> {
                    call.respond(
                        HttpStatusCode.Forbidden, "Incorrect request time",
                    )
                }

                token != HashUtil.sha256(GMTDate().toHttpDate()) -> {
                    call.respond(
                        HttpStatusCode.Forbidden,
                        "Verification token is incorrect"
                    )
                }

                else -> {
                    call.respond(HttpStatusCode.OK, "Elevator called")
                    ElevatorController.call()
                }
            }
        }
    }
}