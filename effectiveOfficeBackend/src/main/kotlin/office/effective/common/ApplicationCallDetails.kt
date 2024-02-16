package office.effective.common

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.util.*
import office.effective.common.enums.Roles

class ApplicationCallDetails(
    public val call: ApplicationCall, public var role: Roles?
) : ApplicationCall {
    override val application: Application
        get() = call.application
    override val attributes: Attributes
        get() = call.attributes
    override val parameters: Parameters
        get() = call.parameters
    override val request: ApplicationRequest
        get() = call.request
    override val response: ApplicationResponse
        get() = call.response

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ApplicationCallDetails

        if (call != other.call) return false
        if (role != other.role) return false
        if (application != other.application) return false
        if (attributes != other.attributes) return false
        if (parameters != other.parameters) return false
        if (request != other.request) return false
        if (response != other.response) return false

        return true
    }

    override fun hashCode(): Int {
        var result = call.hashCode()
        result = 31 * result + (role?.hashCode() ?: 0)
        result = 31 * result + application.hashCode()
        result = 31 * result + attributes.hashCode()
        result = 31 * result + parameters.hashCode()
        result = 31 * result + request.hashCode()
        result = 31 * result + response.hashCode()
        return result
    }

    override fun toString(): String {
        return "ApplicationCallDetails(call=$call, role=$role, application=$application, attributes=$attributes, parameters=$parameters, request=$request, response=$response)"
    }


}