package band.effective.office.tv.repository.duolingo.impl

import band.effective.office.tv.BuildConfig
import band.effective.office.tv.core.network.entity.Either
import band.effective.office.tv.domain.model.duolingo.DuolingoUser
import band.effective.office.tv.domain.model.duolingo.toDomain
import band.effective.office.tv.network.duolingo.DuolingoApi
import band.effective.office.tv.repository.duolingo.DuolingoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.withContext
import notion.api.v1.NotionClient
import notion.api.v1.model.pages.Page
import notion.api.v1.request.databases.QueryDatabaseRequest
import javax.inject.Inject

class DuolingoRepositoryImpl @Inject constructor(
    private val duolingoApi: DuolingoApi,
    private val notionClient: NotionClient
) : DuolingoRepository {
    private val duolingoAttributeName = "Профиль Duolingo"

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun getUsers(): Flow<Either<String, List<DuolingoUser>>> =
        getDuolingoUserName().mapLatest { userNamesRequest ->
            when (userNamesRequest) {
                is Either.Success -> {
                    getDuolingoUsersInfoFromApi(userNamesRequest.data)
                }
                is Either.Failure -> {
                    userNamesRequest
                }
            }
        }

    private suspend fun getDuolingoUserName(): Flow<Either<String, List<String>>> =
        flow {
            emit(
                getDuolingoUserNamesFromNotion(client = notionClient)
            )
        }

    private suspend fun getDuolingoUsersInfoFromApi(duolingoUserNames: List<String>): Either<String, List<DuolingoUser>> {
        val duolingoUsersInfo: MutableList<DuolingoUser> = mutableListOf()
        var errorRequest = ""
        duolingoUserNames.forEach { userName ->
            withContext(Dispatchers.IO) {
                when (val duolingoUser = duolingoApi.getUserInfo(userName)) {
                    is Either.Success -> {
                        val user = duolingoUser.data.toDomain()
                        if (user != null) duolingoUsersInfo.add(user)
                    }
                    is Either.Failure -> {
                        errorRequest = duolingoUser.error.message
                    }
                }
            }
        }
        return if (duolingoUsersInfo.isEmpty()) Either.Failure(errorRequest)
        else Either.Success(duolingoUsersInfo.toList())
    }

    private fun getPagesFromDatabase(client: NotionClient): List<Page> {
        return client.queryDatabase(
            request = QueryDatabaseRequest(
                BuildConfig.notionDatabaseId
            )
        ).results
    }

    private fun getUserNameFromNotionPage(page: Page): String =
        page.properties[duolingoAttributeName]?.richText?.run {
            if (isNotEmpty())
                get(0).plainText.toString()
            else ""
        }.orEmpty()

    private fun getDuolingoUserNamesFromNotion(client: NotionClient): Either<String, List<String>> {
        val usersNames: MutableList<String> = mutableListOf()
        try {
            getPagesFromDatabase(client).forEach { page ->
                val userName = getUserNameFromNotionPage(page)
                if (userName.isNotEmpty()) usersNames.add(userName)
            }
        } catch (t: Throwable) {
            return Either.Failure(t.message.orEmpty())
        }
        return Either.Success(usersNames)
    }
}
