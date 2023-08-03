package office.effective.plugins

import io.ktor.server.application.*
import io.ktor.server.plugins.requestvalidation.*
import office.effective.features.booking.dto.BookingDTO

fun Application.configureValidation() {
    install(RequestValidation) {
        validate<BookingDTO> { booking ->
            if (booking.beginBooking < 0)
                ValidationResult.Invalid("beginBooking should be non-negative")
            if (booking.endBooking < 0)
                ValidationResult.Invalid("beginBooking should be non-negative")
            if (booking.beginBooking < booking.endBooking)
                ValidationResult.Invalid("beginBooking should be greater than endBooking")
            else ValidationResult.Valid
        }
    }
}
