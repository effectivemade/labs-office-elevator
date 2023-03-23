package band.effective.office.tv.domain.model

import band.effective.office.tv.network.leader.models.eventInfo.EventInfoResponse
import java.util.*

fun EventInfoResponse.toLeaderIdEventInfo(): LeaderIdEventInfo =
    LeaderIdEventInfo(
        id = data.id,
        name = data.fullName,
        dateTime = stringToDateTime(data.dateStart),
        isOnline = data.status == "online",
        photoUrl = data.photo,
        organizer = data.organizers[0].name,
        speakers = data.speakers.map { "${it.user.firstName} ${it.user.lastName}" }
    )

fun stringToDateTime(str: String): GregorianCalendar {
    val date = str.substring(0, str.indexOf(" "))
    val time = str.replace(date + " ", "")
    val dateComponents = date.split("-")
    val timeComponents = time.split(":")
    return GregorianCalendar(
        dateComponents[0].toInt(),
        dateComponents[1].toInt(),
        dateComponents[2].toInt(),
        timeComponents[0].toInt(),
        timeComponents[1].toInt()
    )
}