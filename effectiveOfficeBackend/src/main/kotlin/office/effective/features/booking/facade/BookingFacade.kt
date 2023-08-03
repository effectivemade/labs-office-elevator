package office.effective.features.booking.facade
import office.effective.common.exception.InstanceNotFoundException
import office.effective.common.utils.DatabaseTransactionManager
import office.effective.common.utils.UuidValidator
import office.effective.features.booking.converters.BookingFacadeConverter
import office.effective.features.booking.dto.BookingDTO
import office.effective.features.booking.service.BookingService
import office.effective.model.Booking
import office.effective.model.Workspace
import java.util.*

class BookingFacade(private val bookingService: BookingService,
                    private val transactionManager: DatabaseTransactionManager,
                    private val uuidValidator: UuidValidator,
                    private val bookingConverter: BookingFacadeConverter
) {

    fun deleteById(id: String) {
        transactionManager.useTransaction({
            bookingService.deleteById(
                uuidValidator.uuidFromString(id)
            )
        })
    }

    fun findById(id: String): BookingDTO {
        val uuid = uuidValidator.uuidFromString(id)
        val dto: BookingDTO = transactionManager.useTransaction({
            val model = bookingService.findById(uuid)
                ?: throw InstanceNotFoundException(Workspace::class, "Booking with id $id not found", uuid)
            bookingConverter.modelToDto(model)
        })
        return dto
    }

    fun findAllByOwnerId(ownerId: String): List<BookingDTO> {
        val workspaceList: List<Booking> = transactionManager.useTransaction({
            bookingService.findAllByOwnerId(
                uuidValidator.uuidFromString(ownerId)
            )
        })
        return workspaceList.map {
            bookingConverter.modelToDto(it)
        }
    }

    fun post(bookingDTO: BookingDTO): BookingDTO {
        val model = bookingConverter.dtoToModel(bookingDTO)
        val dto: BookingDTO = transactionManager.useTransaction({
            val savedModel = bookingService.save(model)
            bookingConverter.modelToDto(savedModel)
        })
        return dto
    }

    fun put(bookingDTO: BookingDTO): BookingDTO {
        val model = bookingConverter.dtoToModel(bookingDTO)
        val dto: BookingDTO = transactionManager.useTransaction({
            val savedModel = bookingService.update(model)
            bookingConverter.modelToDto(savedModel)
        })
        return dto
    }
}