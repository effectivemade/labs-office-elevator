package band.effective.office.tv.source

import android.util.Log
import band.effective.office.tv.domain.models.Employee.EmployeeInfoDto
import notion.api.v1.NotionClient
import notion.api.v1.model.common.File
import notion.api.v1.model.pages.Page
import notion.api.v1.request.databases.QueryDatabaseRequest

class BirthdayRemoteDataSource {
    fun fetchLatestBirthdays(): List<EmployeeInfoDto> {
        val employeeInfoList: MutableList<EmployeeInfoDto> = mutableListOf()
        NotionClient(token = "secret_DuEWVknGQm55uiDiIrBz6E9nbQ1R4vixZPz5BLfeauX").use { client ->
            getPagesFromDatabase(client).map {
                val icon: File = it.icon as File
                Log.d("BirthdaysRepositoryImpl", "${it.properties}")
                employeeInfoList.add(
                    EmployeeInfoDto(
                        firstName = it.properties["Name"]?.title?.get(0)?.text?.content,
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
                "862d1f2393c9473fbc4ff6d9a74d3310"
            )
        ).results
    }

}