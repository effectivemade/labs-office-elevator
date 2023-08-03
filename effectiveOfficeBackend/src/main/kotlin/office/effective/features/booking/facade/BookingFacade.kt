package office.effective.features.booking.facade
//import office.effective.common.utils.UuidValidator
import office.effective.common.utils.DatabaseTransactionManager
import office.effective.features.booking.service.BookingService
import java.util.*

class BookingFacade(private val bookingService: BookingService,
                    private val transactionManager: DatabaseTransactionManager
                    /*private val uuidValidator: UuidValidator*/) {

    fun deleteById(id: String) {
        transactionManager.useTransaction({
            bookingService.deleteById(UUID.fromString(id))
        })
    }
}