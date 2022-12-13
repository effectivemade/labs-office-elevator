package band.effective.office.elevator.websocket.server.plugins

import band.effective.office.elevator.websocket.server.client.ktorClient
import band.effective.office.elevator.websocket.server.utils.HashUtil
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.time.LocalDateTime

fun Application.configureRouting() {
    routing {

        get("/") {
            call.respond("Server API")
        }

        get("/elevate") {
            val request = ktorClient.post {
                url {
                    path("office-elevator")
                    parameters.append(
                        "command", "go"
                    )

                    val currentTime = LocalDateTime.now().toString()
                    parameters.append(
                        "time", currentTime
                    )

                    parameters.append(
                        "key", HashUtil.sha256(currentTime)
                    )
                }
            }
            when (request.status.value) {
                in 200..299 -> {
                    call.respond(HttpStatusCode.OK, "Success")
                }

                404 -> {
                    call.respond(HttpStatusCode.NotFound, "Not found")
                }

                403 -> {
                    call.respond(HttpStatusCode.Forbidden, "Access denied")
                }

                500 -> {
                    call.respond(HttpStatusCode.InternalServerError, "Internal server error")
                }
            }
        }
    }
}