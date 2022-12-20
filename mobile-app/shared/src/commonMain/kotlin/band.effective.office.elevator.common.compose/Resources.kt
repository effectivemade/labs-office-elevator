package band.effective.office.elevator.common.compose

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap

@Composable
internal expect fun imageResource(id: String): ImageBitmap
