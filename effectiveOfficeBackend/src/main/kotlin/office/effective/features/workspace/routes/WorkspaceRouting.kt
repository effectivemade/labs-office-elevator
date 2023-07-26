package office.effective.features.workspace.routes

import io.github.smiley4.ktorswaggerui.dsl.get
import io.github.smiley4.ktorswaggerui.dsl.route
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import office.effective.features.user.dto.UserDTO
import office.effective.features.workspace.dto.UtilityDTO
import office.effective.features.workspace.dto.WorkspaceDTO
import office.effective.features.workspace.facade.WorkspaceFacade
import org.koin.core.context.GlobalContext
import kotlin.text.get

fun Route.workspaceRouting() {
    route("/workspaces", {}) {
        val facade: WorkspaceFacade = GlobalContext.get().get()
        get("/{id?}", {
            description = "Return workspace by id"
            tags = listOf("workspaces")
            request {
                queryParameter<String>("id") {
                    description = "Workspace id"
                    example = "2561471e-2bc6-11ee-be56-0242ac120002"
                    required = true
                    allowEmptyValue = false
                }
            }
            response {
                HttpStatusCode.OK to {
                    description = "Returns workspace found by id"
                    body<WorkspaceDTO> {
                        example(
                            "Workspaces",
                            WorkspaceDTO(
                                id = "2561471e-2bc6-11ee-be56-0242ac120002",
                                name = "Sun",
                                utilities = listOf(
                                    UtilityDTO(
                                        id = "50d89406-2bc6-11ee-be56-0242ac120002",
                                        name = "Sockets",
                                        iconUrl = "https://img.freepik.com/free-photo/beautiful-shot-of-a-white-british-shorthair-kitten_181624-57681.jpg",
                                        count = 8
                                    ),
                                    UtilityDTO(
                                        id = "a62a86c6-2bc6-11ee-be56-0242ac120002",
                                        name = "Projectors",
                                        iconUrl = "https://img.freepik.com/free-photo/beautiful-shot-of-a-white-british-shorthair-kitten_181624-57681.jpg",
                                        count = 1
                                    )
                                )
                            )
                        ) {
                        }
                    }
                }
                HttpStatusCode.BadRequest to {
                    description = "Bad request"
                }
                HttpStatusCode.NotFound to {
                    description = "Workspace with this id was not found"
                }
            }
        }) {
            val id: String = call.parameters["id"]
                ?: return@get call.respond(HttpStatusCode.BadRequest)

            call.respond(facade.findById(id))
        }
        get("/all", {
            description = "Return all workspaces"
            tags = listOf("workspaces")
            response {
                HttpStatusCode.OK to {
                    description = "Returns workspace found by id"
                    body<List<WorkspaceDTO>> {
                        example(
                            "Workspace",
                            listOf(
                                WorkspaceDTO(
                                    id = "2561471e-2bc6-11ee-be56-0242ac120002",
                                    name = "Sun",
                                    utilities = listOf(
                                        UtilityDTO(
                                            id = "50d89406-2bc6-11ee-be56-0242ac120002",
                                            name = "Sockets",
                                            iconUrl = "https://img.freepik.com/free-photo/beautiful-shot-of-a-white-british-shorthair-kitten_181624-57681.jpg",
                                            count = 8
                                        ),
                                        UtilityDTO(
                                            id = "a62a86c6-2bc6-11ee-be56-0242ac120002",
                                            name = "Projectors",
                                            iconUrl = "https://img.freepik.com/free-photo/beautiful-shot-of-a-white-british-shorthair-kitten_181624-57681.jpg",
                                            count = 1
                                        )
                                    )
                                ),
                                WorkspaceDTO(
                                    id = "2561471e-2bc6-11ee-be56-0242ac120002",
                                    name = "Moon",
                                    utilities = listOf(
                                        UtilityDTO(
                                            id = "50d89406-2bc6-11ee-be56-0242ac120002",
                                            name = "Sockets",
                                            iconUrl = "https://img.freepik.com/free-photo/beautiful-shot-of-a-white-british-shorthair-kitten_181624-57681.jpg",
                                            count = 5
                                        ),
                                    )
                                )
                            )
                        ) {
                        }
                    }
                }
            }
        }) {
            val tag: String = call.request.queryParameters["tag"]
                ?: return@get call.respond(HttpStatusCode.BadRequest)

            call.respond(facade.findAllByTag(tag))
        }
    }
}