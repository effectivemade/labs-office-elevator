package band.effective.office.tablet.domain.model

data class RoomInfo(
    val name: String,
    val capacity: Int,
    val isHaveTv: Boolean,
    val electricSocketCount: Int,
    val eventList: List<EventInfo>,
    //NOTE(Maksim Mishenko): currentEvent is null if room is free
    val currentEvent: EventInfo?
){
    companion object{
        val defaultValue =
            RoomInfo(
                name = "Default",
                capacity = 0,
                isHaveTv = false,
                electricSocketCount = 0,
                eventList = listOf(),
                currentEvent = null
            )
    }
}
