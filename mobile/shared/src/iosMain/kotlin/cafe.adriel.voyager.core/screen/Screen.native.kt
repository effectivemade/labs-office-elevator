package cafe.adriel.voyager.core.screen

import androidx.compose.runtime.Composable

internal actual interface Screen {
    actual val key: ScreenKey
        get() = commonKeyGeneration()

    @Composable
    actual fun Content()
}
