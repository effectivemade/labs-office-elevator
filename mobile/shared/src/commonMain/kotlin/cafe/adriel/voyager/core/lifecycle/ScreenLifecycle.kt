package cafe.adriel.voyager.core.lifecycle

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import cafe.adriel.voyager.core.screen.Screen

@Composable
internal fun Screen.LifecycleEffect(
    onStarted: () -> Unit = {},
    onDisposed: () -> Unit = {}
) {
    DisposableEffect(key) {
        onStarted()
        onDispose(onDisposed)
    }
}

@Composable
internal fun rememberScreenLifecycleOwner(
    screen: Screen
): ScreenLifecycleOwner =
    remember(screen.key) {
        when (screen) {
            is ScreenLifecycleProvider -> screen.getLifecycleOwner()
            else -> DefaultScreenLifecycleOwner
        }
    }

internal interface ScreenLifecycleProvider {

    fun getLifecycleOwner(): ScreenLifecycleOwner
}
