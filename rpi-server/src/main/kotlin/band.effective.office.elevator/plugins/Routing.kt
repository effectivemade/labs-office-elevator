package band.effective.office.elevator.plugins

import band.effective.office.elevator.utils.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.date.*

fun Application.configureRouting() {

    routing {
        get("/") {
            call.respond("Raspberry Client API")
        }

        post("/office-elevator") {
            val time = call.request.queryParameters["time"]?.toGMTDate()
            val token = call.request.queryParameters["token"]

            when {
                call.request.queryParameters["command"] != "go" -> call.respond(HttpStatusCode.NotFound).apply {
                    println("""[NotFound]: queryParameters["command"] != "go" """)
                }

                !DateUtils.isCorrectTime(time) -> {
                    call.respond(
                        HttpStatusCode.Forbidden, "Incorrect request time",
                    ).apply {
                        println("[Forbidden]: Incorrect request time")
                    }
                }

                token != HashUtil.sha256(GMTDate().toVerifiableDate()) -> {
                    call.respond(
                        HttpStatusCode.Forbidden,
                        "Verification token is incorrect"
                    ).apply {
                        println("[Forbidden]: Verification token is incorrect")
                    }
                }

                else -> {
                    ElevatorController.call()
                    call.respond(HttpStatusCode.OK, "Elevator called")
                }
            }
        }
    }
}