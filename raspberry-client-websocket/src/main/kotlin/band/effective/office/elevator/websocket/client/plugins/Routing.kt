package band.effective.office.elevator.websocket.client.plugins

import band.effective.office.elevator.websocket.client.utils.*
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
            Logger.debug("time: $time") // TODO hide logs for PROD
            val key = call.request.queryParameters["key"]

            Logger.debug("retrieved hash: $key")

            when {
                call.request.queryParameters["command"] != "go" -> call.respond(HttpStatusCode.NotFound)
                !DateUtils.isCorrectTime(time?.parseStringToISO8061Date()) -> call.respond(HttpStatusCode.Forbidden) // TODO привести к гринвичу
                !key.equals(
                    HashUtil.sha256(
                        time
                    ).apply { Logger.debug("server-side hash code: $this") }
                ) -> call.respond(HttpStatusCode.Forbidden, "Incorrect hash code")

                else -> {
                    call.respond(HttpStatusCode.OK, "Elevator called")
                    ElevatorController.call()
                }
            }
        }
    }
}