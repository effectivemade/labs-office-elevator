package office.effective.features.calendar.repository

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.http.HttpTransport
import com.google.api.client.json.JsonFactory
import com.google.api.client.json.gson.GsonFactory
import com.google.api.services.calendar.Calendar
import com.google.api.services.calendar.CalendarScopes
import com.google.auth.http.HttpCredentialsAdapter
import com.google.auth.oauth2.GoogleCredentials
import io.ktor.utils.io.core.*
import office.effective.common.exception.InstanceNotFoundException
import office.effective.common.exception.MissingIdException
import office.effective.config
import office.effective.features.workspace.converters.WorkspaceRepositoryConverter
import office.effective.features.workspace.repository.WorkspaceEntity
import office.effective.features.workspace.repository.WorkspaceRepository
import office.effective.model.Booking
import office.effective.model.Workspace
import org.koin.core.context.GlobalContext
import org.ktorm.database.Database
import org.ktorm.dsl.eq
import org.ktorm.entity.all
import org.ktorm.entity.find
import org.ktorm.entity.toList
import utils.GoogleCalendarConverter.toGoogleEvent
import java.io.ByteArrayInputStream
import java.io.FileInputStream
import java.io.InputStreamReader
import java.util.UUID
import kotlin.text.toByteArray

class CalendarRepository(
    private val db: Database,
    private val converter: WorkspaceRepositoryConverter,
    private val workspaceRepository: WorkspaceRepository
) {
    val jsonFactory: JsonFactory = GsonFactory.getDefaultInstance()
    val authorGoogleAccount: String = config.propertyOrNull("auth.app.defaultAppEmail")?.getString() ?: throw Exception(
        "Config file does not contain default gmail value"
    )
    val credentialsPath: String = config.propertyOrNull("auth.app.credentials")?.getString()
        ?: throw Exception("Config file does not contain path for google credentials")


    val inputStream = ByteArrayInputStream(System.getenv("JSON_CREDENTIALS").toByteArray())

    var httpTransport: HttpTransport = GoogleNetHttpTransport.newTrustedTransport()

    var credentials: GoogleCredentials = getCredential(httpTransport)


    val calendar = Calendar.Builder(httpTransport, jsonFactory, HttpCredentialsAdapter(credentials))
        .setApplicationName("APPLICATION_NAME").build()



    private fun getCredential(httpTransport: HttpTransport): GoogleCredentials {
        return GoogleCredentials.fromStream(inputStream)
            .createScoped(CalendarScopes.CALENDAR)
            .createDelegated(authorGoogleAccount)
    }


    /**
     * @return String - id of calendar for specified workspace
     *
     * @author Danil Kiselev
     * */
    fun findByWorkspace(workspaceId: UUID): String {
        return db.calendarIds.find { it.workspaceId eq workspaceId }?.calendarId ?: throw InstanceNotFoundException(
            CalendarIdEntity::class, "Cannot found calendar id to workspace id $workspaceId", null
        )
    }

    fun findByWorkspaceOrDefault(workspaceId: UUID): String {
        return db.calendarIds.find { it.workspaceId eq workspaceId }?.calendarId ?: ""
    }

    /**
     * @return Workspace model by calendar id (String)
     *
     * @author Danil Kiselev
     * */
    fun findWorkspaceById(calendarId: String): Workspace {
        val workspaceEntity =
            db.calendarIds.find { it.calendarId eq calendarId }?.workspace ?: throw InstanceNotFoundException(
                WorkspaceEntity::class, "Workspace with such google calendar id not found", null
            )
        return converter.entityToModel(
            workspaceEntity, workspaceRepository.findUtilitiesByWorkspaceId(workspaceEntity.id)
        )
    }

    fun findAllCalendarsId(): List<String> {
        var list = mutableListOf<String>()
        db.calendarIds.toList().forEach { list.add(it.calendarId) }
        return list
    }
}