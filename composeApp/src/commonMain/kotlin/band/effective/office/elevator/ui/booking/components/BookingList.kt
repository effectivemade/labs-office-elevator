package band.effective.office.elevator.ui.booking.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import band.effective.office.elevator.ui.booking.models.WorkSpaceUI

@Composable
fun WorkSpaceList(
    scrollState: LazyListState,
    workSpaces: List<WorkSpaceUI>,
    onClickOpenBookAccept: (WorkSpaceUI) -> Unit
) {
    LazyColumn(
        modifier = Modifier.background(MaterialTheme.colors.onBackground)
            .padding(horizontal = 16.dp),
        state = scrollState
    ) {
        items(workSpaces) { workSpace ->
            BookingCard(
                workSpace = workSpace,
                onClickOpenBookAccept = onClickOpenBookAccept
            )
        }
    }
}