package cafe.adriel.voyager.core.screen

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.platform.multiplatformName

internal expect interface Screen {

    open val key: ScreenKey

    @Composable
    fun Content()
}


internal fun Screen.commonKeyGeneration() =
    this::class.multiplatformName
        ?: error("Default ScreenKey not found, please provide your own key")
