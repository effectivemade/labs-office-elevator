package cafe.adriel.voyager.core.registry

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import cafe.adriel.voyager.core.screen.Screen

@Composable
internal inline fun <reified T : ScreenProvider> rememberScreen(provider: T): Screen =
    remember(provider) {
        ScreenRegistry.get(provider)
    }

internal interface ScreenProvider
