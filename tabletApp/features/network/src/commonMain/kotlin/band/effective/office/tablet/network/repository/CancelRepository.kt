package band.effective.office.tablet.network.repository

interface CancelRepository {
    suspend fun cancelEvent(): Boolean
}