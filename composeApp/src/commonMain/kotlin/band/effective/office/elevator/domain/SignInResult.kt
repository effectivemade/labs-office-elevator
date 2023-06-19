package band.effective.office.elevator.domain

interface SignInResultCallback {
    fun onSuccess()
    fun onFailure(message: String)
}
