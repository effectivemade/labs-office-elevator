package band.effective.office.tv.model.domain

sealed class Resource<T> {
    data class Data<T> (val data: T) : Resource<T>()
    data class Error<T> (val error: String): Resource<T>()
}