package band.effective.office.utils

import band.effective.office.network.dto.SuccessResponse
import band.effective.office.network.model.Either
import band.effective.office.network.model.ErrorResponse
import io.ktor.client.plugins.api.createClientPlugin
import io.ktor.utils.io.readUTF8Line
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer

val KtorEitherPlugin = createClientPlugin("KtorEitherPlugin") {
    transformResponseBody { response, content, requestedType ->
        if (response.status.value in 200..299) {
            if (requestedType.kotlinType!!.arguments[1].type!! == SuccessResponse::class){
                Either.Success(SuccessResponse("ok"))
            }
            else{
                Either.Success(
                    Json.decodeFromString(

                        serializer(requestedType.kotlinType!!.arguments[1].type!!),
                        content.readUTF8Line()!!
                    )
                )
            }

        } else {
            Either.Error(ErrorResponse.getResponse(response.status.value))
        }
    }
}