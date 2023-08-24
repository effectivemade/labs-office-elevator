package band.effective.office.elevator.ui.booking.models

object MockDataSpaces {
    val allBookingZone = listOf(
        WorkSpaceZone(name = "Sirius", isSelected = true),
        WorkSpaceZone(name = "Antares", isSelected = true),
        WorkSpaceZone(name = "Mars", isSelected = true),
        WorkSpaceZone(name = "Cassiopeia", isSelected = true),
        WorkSpaceZone(name = "Arrakis", isSelected = true),
    )

    val allMeetingRooms = listOf(
        WorkSpaceZone(name = "Moon", isSelected = true),
        WorkSpaceZone(name = "Sun", isSelected = true),
        WorkSpaceZone(name = "Mercury", isSelected = true),
        WorkSpaceZone(name = "Pluto", isSelected = true),
        WorkSpaceZone(name = "Call box", isSelected = true)
    )

    val workSpacesUI = listOf(
        WorkSpaceUI(
            workSpaceId = "",
            workSpaceName = "Cassiopeia",
            workSpaceType = WorkSpaceType.WORK_PLACE
        ),
        WorkSpaceUI(
            workSpaceId = "",
            workSpaceName = "Arrakis",
            workSpaceType = WorkSpaceType.WORK_PLACE
        ),
        WorkSpaceUI(
            workSpaceId = "",
            workSpaceName = "Mars",
            workSpaceType = WorkSpaceType.WORK_PLACE
        ),
        WorkSpaceUI(
            workSpaceId = "",
            workSpaceName = "Antares",
            workSpaceType = WorkSpaceType.WORK_PLACE
        ),
        WorkSpaceUI(
            workSpaceId = "",
            workSpaceName = "Sirius",
            workSpaceType = WorkSpaceType.WORK_PLACE
        ),

        WorkSpaceUI(
            workSpaceId = "",
            workSpaceName = "Moon",
            workSpaceType = WorkSpaceType.MEETING_ROOM
        ),
        WorkSpaceUI(
            workSpaceId = "",
            workSpaceName = "Call box",
            workSpaceType = WorkSpaceType.MEETING_ROOM
        ),
        WorkSpaceUI(
            workSpaceId = "",
            workSpaceName = "Sun",
            workSpaceType = WorkSpaceType.MEETING_ROOM
        ),
        WorkSpaceUI(
            workSpaceId = "",
            workSpaceName = "Mercury",
            workSpaceType = WorkSpaceType.MEETING_ROOM
        ),
        WorkSpaceUI(
            workSpaceId = "",
            workSpaceName = "Pluto",
            workSpaceType = WorkSpaceType.MEETING_ROOM
        )
    )
}