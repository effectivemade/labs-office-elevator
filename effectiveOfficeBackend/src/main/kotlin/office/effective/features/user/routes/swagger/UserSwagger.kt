package office.effective.features.user.routes.swagger

import io.github.smiley4.ktorswaggerui.dsl.OpenApiRoute
import io.ktor.http.*
import office.effective.common.swagger.SwaggerDocument
import office.effective.features.user.dto.UserDTO

fun SwaggerDocument.returnUserByEmail(): OpenApiRoute.() -> Unit = {
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
}

fun SwaggerDocument.returnUsers(): OpenApiRoute.() -> Unit = {
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
}

fun SwaggerDocument.returnUserById(): OpenApiRoute.() -> Unit = {
    description = "Return user by id"
    tags = listOf("users")
    request {
        queryParameter<String>("id") {
            description = "User id"
            example = "2561471e-2bc6-11ee-be56-0242ac120002"
            required = true
            allowEmptyValue = false
        }
    }
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
}

fun SwaggerDocument.alterUser(): OpenApiRoute.() -> Unit = {
    description = "Change user in db"
    tags = listOf("users")
    response {
        HttpStatusCode.OK to {
            description = "Return users dto"
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
}
