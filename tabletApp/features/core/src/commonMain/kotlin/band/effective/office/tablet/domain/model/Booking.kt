package band.effective.office.tablet.domain.model

data class Booking(
    val nameRoom: String,
    val eventInfo: EventInfo
) {
    companion object{
        val default = Booking(nameRoom = "", eventInfo = EventInfo.emptyEvent)
    }
}