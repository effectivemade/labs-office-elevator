package band.effective.office.tablet.domain.model

data class Organizer(val fullName: String, val id: String) {
    companion object {
        val default = Organizer(
            fullName = "", id = ""
        )
    }
}

