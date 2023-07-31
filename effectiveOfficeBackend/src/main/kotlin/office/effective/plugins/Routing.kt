package office.effective.plugins

import authRoutingFun
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import office.effective.features.user.routes.userRouting
import office.effective.features.booking.routes.bookingRouting
import office.effective.features.workspace.routes.workspaceRouting

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Hello World!")
        }
        authRoutingFun()
        workspaceRouting()
        userRouting()
        bookingRouting()
    }

}
