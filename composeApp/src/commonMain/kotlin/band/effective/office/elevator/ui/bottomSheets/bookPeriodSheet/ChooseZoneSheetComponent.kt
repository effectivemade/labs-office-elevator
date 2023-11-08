package band.effective.office.elevator.ui.bottomSheets.bookPeriodSheet

import androidx.compose.runtime.Composable
import band.effective.office.elevator.MainRes
import band.effective.office.elevator.ui.booking.components.modals.ChooseZone
import band.effective.office.elevator.ui.booking.models.WorkSpaceType
import band.effective.office.elevator.ui.booking.models.WorkspaceZoneUI
import band.effective.office.elevator.ui.booking.store.BookingStore
import band.effective.office.elevator.ui.bottomSheets.BottomSheet
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.compose.stringResource

class ChooseZoneSheetComponent(
    private val sheetTile: StringResource,
    private val workSpacecZone: List<WorkspaceZoneUI>,
) : BottomSheet {
    @Composable
    override fun SheetContent() {
        ChooseZone(
            sheetTile = stringResource(sheetTile),
            workSpacecZone = workSpacecZone,
            onClickCloseChoseZone = { },
            onClickConfirmSelectedZone = {}
        )
    }
}