package band.effective.office.tablet.ui.selectRoomScreen

data class SelectRoomScreenState(
    val isLoad: Boolean,
    val isData: Boolean,
    val isError: Boolean,
    val error: String
){
    companion object {
        val defaultState =
            SelectRoomScreenState(
                isLoad = false,
                isData = true,
                isError = false,
                error = ""
            )
    }
}
