package band.effective.office.tablet

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import band.effective.office.tablet.ui.root.Root
import band.effective.office.tablet.ui.root.RootComponent
import com.arkivanov.decompose.ComponentContext

@Composable
fun App(componentContext: ComponentContext) {
    val rootComponent = RootComponent(componentContext)
    MaterialTheme {
        Root(rootComponent)
    }
}
