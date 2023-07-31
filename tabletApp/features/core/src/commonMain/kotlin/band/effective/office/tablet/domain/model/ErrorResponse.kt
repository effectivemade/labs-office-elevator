package network.model

data class ErrorResponse(val code: Int, val description: String) {
    companion object {
        fun getResponse(code: Int): ErrorResponse {
            val description = when (code) {
                400, 404 -> "Pet not found"
                in 500..599 -> "Server error"
                else -> "Unknown error"
            }
            return ErrorResponse(code, description)
        }
    }
}