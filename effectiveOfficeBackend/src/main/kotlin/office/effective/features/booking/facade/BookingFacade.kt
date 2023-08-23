package office.effective.features.booking.facade
import io.ktor.server.plugins.*
import office.effective.common.exception.InstanceNotFoundException
import office.effective.common.utils.DatabaseTransactionManager
import office.effective.common.utils.UuidValidator
import office.effective.features.booking.converters.BookingFacadeConverter
import office.effective.dto.BookingDTO
import office.effective.features.booking.service.BookingService
import office.effective.model.Booking
import office.effective.model.Workspace

class BookingFacade(private val bookingService: BookingService,
                    private val transactionManager: DatabaseTransactionManager,
                    private val uuidValidator: UuidValidator,
                    private val bookingConverter: BookingFacadeConverter
) {

    /**
     * Deletes the booking with the given id
     *
     * @author Daniil Zavyalov
     */
    fun deleteById(id: String) {
        transactionManager.useTransaction({
            bookingService.deleteById(id)
        })
    }

    /**
     * Retrieves a booking model by its id
     *
     * @throws InstanceNotFoundException if booking with the given id doesn't exist in database
     *
     * @author Daniil Zavyalov
     */
    fun findById(id: String): BookingDTO {
        val dto: BookingDTO = transactionManager.useTransaction({
            val model = bookingService.findById(id)
                ?: throw InstanceNotFoundException(Workspace::class, "Booking with id $id not found")
            bookingConverter.modelToDto(model)
        })
        return dto
    }

    /**
     * Returns all bookings. Bookings can be filtered by owner and workspace id
     *
     * @author Daniil Zavyalov
     */
    fun findAll(userId: String?, workspaceId: String?): List<BookingDTO> {
        val bookingList: List<Booking> = transactionManager.useTransaction({
            bookingService.findAll(
                userId?.let { uuidValidator.uuidFromString(it) },
                workspaceId?.let { uuidValidator.uuidFromString(it) }
            )
        })
        return bookingList.map {
            bookingConverter.modelToDto(it)
        }
    }

    /**
     * Saves a given booking. Use the returned model for further operations
     *
     * @author Daniil Zavyalov
     */
    fun post(bookingDTO: BookingDTO): BookingDTO {
        val model = bookingConverter.dtoToModel(bookingDTO)
        val dto: BookingDTO = transactionManager.useTransaction({
            val savedModel = bookingService.save(model)
            bookingConverter.modelToDto(savedModel)
        })
        return dto
    }

    /**
     * Updates a given booking. Use the returned model for further operations
     *
     * @throws BadRequestException if booking id is null
     *
     * @author Daniil Zavyalov
     */
    fun put(bookingDTO: BookingDTO): BookingDTO {
        if (bookingDTO.id == null) throw BadRequestException("Missing booking id")
        val model = bookingConverter.dtoToModel(bookingDTO)
        val dto: BookingDTO = transactionManager.useTransaction({
            val savedModel = bookingService.update(model)
            bookingConverter.modelToDto(savedModel)
        })
        return dto
    }
}