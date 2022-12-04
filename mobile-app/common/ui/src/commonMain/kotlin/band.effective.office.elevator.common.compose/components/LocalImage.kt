package band.effective.office.elevator.common.compose.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
expect fun LocalImage(
    imageResourceName: String,
    modifier: Modifier,
    contentDescription: String?
)