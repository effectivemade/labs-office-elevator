package office.effective.features.user.routes.swagger

import io.github.smiley4.ktorswaggerui.dsl.OpenApiRoute
import io.ktor.http.*
import office.effective.common.swagger.SwaggerDocument
import office.effective.features.user.dto.UserDTO

fun SwaggerDocument.login(): OpenApiRoute.() -> Unit = {
    description = "Empty route. Need to redirect to google authentication page"
    tags = listOf("users")
}

fun SwaggerDocument.callback(): OpenApiRoute.() -> Unit = {
    description = "Change user in db"
    tags = listOf("users")
    response {
        HttpStatusCode.OK to {
            description = "Return users emai;"
            body<String> {
                example(
                    "User email",
                    "123@effective.band"
                ) {
                }
            }
        }
    }
}