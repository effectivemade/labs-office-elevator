package band.effective.office.tv.repository.workTogether

import band.effective.office.tv.BuildConfig
import notion.api.v1.NotionClient
import notion.api.v1.model.common.PropertyType
import notion.api.v1.model.pages.Page
import notion.api.v1.request.databases.QueryDatabaseRequest
import java.text.SimpleDateFormat
import java.util.GregorianCalendar
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WorkTogetherImpl @Inject constructor(private val notionClient: NotionClient) : WorkTogether {
    override fun getAll(): List<Teammate> {
        return notionClient.queryDatabase(
            request = QueryDatabaseRequest(BuildConfig.notionDatabaseId)
        ).results.map { it.toTeammate() }

    }

    private fun Page.toTeammate() =
        Teammate(
            id = id,
            name = getStringFromProp("Name") ?: "Null name",
            positions = getStringFromProp("Position")?.split(" ") ?: listOf(),
            employment = getStringFromProp("Employment") ?: "Null employment",
            startDate = GregorianCalendar().apply {
                val date = getStringFromProp("Start Date")
                val simpleDateFormatter = SimpleDateFormat("yyyy-MM-dd")
                if (date != null) {
                    time = simpleDateFormatter.parse(date) ?: time
                }
            },
            duolingo = getStringFromProp("Профиль Duolingo")
        )

    private fun Page.getStringFromProp(propName: String) =
        properties[propName]?.run {
            when (type) {
                PropertyType.Title -> title?.firstOrNull()?.text?.content
                PropertyType.RichText -> richText?.firstOrNull()?.text?.content
                PropertyType.MultiSelect -> multiSelect?.fold("") { acc, option -> "$acc ${option.name}" }
                PropertyType.Select -> select?.name
                PropertyType.Date -> date?.start
                else -> null
            }
        }
}