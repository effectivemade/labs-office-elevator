package cafe.adriel.voyager.core.screen

import androidx.compose.runtime.Composable
import java.io.Serializable

internal actual interface Screen : Serializable {
    public actual val key: ScreenKey
        get() = commonKeyGeneration()

    @Composable
    public actual fun Content()
}
