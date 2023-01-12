package band.effective.office.elevator.common.compose

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import band.effective.office.elevator.common.compose.helpers.LastOpenActivityProvider

@Composable
internal actual fun imageResource(id: String): ImageBitmap {
    val context = Android.applicationContext
    val id = context.resources.getIdentifier(id, "drawable", context.packageName)
    return ImageBitmap.Companion.imageResource(id = id)
}

object Android {
    lateinit var applicationContext: Context
    val activity = LastOpenActivityProvider.lastOpenedActivity()
}
