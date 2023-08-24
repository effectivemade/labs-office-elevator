package band.effective.office.elevator.ui.booking.models

data class WorkSpaceUI(
    val workSpaceId: String,
    val workSpaceName: String,
    val workSpaceType: WorkSpaceType
)

enum class WorkSpaceType( val type: String){
    MEETING_ROOM("meeting"),
    WORK_PLACE("regular")
}