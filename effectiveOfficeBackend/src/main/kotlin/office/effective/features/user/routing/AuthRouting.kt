import io.github.smiley4.ktorswaggerui.dsl.get
import io.github.smiley4.ktorswaggerui.dsl.route
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

import office.effective.features.user.ITokenVerifier
import office.effective.features.user.converters.UserDTOModelConverter
import office.effective.features.user.dto.UserDTO
import office.effective.features.user.repository.UserRepository
import office.effective.features.user.service.IUserService
import org.koin.core.context.GlobalContext

fun Route.authRoutingFun() {
    val service: IUserService = GlobalContext.get().get()
    val verifier: ITokenVerifier = GlobalContext.get().get()

    route("userTests", {
    }) {
        get("/{email}", {
            description = "Return user by email"
            tags = listOf("userTests")
            request {
                queryParameter<String>("email") {
                    description = "User`s email"
                    example = "user@effective.band"
                    required = true
                    allowEmptyValue = false
                }
            }
            response {
                HttpStatusCode.OK to {
                    description = "Return user found by email"
                    body<UserDTO> {
                        example(
                            "User",
                            UserDTO(
                                id = "2c77feee-2bc1-11ee-be56-0242ac120002",
                                fullName = "Ivan Ivanov",
                                active = true,
                                role = "ADMIN",
                                avatarUrl = "https://img.freepik.com/free-photo/beautiful-shot-of-a-white-british-shorthair-kitten_181624-57681.jpg"
                            )
                        ) {
                        }
                    }
                }
                HttpStatusCode.BadRequest to {
                    description = "Bad request"
                }
                HttpStatusCode.NotFound to {
                    description = "User with this email was not found"
                }
            }
        }) {
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
        get("/all", {
            description = "Return all users"
            tags = listOf("users")
            response {
                HttpStatusCode.OK to {
                    description = "Return users list"
                    body<List<UserDTO>> {
                        example(
                            "Users",
                            listOf(
                                UserDTO(
                                    id = "2c77feee-2bc1-11ee-be56-0242ac120002",
                                    fullName = "Ivan Ivanov",
                                    active = true,
                                    role = "ADMIN",
                                    avatarUrl = "https://img.freepik.com/free-photo/beautiful-shot-of-a-white-british-shorthair-kitten_181624-57681.jpg"
                                ),
                                UserDTO(
                                    id = "207b9634-2bc4-11ee-be56-0242ac120002",
                                    fullName = "Lol Kekov",
                                    active = true,
                                    role = "USER",
                                    avatarUrl = "https://img.freepik.com/free-photo/beautiful-shot-of-a-white-british-shorthair-kitten_181624-57681.jpg"
                                )
                            )
                        ) {
                        }
                    }
                }
            }
        }) {
            var tagStr = call.request.queryParameters["tag"] ?: call.response.status(HttpStatusCode.BadRequest)
            val tokenStr = call.request.header("id_token") ?: call.response.status(HttpStatusCode.Forbidden)
            val users: Set<UserDTO>? = service.getUsersByTag(tagStr as String, tokenStr as String)
            call.respond(users ?: "no such users")
        }
        get("/{user_id}", {
            description = "Return user by id"
            tags = listOf("users")
            response {
                HttpStatusCode.OK to {
                    description = "Returns user found by id"
                    body<UserDTO> {
                        example(
                            "User",
                            UserDTO(
                                id = "2c77feee-2bc1-11ee-be56-0242ac120002",
                                fullName = "Ivan Ivanov",
                                active = true,
                                role = "ADMIN",
                                avatarUrl = "https://img.freepik.com/free-photo/beautiful-shot-of-a-white-british-shorthair-kitten_181624-57681.jpg"
                            )
                        ) {
                        }
                    }
                }
                HttpStatusCode.BadRequest to {
                    description = "Bad request"
                }
                HttpStatusCode.NotFound to {
                    description = "User with this id was not found"
                }
            }
        }) {
            val userId = call.parameters["user_id"] ?: call.response.status(HttpStatusCode.BadRequest)
            val tokenStr = call.request.header("id_token") ?: call.response.status(HttpStatusCode.Forbidden)
            val user = service.getUserById(userId as String, tokenStr as String)
            call.respond(user ?: "No such user. Suggestion: bad id")
        }
    }


}
