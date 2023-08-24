package band.effective.office.network.api.impl

import band.effective.office.network.api.Api
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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ApiImpl : Api {
    private val client = KtorEtherClient
    private val baseUrl: String = "https://d5do2upft1rficrbubot.apigw.yandexcloud.net"
    override suspend fun getWorkspace(id: String): Either<ErrorResponse, WorkspaceDTO> =
        client.securityResponse("$baseUrl/workspaces") {
            url {
                parameters.append("id", id)
            }
        }

    override suspend fun getWorkspaces(
        tag: String,
        freeFrom: Long?,
        freeUntil: Long?
    ): Either<ErrorResponse, List<WorkspaceDTO>> =
        client.securityResponse("$baseUrl/workspaces") {
            url {
                parameters.append("tag", tag)
                if (freeFrom != null) {
                    parameters.append("free_from", freeFrom.toString())
                }
                if (freeUntil != null) {
                    parameters.append("free_until", freeUntil.toString())
                }
            }
        }

    override suspend fun getZones(): Either<ErrorResponse, List<WorkspaceZoneDTO>> =
        client.securityResponse("$baseUrl//workspaces/zones")

    override suspend fun getUser(id: String): Either<ErrorResponse, UserDTO> =
        client.securityResponse("$baseUrl/users") {
            url {
                appendPathSegments(id)
            }
        }

    override suspend fun getUsers(tag: String): Either<ErrorResponse, List<UserDTO>> =
        client.securityResponse("$baseUrl/users") {
            url {
                parameters.append("tag", tag)
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
            setBody(bookingInfo)
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

    //TODO(Maksim Mishenko): Request not exist in swagger
    override suspend fun subscribeOnWorkspaceUpdates(id: String): Flow<Either<ErrorResponse, WorkspaceDTO>> =
        flow {
            emit(
                Either.Error(
                    ErrorResponse(
                        code = 601,
                        description = "Request not exist in swagger"
                    )
                )
            )
        }

    //TODO(Maksim Mishenko): Request not exist in swagger
    override suspend fun subscribeOnOrganizersList(): Flow<Either<ErrorResponse, List<UserDTO>>> =
        flow {
            emit(
                Either.Error(
                    ErrorResponse(
                        code = 601,
                        description = "Request not exist in swagger"
                    )
                )
            )
        }

    //TODO(Maksim Mishenko): Request not exist in swagger
    override suspend fun subscribeOnBookingsList(workspaceId: String): Flow<Either<ErrorResponse, List<BookingDTO>>> =
        flow {
            emit(
                Either.Error(
                    ErrorResponse(
                        code = 601,
                        description = "Request not exist in swagger"
                    )
                )
            )
        }

    override suspend fun getUserByEmail(email: String): Either<ErrorResponse, UserDTO> =
        client.securityResponse("$baseUrl/users"){
           url {
               parameters.append(name = "email", value = email)
           }
        }
}