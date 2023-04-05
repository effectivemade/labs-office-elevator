package band.effective.office.elevator.common.compose.navigation.tabs.internal

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.NavigatorDisposeBehavior

internal typealias TabNavigatorContent = @Composable (tabNavigator: TabNavigator) -> Unit

internal val LocalTabNavigator: ProvidableCompositionLocal<TabNavigator> =
    staticCompositionLocalOf { error("TabNavigator not initialized") }

@Composable
internal fun TabNavigator(
    tab: Tab,
    disposeNestedNavigators: Boolean = false,
    content: TabNavigatorContent = { CurrentTab() }
) {
    Navigator(
        screen = tab,
        disposeBehavior = NavigatorDisposeBehavior(
            disposeNestedNavigators = disposeNestedNavigators,
            disposeSteps = true
        ),
        onBackPressed = null
    ) { navigator ->
        val tabNavigator = remember(navigator) {
            TabNavigator(navigator)
        }

        CompositionLocalProvider(LocalTabNavigator provides tabNavigator) {
            content(tabNavigator)
        }
    }
}

internal class TabNavigator internal constructor(
    private val navigator: Navigator
) {

    public var current: Tab
        get() = navigator.lastItem as Tab
        set(tab) = navigator.replaceAll(tab)

    @Composable
    public fun saveableState(
        key: String,
        tab: Tab = current,
        content: @Composable () -> Unit
    ) {
        navigator.saveableState(key, tab, content = content)
    }
}