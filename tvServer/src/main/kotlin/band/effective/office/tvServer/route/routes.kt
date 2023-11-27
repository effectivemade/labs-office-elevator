package band.effective.office.tvServer.route

import band.effective.office.tvServer.route.leader.leaderId
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.routes() {
    routing {
        leaderId()
    }
}