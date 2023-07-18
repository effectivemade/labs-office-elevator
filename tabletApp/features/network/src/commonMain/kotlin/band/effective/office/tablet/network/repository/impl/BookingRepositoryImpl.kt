package band.effective.office.tablet.network.repository.impl

import band.effective.office.tablet.network.api.Api
import band.effective.office.tablet.network.repository.BookingRepository

class BookingRepositoryImpl(private val api: Api) : BookingRepository {
    override suspend fun bookingRoom(): Boolean = api.bookingRoom()
}