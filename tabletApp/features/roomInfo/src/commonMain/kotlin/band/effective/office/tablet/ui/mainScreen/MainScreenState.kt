package band.effective.office.tablet.ui.mainScreen

data class MainScreenState(
    val platform: String
){
    companion object{
        val defaultState = MainScreenState(platform = "world")
    }
}
