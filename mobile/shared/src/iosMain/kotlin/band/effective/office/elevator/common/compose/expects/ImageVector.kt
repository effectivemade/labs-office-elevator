package band.effective.office.elevator.common.compose.expects

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import band.effective.office.elevator.common.compose.imageResource

@Composable
internal actual fun ImageVector(id: String, modifier: Modifier, tint: Color?) {
    Image(
        bitmap = imageResource(id),
        contentDescription = null,
        colorFilter = if (tint != null) ColorFilter.tint(tint) else null
    )
}