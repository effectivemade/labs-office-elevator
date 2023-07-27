package office.effective.features.workspace.routes.swagger

import io.github.smiley4.ktorswaggerui.dsl.OpenApiRoute
import io.ktor.http.*
import office.effective.common.swagger.SwaggerDocument
import office.effective.features.workspace.dto.UtilityDTO
import office.effective.features.workspace.dto.WorkspaceDTO

fun SwaggerDocument.returnWorkspaceById(): OpenApiRoute.() -> Unit = {
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
                    "Workspaces", WorkspaceDTO(
                        id = "2561471e-2bc6-11ee-be56-0242ac120002", name = "Sun", utilities = listOf(
                            UtilityDTO(
                                id = "50d89406-2bc6-11ee-be56-0242ac120002",
                                name = "Sockets",
                                iconUrl = "https://img.freepik.com/free-photo/beautiful-shot-of-a-white-british-shorthair-kitten_181624-57681.jpg",
                                count = 8
                            ), UtilityDTO(
                                id = "a62a86c6-2bc6-11ee-be56-0242ac120002",
                                name = "Projectors",
                                iconUrl = "https://img.freepik.com/free-photo/beautiful-shot-of-a-white-british-shorthair-kitten_181624-57681.jpg",
                                count = 1
                            )
                        )
                    )
                ) {}
            }
        }
        HttpStatusCode.BadRequest to {
            description = "Bad request"
        }
        HttpStatusCode.NotFound to {
            description = "Workspace with this id was not found"
        }
    }
}

fun SwaggerDocument.returnWorkspaceByTag(): OpenApiRoute.() -> Unit = {
    description = "Return all workspaces by tag"
    tags = listOf("workspaces")
    request {
        queryParameter<WorkspaceTag>("tag") {
            description = "Workspace tag"
            example = "meeting"
            required = true
            allowEmptyValue = false
        }
    }
    response {
        HttpStatusCode.OK to {
            description = "Returns workspace found by tag"
            body<List<WorkspaceDTO>> {
                example(
                    "Workspace", listOf(
                        WorkspaceDTO(
                            id = "2561471e-2bc6-11ee-be56-0242ac120002", name = "Sun", utilities = listOf(
                                UtilityDTO(
                                    id = "50d89406-2bc6-11ee-be56-0242ac120002",
                                    name = "Sockets",
                                    iconUrl = "https://img.freepik.com/free-photo/beautiful-shot-of-a-white-british-shorthair-kitten_181624-57681.jpg",
                                    count = 8
                                ), UtilityDTO(
                                    id = "a62a86c6-2bc6-11ee-be56-0242ac120002",
                                    name = "Projectors",
                                    iconUrl = "https://img.freepik.com/free-photo/beautiful-shot-of-a-white-british-shorthair-kitten_181624-57681.jpg",
                                    count = 1
                                )
                            )
                        ), WorkspaceDTO(
                            id = "2561471e-2bc6-11ee-be56-0242ac120002", name = "Moon", utilities = listOf(
                                UtilityDTO(
                                    id = "50d89406-2bc6-11ee-be56-0242ac120002",
                                    name = "Sockets",
                                    iconUrl = "https://img.freepik.com/free-photo/beautiful-shot-of-a-white-british-shorthair-kitten_181624-57681.jpg",
                                    count = 5
                                ),
                            )
                        )
                    )
                ) {}
            }
        }
        HttpStatusCode.NotFound to {
            description = "Workspace with this tag was not found"
        }
    }
}

enum class WorkspaceTag(val tagName: String) {
    meeting("meeting"), regular("regular")
}
