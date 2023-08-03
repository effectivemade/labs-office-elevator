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
import office.effective.features.user.repository.UserRepository
import office.effective.features.user.routes.swagger.alterUser
import office.effective.features.user.routes.swagger.returnUserByEmail
import office.effective.features.user.routes.swagger.returnUserById
import office.effective.features.user.routes.swagger.returnUsers
import office.effective.features.user.service.IUserService
import org.koin.core.context.GlobalContext

fun Route.userRouting() {
    val service: IUserService = GlobalContext.get().get()
    val verifier: ITokenVerifier = GlobalContext.get().get()

    route("userTests", {
    }) {
        get("/{email}", SwaggerDocument.returnUserByEmail()) {
            val repo: UserRepository = GlobalContext.get().get()
            val model =
                repo.findByEmail(
                    (call.parameters["email"] ?: call.response.status(HttpStatusCode.BadRequest)) as String
                )
            val converterDTO = UserDTOModelConverter()
            call.respond(converterDTO.modelToDTO(model))
        }
    }
    route("users", {}) {
        get("", SwaggerDocument.returnUsers()) {
            var tagStr = call.request.queryParameters["tag"] ?: call.response.status(HttpStatusCode.BadRequest)
            val tokenStr = call.request.header("id_token") ?: call.response.status(HttpStatusCode.Forbidden)
            val users: Set<UserDTO>? = service.getUsersByTag(tagStr as String, tokenStr as String)
            call.respond(users ?: "no such users")
        }
        get("/{user_id}", SwaggerDocument.returnUserById()) {
            val userId = call.parameters["user_id"] ?: call.response.status(HttpStatusCode.BadRequest)
            val tokenStr = call.request.header("id_token") ?: call.response.status(HttpStatusCode.Forbidden)
            val user = service.getUserById(userId as String, tokenStr as String)
            call.respond(user ?: "No such user. Suggestion: bad id")
        }

        put("/alter/{user_id}", SwaggerDocument.alterUser()) {
            val user: UserDTO = call.receive<UserDTO>()
            val tokenStr = call.request.header("id_token") ?: call.response.status(HttpStatusCode.Forbidden)
            call.respond(service.updateUser(user, tokenStr as String))
        }
    }
}