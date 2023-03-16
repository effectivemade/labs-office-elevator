package band.effective.office.tv.model

import java.util.GregorianCalendar

sealed class LeaderIdEventInfo{
    data class FullInfo(
        val id: Int,
        val name: String,
        val dateTime: GregorianCalendar,
        val isOnline: Boolean,
        val photoUrl: String,
        val organizer: String,
        val speakers: List<String>
    ):LeaderIdEventInfo(){
        constructor(partInfo: PartInfo, organizer: String,speakers: List<String>) : this(
            id = partInfo.id,
            name = partInfo.name,
            dateTime = partInfo.dateTime,
            isOnline = partInfo.isOnline,
            photoUrl = partInfo.photoUrl,
            organizer = organizer,
            speakers = speakers
        )
    }
    data class PartInfo(
        val id: Int,
        val name: String,
        val dateTime: GregorianCalendar,
        val isOnline: Boolean,
        val photoUrl: String,
        val errorText: String
    ):LeaderIdEventInfo()

    data class NullInfo(
        val errorText: String
    ): LeaderIdEventInfo()
}