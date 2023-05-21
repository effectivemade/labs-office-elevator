package band.effective.office.tv.network.notion

import android.util.Log
import band.effective.office.tv.BuildConfig
import band.effective.office.tv.core.network.entity.Either
import band.effective.office.tv.core.network.entity.ErrorReason
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import notion.api.v1.NotionClient
import notion.api.v1.model.common.File
import notion.api.v1.model.pages.Page
import notion.api.v1.request.databases.QueryDatabaseRequest
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EmployeeInfoRemoteDataSourceImpl @Inject constructor(
    private val notionClient: NotionClient
    ): EmployeeInfoRemoteDataSource
{

    private var employeeInfoList: MutableList<EmployeeInfoDto> = mutableListOf()

    override suspend fun fetchLatestBirthdays(): Flow<Either<String, List<EmployeeInfoDto>>> =
        flow {
            notionClient.use { client ->
                when (val res: Either<ErrorReason, List<EmployeeInfoDto>> =
                    queryDatabasePages(client)) {
                    is Either.Success -> {
                        emit(Either.Success(res.data))
                        Log.d("test", employeeInfoList.size.toString())
                    }
                    is Either.Failure -> {
                        emit(Either.Failure(res.error.message))
                    }
                }
            }
        }

    private fun queryPage(page: Page): Either<ErrorReason, List<EmployeeInfoDto>> {
        val icon: File? = try {
            page.icon as File?
        } catch (ex: Exception) {
            Log.d(
                "EmployeeInfoRemoteDataSourceImpl",
                "An error occurred when fetching data from a notion, message: ${ex.message} \n stacktrace:${ex.stackTrace}"
            )
            null
        }
        val firstName = page.properties["Name"]?.title?.get(0)?.text?.content?.split(" ")
            ?.get(0)
        val startDate = page.properties["Start Date"]?.date?.start
        val nextBirthdayDate = page.properties["Next B-DAY"]?.date?.start
        val photoUrl = icon?.file?.url ?: ""
        if (firstName != null && (startDate != null || nextBirthdayDate != null)) {
            employeeInfoList.add(
                EmployeeInfoDto(
                    firstName = firstName,
                    startDate = startDate,
                    nextBirthdayDate = nextBirthdayDate,
                    photoUrl = photoUrl
                )
            )
        }
        return Either.Success(employeeInfoList)

    }

    override suspend fun getDuolingoUserName(): List<String> {
        val duolingoUserNames: MutableList<String> = mutableListOf()
        NotionClient(BuildConfig.notionToken).use { client ->
            getPagesFromDatabase(client).forEach { page ->
                val userName = page.properties["Профиль Duolingo"]?.richText?.run {
                    if (isNotEmpty())
                        get(0).plainText.toString()
                    else ""
                }.orEmpty()
                if (userName.isNotEmpty()) duolingoUserNames.add(userName)
            }
        }
        return duolingoUserNames.toList()
    }

    private fun getPagesFromDatabase(client: NotionClient): List<Page> {
        return client.queryDatabase(
            request = QueryDatabaseRequest(
                BuildConfig.notionDatabaseId
            )
        ).results
    }

    private fun queryDatabasePages(client: NotionClient): Either<ErrorReason, List<EmployeeInfoDto>> {
        employeeInfoList = mutableListOf()
        try {
            getPagesFromDatabase(client).map { page ->
                queryPage(page)
            }
        } catch (t: Throwable) {
            val errorReason = when (t) {
                is UnknownHostException -> ErrorReason.NetworkError(t)
                is TimeoutException -> ErrorReason.NetworkError(t)
                else -> ErrorReason.UnexpectedError(t)
            }
            return Either.Failure(errorReason)
        }
        return Either.Success(employeeInfoList)
    }

}