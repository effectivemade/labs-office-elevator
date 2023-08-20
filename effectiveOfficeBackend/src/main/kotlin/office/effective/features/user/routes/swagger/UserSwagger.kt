package office.effective.features.user.routes.swagger

import io.github.smiley4.ktorswaggerui.dsl.OpenApiRoute
import io.ktor.http.*
import office.effective.common.swagger.SwaggerDocument
import office.effective.dto.IntegrationDTO
import office.effective.dto.UserDTO

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
                        role = "Backend developer",
                        avatarUrl = "https://img.freepik.com/free-photo/beautiful-shot-of-a-white-british-shorthair-kitten_181624-57681.jpg",
                        integrations = listOf(
                            IntegrationDTO(
                                "c717cf6e-28b3-4148-a469-032991e5d9e9",
                                "phoneNumber",
                                "89236379887"
                            ), IntegrationDTO(
                                "8c02c6dc-1c4f-459e-ac27-f1db53923b2f",
                                "telegram",
                                "@cooldeveloper"
                            ), IntegrationDTO(
                                "e321f85c-3f85-4c23-8e59-05005a82ffa8",
                                "Foobr",
                                "CoolDev@foobr.ru"
                            )
                        ),
                        email = "cool.backend.developer@effective.band"
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
                            role = "Backend developer",
                            avatarUrl = "https://img.freepik.com/free-photo/beautiful-shot-of-a-white-british-shorthair-kitten_181624-57681.jpg",
                            integrations = listOf(
                                IntegrationDTO(
                                    "c717cf6e-28b3-4148-a469-032991e5d9e9",
                                    "phoneNumber",
                                    "89236379887"
                                ), IntegrationDTO(
                                    "8c02c6dc-1c4f-459e-ac27-f1db53923b2f",
                                    "telegram",
                                    "@cooldeveloper"
                                )
                            ),
                            email = "cool.backend.developer@effective.band"
                        ),
                        UserDTO(
                            id = "207b9634-2bc4-11ee-be56-0242ac120002",
                            fullName = "Danil Egorov",
                            active = true,
                            role = "Android developer",
                            avatarUrl = "https://img.freepik.com/free-photo/beautiful-shot-of-a-white-british-shorthair-kitten_181624-57681.jpg",
                            integrations = listOf(IntegrationDTO(
                                "c717cf6e-28b3-4148-a469-032991e5d9e9",
                                "phoneNumber",
                                "89038760982")
                            ),
                            email = "cool.android.developer@effective.band"
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
                        role = "Backend developer",
                        avatarUrl = "https://img.freepik.com/free-photo/beautiful-shot-of-a-white-british-shorthair-kitten_181624-57681.jpg",
                        integrations = listOf(
                            IntegrationDTO(
                                "c717cf6e-28b3-4148-a469-032991e5d9e9",
                                "phoneNumber",
                                "89236379887"
                            ), IntegrationDTO(
                                "8c02c6dc-1c4f-459e-ac27-f1db53923b2f",
                                "telegram",
                                "@cooldeveloper"
                            )
                        ),
                        email = "cool.android.developer@effective.band"
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
        pathParameter<String>("user_id") {
            description = "User id"
            example = "87e66ee0-2550-4188-8d79-75560125836a"
            required = true
            allowEmptyValue = false
        }
        body<UserDTO> {
            required = true
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
                            "c717cf6e-28b3-4148-a469-032991e5d9e9",
                            "phoneNumber",
                            "88009000000"
                        ), IntegrationDTO(
                            "8c02c6dc-1c4f-459e-ac27-f1db53923b2f",
                            "telegram",
                            "@mimocrocodile"
                        )
                    ),
                    email = "mimocrocodile@mimo.crocodile"
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
                                "c717cf6e-28b3-4148-a469-032991e5d9e9",
                                "phoneNumber",
                                "88009000000"
                            ), IntegrationDTO(
                                "8c02c6dc-1c4f-459e-ac27-f1db53923b2f",
                                "telegram",
                                "@mimocrocodile"
                            )
                        ),
                        email = "mimocrocodile@mimo.crocodile"
                    )
                ) {
                }
            }
        }
        HttpStatusCode.BadRequest to {
            description = "Bad request"
        }
        HttpStatusCode.NotFound to {
            description = "User with this id not found"
        }
    }
}
