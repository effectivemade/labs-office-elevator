package office.effective.serviceapi

import office.effective.common.exception.InstanceNotFoundException
import office.effective.model.*
import java.util.*

interface IBookingService {

    /**
     * Returns whether a booking with the given id exists
     *
     * @author Daniil Zavyalov
     */
    fun existsById(id: String): Boolean

    /**
     * Deletes the booking with the given id
     *
     * @author Daniil Zavyalov
     */
    fun deleteById(id: String)

    /**
     * Retrieves a booking model by its id
     *
     * @author Daniil Zavyalov
     */
    fun findById(id: String): Booking?

    /**
     * Returns all bookings. Bookings can be filtered by owner and workspace id
     *
     * @throws InstanceNotFoundException if user or workspace with the given id doesn't exist in database
     *
     * @author Daniil Zavyalov
     */
    fun findAll(userId: UUID?, workspaceId: UUID?): List<Booking>

    /**
     * Saves a given booking. Use the returned model for further operations
     *
     * @author Daniil Zavyalov
     */
    fun save(booking: Booking): Booking

    /**
     * Updates a given booking. Use the returned model for further operations
     *
     * @author Daniil Zavyalov
     */
    fun update(booking: Booking): Booking
}
