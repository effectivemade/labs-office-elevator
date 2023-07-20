package office.effective.plugins

import authRoutingFun
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import office.effective.features.workspace.routes.workspaceRouting

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Hello World!")
        }
        authRoutingFun()
        workspaceRouting()
    }

}
