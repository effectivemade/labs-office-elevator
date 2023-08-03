package band.effective.office.tablet.domain.model

import network.model.ErrorResponse

data class ErrorWithData<out T>(
    val error: ErrorResponse,
    val saveData: T?
)