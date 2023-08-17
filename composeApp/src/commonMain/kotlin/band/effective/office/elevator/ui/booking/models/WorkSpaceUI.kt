package band.effective.office.elevator.ui.booking.models

data class WorkSpaceUI(
    val workSpaceId: String,
    val workSpaceName: String,
    val workSpaceType: WorkSpaceType
)

enum class WorkSpaceType{
    MEETING_ROOM,
    WORK_PLACE
}