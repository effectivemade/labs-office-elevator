package band.effective.office.tv.core.network

sealed class Resource<T> {
    data class Data<T> (val data: T) : Resource<T>()
    data class Error<T> (val error: String): Resource<T>()
    class Loading<T>: Resource<T>()
}