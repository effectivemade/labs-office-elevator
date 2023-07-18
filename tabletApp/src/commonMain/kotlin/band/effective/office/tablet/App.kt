package band.effective.office.tablet

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import band.effective.office.tablet.ui.root.Root
import band.effective.office.tablet.ui.root.RootComponent
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.store.StoreFactory

@Composable
fun App(componentContext: ComponentContext, storeFactory: StoreFactory) {
    val rootComponent = RootComponent(componentContext, storeFactory)
    MaterialTheme {
        Root(rootComponent)
    }
}
