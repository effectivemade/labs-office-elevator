package band.effective.office.network.api.impl

import band.effective.office.network.api.Api
import band.effective.office.network.dto.BookingInfo
import band.effective.office.network.dto.SuccessResponse
import band.effective.office.network.dto.UserDTO
import band.effective.office.network.dto.WorkspaceDTO
import band.effective.office.network.model.Either
import band.effective.office.network.model.ErrorResponse
import band.effective.office.utils.KtorEtherClient
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

    override suspend fun getWorkspaces(tag: String): Either<ErrorResponse, List<WorkspaceDTO>> =
        client.securityResponse("$baseUrl/workspaces") {
            url {
                parameters.append("tag", tag)
            }
        }

    override suspend fun getUser(id: String): Either<ErrorResponse, UserDTO> =
        client.securityResponse("$baseUrl/users") {
            url {
                parameters.append("id", id)
            }
        }

    override suspend fun getUsers(): Either<ErrorResponse, List<UserDTO>> =
        client.securityResponse("$baseUrl/users")

    //TODO(Maksim Mishenko): Request not exist in swagger
    override suspend fun getBookingsByUser(userId: String): Either<ErrorResponse, List<BookingInfo>> =
        Either.Error(ErrorResponse(code = 601, description = "Request not exist in swagger"))

    //TODO(Maksim Mishenko): Request not exist in swagger
    override suspend fun getBookingsByWorkspaces(workspaceId: String): Either<ErrorResponse, List<BookingInfo>> =
        Either.Error(ErrorResponse(code = 601, description = "Request not exist in swagger"))

    //TODO(Maksim Mishenko): Request not exist in swagger
    override suspend fun createBooking(bookingInfo: BookingInfo): Either<ErrorResponse, SuccessResponse> =
        Either.Error(ErrorResponse(code = 601, description = "Request not exist in swagger"))

    //TODO(Maksim Mishenko): Request not exist in swagger
    override suspend fun updateBooking(
        bookingInfo: BookingInfo
    ): Either<ErrorResponse, SuccessResponse> =
        Either.Error(ErrorResponse(code = 601, description = "Request not exist in swagger"))

    //TODO(Maksim Mishenko): Request not exist in swagger
    override suspend fun deleteBooking(bookingId: String): Either<ErrorResponse, SuccessResponse> =
        Either.Error(ErrorResponse(code = 601, description = "Request not exist in swagger"))

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

    //TODO(Maksim Mрегьюлар воркспейсамishenko): Request not exist in swagger
    override suspend fun subscribeOnBookingsList(workspaceId: String): Flow<Either<ErrorResponse, List<BookingInfo>>> =
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
}