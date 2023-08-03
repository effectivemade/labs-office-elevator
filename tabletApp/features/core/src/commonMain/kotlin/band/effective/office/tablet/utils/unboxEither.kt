package band.effective.office.tablet.utils

import band.effective.office.tablet.domain.model.Either

fun <ErrorType, DataType> Either<ErrorType, DataType>.unbox(
    errorHandler: (ErrorType) -> DataType,
    successHandler: ((DataType) -> DataType)? = null
): DataType =
    when (this) {
        is Either.Error -> errorHandler(error)
        is Either.Success -> successHandler?.invoke(data) ?: data
    }