package band.effective.office.tablet.network.repository

interface BookingRepository {
    suspend fun bookingRoom(): Boolean
}