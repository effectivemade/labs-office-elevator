package office.effective.features.workspace.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import office.effective.features.workspace.facade.WorkspaceFacade
import org.koin.core.context.GlobalContext

fun Route.workspaceRouting() {
    route("/workspace") {
        val facade: WorkspaceFacade = GlobalContext.get().get()

        get("{id?}") {
            val id: String = call.parameters["id"]
                ?: return@get call.respond(HttpStatusCode.BadRequest)

            call.respond(facade.findById(id))
        }
        get {
            val tag: String = call.request.queryParameters["tag"]
                ?: return@get call.respond(HttpStatusCode.BadRequest)

            call.respond(facade.findAllByTag(tag))
        }
    }
}