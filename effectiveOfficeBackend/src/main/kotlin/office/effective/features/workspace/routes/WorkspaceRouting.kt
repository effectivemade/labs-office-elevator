package office.effective.features.workspace.routes

import io.github.smiley4.ktorswaggerui.dsl.get
import io.github.smiley4.ktorswaggerui.dsl.route
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import office.effective.common.swagger.SwaggerDocument
import office.effective.features.workspace.facade.WorkspaceFacade
import office.effective.features.workspace.routes.swagger.returnWorkspaceById
import office.effective.features.workspace.routes.swagger.returnWorkspaceByTag
import org.koin.core.context.GlobalContext

fun Route.workspaceRouting() {
    route("/workspaces") {
        val facade: WorkspaceFacade = GlobalContext.get().get()

        get("/{id}", SwaggerDocument.returnWorkspaceById()) {
            val id: String = call.parameters["id"] ?: return@get call.respond(HttpStatusCode.BadRequest)
            call.respond(facade.findById(id))
        }
        get("/{tag}", SwaggerDocument.returnWorkspaceByTag()) {
            val tag: String = call.request.queryParameters["tag"] ?: return@get call.respond(HttpStatusCode.BadRequest)

            call.respond(facade.findAllByTag(tag))
        }
    }
}
