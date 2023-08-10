package band.effective.office.tablet.domain.model

data class Booking(
    val nameRoom: String,
    val roomId: String,
    val eventInfo: EventInfo
) {
    companion object {
        val default = Booking(nameRoom = "", roomId = "", eventInfo = EventInfo.emptyEvent)
    }
}