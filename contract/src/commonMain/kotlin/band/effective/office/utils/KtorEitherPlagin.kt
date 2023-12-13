package band.effective.office.utils

import band.effective.office.network.dto.SuccessResponse
import band.effective.office.network.model.Either
import band.effective.office.network.model.ErrorResponse
import io.ktor.client.plugins.api.createClientPlugin
import io.ktor.utils.io.ByteReadChannel
import io.ktor.utils.io.read
import io.ktor.utils.io.readUTF8Line
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer

/**Ktor plugin for parse response to Either*/
val KtorEitherPlugin = createClientPlugin("KtorEitherPlugin") {
    transformResponseBody { response, content, requestedType ->
        if (response.status.value in 200..299) {
            val response = content.readEntireUTF8()
            if (response == null) {
                // if response success and empty, return SuccessResponse
                Either.Success(SuccessResponse("ok"))
            } else {
                println("KtorEitherPluginSuccess: $response")
                Either.Success(
                    Json.decodeFromString(
                        serializer(requestedType.kotlinType!!.arguments[1].type!!), // get serializer for success response model
                        response
                    )
                )
            }

        } else {
            println(
                "KtorEitherPluginError: ${response.status.value}: ${response.status.description}\n" +
                        "${content.readUTF8Line()}"
            )
            Either.Error(ErrorResponse.getResponse(response.status.value))
        }
    }
}

private suspend fun ByteReadChannel.readEntireUTF8(): String {
    val sb = StringBuilder()
    while (!isClosedForRead) {
        sb.append(readUTF8Line() ?: "")
    }
    return sb.toString()
}