package band.effective.office.elevator.domain.models

data class User(
    val id: String,
    val imageUrl: String,
    val userName: String,
    val post:String,
    val phoneNumber:String,
    val telegram: String,
    val email:String
){
    companion object {
        val defaultUser =
            User(
                id = "",
                imageUrl = "",
                userName = "",
                post = "",
                phoneNumber = "",
                telegram = "",
                email = ""
            )
    }
}
