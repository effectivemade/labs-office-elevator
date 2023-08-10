package office.effective.features.user.routes.swagger

import io.github.smiley4.ktorswaggerui.dsl.OpenApiRoute
import io.ktor.http.*
import office.effective.common.swagger.SwaggerDocument
import office.effective.features.booking.dto.BookingDTO
import office.effective.features.user.dto.IntegrationDTO
import office.effective.features.user.dto.UserDTO

fun SwaggerDocument.returnUserByEmail(): OpenApiRoute.() -> Unit = {
    description = "Return user by email"
    tags = listOf("userTests")
    request {
        pathParameter<String>("email") {
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
                        avatarUrl = "https://img.freepik.com/free-photo/beautiful-shot-of-a-white-british-shorthair-kitten_181624-57681.jpg",
                        integrations = listOf(
                            IntegrationDTO(
                                "13c80c3d-4278-45cf-8d2a-e281004d3ff9",
                                "email",
                                "123@effective.band"
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
            description = "User with this email was not found"
        }
    }
}

fun SwaggerDocument.returnUsers(): OpenApiRoute.() -> Unit = {
    description = "Return all users by Tag name"
    tags = listOf("users")
    request{
        queryParameter<String>("tag"){
            description = "Name of the tag"
            example = "emploee"
            required = true
            allowEmptyValue = false
        }

    }
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
                            avatarUrl = "https://img.freepik.com/free-photo/beautiful-shot-of-a-white-british-shorthair-kitten_181624-57681.jpg",
                            integrations = listOf(
                                IntegrationDTO(
                                    "13c80c3d-4278-45cf-8d2a-e281004d3ff9",
                                    "email",
                                    "123@effective.band"
                                )
                            )
                        ),
                        UserDTO(
                            id = "207b9634-2bc4-11ee-be56-0242ac120002",
                            fullName = "Lol Kekov",
                            active = true,
                            role = "USER",
                            avatarUrl = "https://img.freepik.com/free-photo/beautiful-shot-of-a-white-british-shorthair-kitten_181624-57681.jpg",
                            integrations = listOf(
                                IntegrationDTO(
                                    "13c80c3d-4278-45cf-8d2a-e281004d3ff9",
                                    "email",
                                    "321@effective.band"
                                )
                            )
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
        pathParameter<String>("user_id") {
            description = "User id"
            example = "87e66ee0-2550-4188-8d79-75560125836a"
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
                        avatarUrl = "https://img.freepik.com/free-photo/beautiful-shot-of-a-white-british-shorthair-kitten_181624-57681.jpg",
                        integrations = listOf(
                            IntegrationDTO(
                                "13c80c3d-4278-45cf-8d2a-e281004d3ff9",
                                "email",
                                "123@effective.band"
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
            description = "User with this id was not found"
        }
    }
}

fun SwaggerDocument.updateUser(): OpenApiRoute.() -> Unit = {
    description = "Changes user by id"
    tags = listOf("users")
    request {
        body<UserDTO> {
            example(
                "User",
                UserDTO(
                    id = "87e66ee0-2550-4188-8d79-75560125836a",
                    fullName = "I'm a geno-modified GenaCrocodile",
                    active = true,
                    role = "mimocrocodile",
                    avatarUrl = "https://img.freepik.com/free-photo/beautiful-shot-of-a-white-british-shorthair-kitten_181624-57681.jpg",
                    integrations = listOf(
                        IntegrationDTO(
                            "13c80c3d-4278-45cf-8d2a-e281004d3ff9",
                            "email",
                            "0987654321@gmail.com"
                        ), IntegrationDTO(
                            "52a3a1e0-21e1-4e76-b8c3-4b5ae7022aab",
                            "telegraph",
                            "@0987654321"
                        )
                    )
                )
            )
        }
    }
    response {
        HttpStatusCode.OK to {
            description = "Return users dto"
            body<UserDTO> {
                example(
                    "User",
                    UserDTO(
                        id = "87e66ee0-2550-4188-8d79-75560125836a",
                        fullName = "I'm a geno-modified GenaCrocodile",
                        active = true,
                        role = "mimocrocodile",
                        avatarUrl = "https://img.freepik.com/free-photo/beautiful-shot-of-a-white-british-shorthair-kitten_181624-57681.jpg",
                        integrations = listOf(
                            IntegrationDTO(
                                "13c80c3d-4278-45cf-8d2a-e281004d3ff9",
                                "email",
                                "0987654321@gmail.com"
                            ), IntegrationDTO(
                                "52a3a1e0-21e1-4e76-b8c3-4b5ae7022aab",
                                "telegraph",
                                "@0987654321"
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
            description = "User with this id was not found"
        }
    }
}
