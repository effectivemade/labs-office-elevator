package band.effective.office.tablet

import androidx.compose.runtime.Composable
import band.effective.office.tablet.ui.root.Root
import band.effective.office.tablet.ui.root.RootComponent
import band.effective.office.tablet.ui.theme.AppTheme
import com.arkivanov.decompose.ComponentContext

@Composable
fun App(componentContext: ComponentContext) {
    val rootComponent = RootComponent(componentContext)
    AppTheme {
        Root(rootComponent)
    }
}
