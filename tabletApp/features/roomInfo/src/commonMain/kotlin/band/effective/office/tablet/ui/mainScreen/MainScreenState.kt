package band.effective.office.tablet.ui.mainScreen

import band.effective.office.tablet.domain.model.RoomInfo


data class MainScreenState(
    val isLoad: Boolean,
    val isData: Boolean,
    val isError: Boolean,
    val roomInfo: RoomInfo,
    val error: String
) {
    companion object {
        val defaultState =
            MainScreenState(
                isLoad = true,
                isData = false,
                isError = false,
                roomInfo = RoomInfo.defaultValue,
                error = ""
            )
    }
}
