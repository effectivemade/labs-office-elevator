package band.effective.office.elevator.ui.booking.models

object MockDataSpaces {
    val allBookingZone = listOf(
        WorkspaceZoneUI(name = "Sirius", isSelected = true),
        WorkspaceZoneUI(name = "Antares", isSelected = true),
        WorkspaceZoneUI(name = "Mars", isSelected = true),
        WorkspaceZoneUI(name = "Cassiopeia", isSelected = true),
        WorkspaceZoneUI(name = "Arrakis", isSelected = true),
    )

    val allMeetingRooms = listOf(
        WorkspaceZoneUI(name = "Moon", isSelected = true),
        WorkspaceZoneUI(name = "Sun", isSelected = true),
        WorkspaceZoneUI(name = "Mercury", isSelected = true),
        WorkspaceZoneUI(name = "Pluto", isSelected = true),
        WorkspaceZoneUI(name = "Call box", isSelected = true)
    )

    val workSpacesUI = listOf(
        WorkSpaceUI(
            workSpaceId = "",
            workSpaceName = "Cassiopeia",
            workSpaceType = WorkSpaceType.WORK_PLACE,
            zoneName = "Cassiopeia"
        ),
        WorkSpaceUI(
            workSpaceId = "",
            workSpaceName = "Arrakis",
            workSpaceType = WorkSpaceType.WORK_PLACE,
            zoneName = "Arrakis"
        ),
        WorkSpaceUI(
            workSpaceId = "",
            workSpaceName = "Mars",
            workSpaceType = WorkSpaceType.WORK_PLACE,
            zoneName = "Mars"
        ),
        WorkSpaceUI(
            workSpaceId = "",
            workSpaceName = "Antares",
            workSpaceType = WorkSpaceType.WORK_PLACE,
            zoneName = "Antares"
        ),
        WorkSpaceUI(
            workSpaceId = "",
            workSpaceName = "Sirius",
            workSpaceType = WorkSpaceType.WORK_PLACE,
            zoneName = "Sirius"
        ),

        WorkSpaceUI(
            workSpaceId = "",
            workSpaceName = "Moon",
            workSpaceType = WorkSpaceType.MEETING_ROOM,
            zoneName = "Moon"

        ),
        WorkSpaceUI(
            workSpaceId = "",
            workSpaceName = "Call box",
            workSpaceType = WorkSpaceType.MEETING_ROOM,
            zoneName = "Call box"
        ),
        WorkSpaceUI(
            workSpaceId = "",
            workSpaceName = "Sun",
            workSpaceType = WorkSpaceType.MEETING_ROOM,
            zoneName = "Sun"

        ),
        WorkSpaceUI(
            workSpaceId = "",
            workSpaceName = "Mercury",
            workSpaceType = WorkSpaceType.MEETING_ROOM,
            zoneName = "Mercury"
        ),
        WorkSpaceUI(
            workSpaceId = "",
            workSpaceName = "Pluto",
            workSpaceType = WorkSpaceType.MEETING_ROOM,
            zoneName = "Pluto"

        )
    )
}