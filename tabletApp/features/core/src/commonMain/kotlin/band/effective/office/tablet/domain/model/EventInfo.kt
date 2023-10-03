package band.effective.office.tablet.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.Calendar
import java.util.GregorianCalendar
@Parcelize
data class EventInfo(
    val startTime: Calendar,
    val finishTime: Calendar,
    val organizer: Organizer,
    val id: String
) : Parcelable {
    companion object {
        val emptyEvent = EventInfo(
            startTime = GregorianCalendar(),
            finishTime = GregorianCalendar(),
            organizer = Organizer.default,
            id = ""
        )
    }
}
