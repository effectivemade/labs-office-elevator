package band.effective.office.tv.network.notion

import band.effective.office.tv.BuildConfig
import notion.api.v1.NotionClient
import notion.api.v1.model.common.File
import notion.api.v1.model.pages.Page
import notion.api.v1.request.databases.QueryDatabaseRequest
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EmployeeInfoRemoteDataSource @Inject constructor() {
    fun fetchLatestBirthdays(): List<EmployeeInfoDto> {
        val employeeInfoList: MutableList<EmployeeInfoDto> = mutableListOf()
        NotionClient(BuildConfig.notionToken).use { client ->
            getPagesFromDatabase(client).map { page ->
                val icon: File? = try {
                    page.icon as File?
                } catch (ex: Exception) {
                    null
                }
                val firstName = page.properties["Name"]?.title?.get(0)?.text?.content?.split(" ")
                    ?.get(0)
                val startDate = page.properties["Start Date"]?.date?.start
                val nextBirthdayDate = page.properties["Next B-DAY"]?.date?.start
                val photoUrl = icon?.file?.url ?: "https://ui-avatars.com/api/?name=John+Doe"
                if (firstName != null && startDate != null && nextBirthdayDate != null) {
                    employeeInfoList.add(
                        EmployeeInfoDto(
                            firstName = firstName,
                            startDate = startDate,
                            nextBirthdayDate = nextBirthdayDate,
                            photoUrl = photoUrl
                        )
                    )
                }

            }
        }

        return employeeInfoList
    }

    private fun getPagesFromDatabase(client: NotionClient): List<Page> {
        return client.queryDatabase(
            request = QueryDatabaseRequest(
                BuildConfig.notionDatabaseId
            )
        ).results
    }

}