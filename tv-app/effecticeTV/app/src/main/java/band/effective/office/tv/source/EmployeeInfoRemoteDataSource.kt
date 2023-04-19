package band.effective.office.tv.source

import band.effective.office.tv.BuildConfig
import band.effective.office.tv.network.notion.EmployeeInfoDto
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
            getPagesFromDatabase(client).map {
                val icon: File = it.icon as File
                employeeInfoList.add(
                    EmployeeInfoDto(
                        firstName = it.properties["Name"]?.title?.get(0)?.text?.content?.split(" ")
                            ?.get(0),
                        startDate = it.properties["Start Date"]?.date?.start,
                        nextBirthdayDate = it.properties["Next B-DAY"]?.date?.start,
                        photoUrl = icon.file?.url
                    )
                )

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