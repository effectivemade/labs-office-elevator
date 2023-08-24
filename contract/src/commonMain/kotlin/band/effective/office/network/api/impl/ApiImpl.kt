package band.effective.office.network.api.impl

import band.effective.office.network.api.Api
import band.effective.office.network.api.Collector
import band.effective.office.network.dto.BookingDTO
import band.effective.office.network.dto.SuccessResponse
import band.effective.office.network.dto.UserDTO
import band.effective.office.network.dto.WorkspaceDTO
import band.effective.office.network.dto.WorkspaceZoneDTO
import band.effective.office.network.model.Either
import band.effective.office.network.model.ErrorResponse
import band.effective.office.utils.KtorEtherClient
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.appendPathSegments
import io.ktor.http.contentType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class ApiImpl : Api {
    val collector = Collector("")
    private val client = KtorEtherClient
    private val baseUrl: String = "https://d5do2upft1rficrbubot.apigw.yandexcloud.net"
    override suspend fun getWorkspace(id: String): Either<ErrorResponse, WorkspaceDTO> =
        with(getWorkspaces("meeting")) {
            when (this) {
                is Either.Error -> this
                is Either.Success -> data.firstOrNull { it.name == id }.let {
                    if (it == null) {
                        Either.Error(ErrorResponse.getResponse(404))
                    } else {
                        Either.Success(it)
                    }
                }
            }
        }
    /*client.securityResponse("$baseUrl/workspaces") {
        url {
            parameters.append("id", id)
        }
    } */

    override suspend fun getWorkspaces(
        tag: String,
        freeFrom: Long?,
        freeUntil: Long?
    ): Either<ErrorResponse, List<WorkspaceDTO>> =
        client.securityResponse("$baseUrl/workspaces") {
            url {
                parameters.append("workspace_tag", tag)
                if (freeFrom != null) {
                    parameters.append("free_from", freeFrom.toString())
                }
                if (freeUntil != null) {
                    parameters.append("free_until", freeUntil.toString())
                }
            }
        }

    override suspend fun getZones(): Either<ErrorResponse, List<WorkspaceZoneDTO>> =
        client.securityResponse("$baseUrl/workspaces/zones")

    override suspend fun getUser(id: String): Either<ErrorResponse, UserDTO> =
        client.securityResponse("$baseUrl/users") {
            url {
                appendPathSegments(id)
            }
        }

    override suspend fun getUsers(tag: String): Either<ErrorResponse, List<UserDTO>> =
        client.securityResponse("$baseUrl/users") {
            url {
                parameters.append("user_tag", tag)
            }
        }

    override suspend fun updateUser(user: UserDTO): Either<ErrorResponse, UserDTO> =
        client.securityResponse(
            urlString = "$baseUrl/users",
            method = KtorEtherClient.RestMethod.Put
        ) {
            contentType(ContentType.Application.Json)
            setBody(user)
            url {
                appendPathSegments(user.id)
            }
        }

    override suspend fun getBooking(id: String): Either<ErrorResponse, BookingDTO> =
        client.securityResponse(
            urlString = "$baseUrl/bookings",
        ) {
            url {
                appendPathSegments(id)
            }
        }

    override suspend fun getBookingsByUser(userId: String): Either<ErrorResponse, List<BookingDTO>> =
        client.securityResponse(
            urlString = "$baseUrl/bookings",
        ) {
            url {
                parameters.append("user_id", userId)
            }
        }

    override suspend fun getBookingsByWorkspaces(workspaceId: String): Either<ErrorResponse, List<BookingDTO>> =
        client.securityResponse(
            urlString = "$baseUrl/bookings",
        ) {
            url {
                parameters.append("workspace_id", workspaceId)
            }
        }

    override suspend fun createBooking(bookingInfo: BookingDTO): Either<ErrorResponse, SuccessResponse> =
        client.securityResponse(
            urlString = "$baseUrl/bookings",
            method = KtorEtherClient.RestMethod.Post
        ) {
            contentType(ContentType.Application.Json)
            val body = Json.encodeToString(bookingInfo)
            setBody(body)
        }

    override suspend fun updateBooking(
        bookingInfo: BookingDTO
    ): Either<ErrorResponse, SuccessResponse> =
        client.securityResponse(
            urlString = "$baseUrl/bookings",
            method = KtorEtherClient.RestMethod.Put
        ) {
            contentType(ContentType.Application.Json)
            setBody(bookingInfo)
        }

    override suspend fun deleteBooking(bookingId: String): Either<ErrorResponse, SuccessResponse> =
        client.securityResponse(
            urlString = "$baseUrl/bookings",
        ) {
            url {
                appendPathSegments(bookingId)
            }
        }

    override fun subscribeOnWorkspaceUpdates(
        id: String,
        scope: CoroutineScope
    ): Flow<Either<ErrorResponse, WorkspaceDTO>> =
        collector.flow(scope).filter { it == "workspace" }.map { getWorkspace(id) }

    override fun subscribeOnOrganizersList(scope: CoroutineScope): Flow<Either<ErrorResponse, List<UserDTO>>> =
        collector.flow(scope).filter { it == "organizer" }.map { getUsers(tag = "employee") }


    override suspend fun getUserByEmail(email: String): Either<ErrorResponse, UserDTO> =
        client.securityResponse("$baseUrl/users"){
           url {
               parameters.append(name = "email", value = email)
           }
        }

    override fun subscribeOnBookingsList(
        workspaceId: String,
        scope: CoroutineScope
    ): Flow<Either<ErrorResponse, List<BookingDTO>>> =
        collector.flow(scope).filter { it == "booking" }
            .map { getBookingsByWorkspaces(workspaceId) }
}