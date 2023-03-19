package band.effective.office.elevator.common.compose.expects

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import band.effective.office.elevator.common.compose.Android

@Composable
internal actual fun ImageVector(id: String, modifier: Modifier, tint: Color?) {
    val context = Android.applicationContext
    val id = context.resources.getIdentifier(id, "drawable", context.packageName)
    Image(
        modifier = modifier,
        imageVector = ImageVector.vectorResource(id),
        contentDescription = null,
        colorFilter = if (tint != null) ColorFilter.tint(tint) else null
    )
}