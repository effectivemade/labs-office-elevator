package band.effective.office.elevator.common.compose.expects

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import band.effective.office.elevator.common.compose.imageResource

@Composable
internal actual fun ImageVector(id: String, modifier: Modifier) {
    Image(bitmap = imageResource(id), contentDescription = null)
}