package office.effective.features.user.routes

import io.github.smiley4.ktorswaggerui.dsl.get
import io.github.smiley4.ktorswaggerui.dsl.put
import io.github.smiley4.ktorswaggerui.dsl.route
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import office.effective.common.swagger.SwaggerDocument
import office.effective.features.user.ITokenVerifier
import office.effective.features.user.converters.UserDTOModelConverter
import office.effective.features.user.dto.UserDTO
import office.effective.features.user.facade.UserFacade
import office.effective.features.user.repository.UserRepository
import office.effective.features.user.routes.swagger.updateUser
import office.effective.features.user.routes.swagger.returnUserByEmail
import office.effective.features.user.routes.swagger.returnUserById
import office.effective.features.user.routes.swagger.returnUsers
import org.koin.core.context.GlobalContext

fun Route.userRouting() {
    val facade: UserFacade = GlobalContext.get().get()
    val verifier: ITokenVerifier = GlobalContext.get().get()

    route("userTests", {
    }) {
        get("/{email}", SwaggerDocument.returnUserByEmail()) {
            val repo: UserRepository = GlobalContext.get().get()
            val model =
                repo.findByEmail(
                    call.parameters["email"] ?: return@get call.respond(HttpStatusCode.BadRequest)
                )
            val converterDTO: UserDTOModelConverter = GlobalContext.get().get()
            call.respond(converterDTO.modelToDTO(model))
        }
    }
    route("users", {}) {
        get("", SwaggerDocument.returnUsers()) {
            var tagStr = call.request.queryParameters["tag"] ?: return@get call.respond(HttpStatusCode.BadRequest)
            val users: Set<UserDTO>? = facade.getUsersByTag(tagStr as String, "tokenStr as String")
            call.respond(users ?: "no such users")
        }
        get("/{user_id}", SwaggerDocument.returnUserById()) {
            val userId : String = call.parameters["user_id"] ?: return@get call.respond(HttpStatusCode.BadRequest)
            val user = facade.getUserById(userId, "zsd")
            call.respond(user)
        }

        put("/{user_id}", SwaggerDocument.updateUser()) {
            val user: UserDTO = call.receive<UserDTO>()
            call.respond(facade.updateUser(user))
        }
    }
}