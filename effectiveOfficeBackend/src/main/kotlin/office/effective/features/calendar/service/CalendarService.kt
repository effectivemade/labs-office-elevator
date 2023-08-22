package office.effective.features.calendar.service

import com.google.api.services.calendar.model.Event
import com.google.api.services.calendar.model.Event.Organizer
import com.google.api.services.calendar.model.EventAttendee
import com.google.api.services.calendar.model.EventDateTime
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.googleapis.testing.auth.oauth2.MockGoogleCredential
import com.google.api.client.http.HttpTransport
import com.google.api.client.json.JsonFactory
import com.google.api.client.json.gson.GsonFactory
import com.google.api.services.calendar.Calendar
import com.google.api.services.calendar.CalendarScopes
import com.google.auth.http.HttpCredentialsAdapter
import com.google.auth.oauth2.GoogleCredentials
import office.effective.common.exception.MissingIdException
import office.effective.config
import office.effective.dto.BookingDTO
import office.effective.features.calendar.repository.CalendarRepository
import office.effective.model.Booking
import utils.GoogleCalendarConverter
import utils.GoogleCalendarConverter.toGoogleEvent
import java.io.FileInputStream
import java.io.InputStreamReader


class CalendarService(val repository: CalendarRepository, val converter: GoogleCalendarConverter) {
    val authorGoogleAccount: String = "maxim.mishchenko@effective.band"
    val credentialsPath: String = config.propertyOrNull("auth.app.credentials")?.getString()
        ?: throw Exception("Config file does not contain path for google credentials")
    val inputStream = FileInputStream(credentialsPath)
    var httpTransport: HttpTransport = GoogleNetHttpTransport.newTrustedTransport()

    var credentials: GoogleCredentials = getCredential(httpTransport)
    val jsonFactory: JsonFactory = GsonFactory.getDefaultInstance()

    val calendar = Calendar.Builder(httpTransport, jsonFactory, HttpCredentialsAdapter(credentials))
        .setApplicationName("APPLICATION_NAME").build()

    fun putEvent(booking: Booking) {
        val event = booking.toGoogleEvent()
        val calendarID: String =
            repository.findByWorkspace(booking.workspace.id ?: throw MissingIdException("workspace model"))
        calendar.Events().insert(calendarID, event)
    }

    private fun getCredential(httpTransport: HttpTransport): GoogleCredentials {
        val clientSecrets = GoogleClientSecrets.load(
            jsonFactory, InputStreamReader(
                object {}.javaClass.getResourceAsStream(credentialsPath)
                    ?: throw Exception("Cannot access to credentials file")
            )
        )

        val flow = GoogleAuthorizationCodeFlow.Builder(
            httpTransport, jsonFactory, clientSecrets, listOf(CalendarScopes.CALENDAR)
        ).setAccessType("offline").build()

        return GoogleCredentials.fromStream(inputStream).createScoped(CalendarScopes.CALENDAR)
            .createDelegated(authorGoogleAccount)
    }
}
