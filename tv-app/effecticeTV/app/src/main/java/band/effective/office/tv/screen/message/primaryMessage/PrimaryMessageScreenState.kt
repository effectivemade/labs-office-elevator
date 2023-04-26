package band.effective.office.tv.screen.message.primaryMessage

data class PrimaryMessageScreenState(
    val isEmpty: Boolean,
    val currentMessage: String
){
    companion object{
        val empty = PrimaryMessageScreenState(true,"")
    }
}
