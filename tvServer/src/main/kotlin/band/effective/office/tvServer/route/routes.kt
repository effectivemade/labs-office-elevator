package band.effective.office.tvServer.route

import band.effective.office.tvServer.route.leader.leaderId
import band.effective.office.tvServer.route.mattermost.mattermost
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.routes() {
    routing {
        leaderId()
        mattermost()
    }

}