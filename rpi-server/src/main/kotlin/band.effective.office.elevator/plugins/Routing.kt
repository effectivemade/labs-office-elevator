package band.effective.office.elevator.plugins

import band.effective.office.common.utils.DateUtils
import band.effective.office.common.utils.HashUtil
import band.effective.office.common.utils.toGMTDate
import band.effective.office.common.utils.toVerifiableDate
import band.effective.office.elevator.utils.ElevatorController
import band.effective.office.elevator.utils.PropertiesUtil
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import io.ktor.util.date.GMTDate


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

                token != HashUtil.sha256(
                    value = GMTDate().toVerifiableDate(),
                    password = PropertiesUtil.read("password")
                ) -> {
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