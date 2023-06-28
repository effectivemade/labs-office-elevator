package band.effective.office.tablet

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import band.effective.office.tablet.ui.mainScreen.MainComponent
import band.effective.office.tablet.ui.mainScreen.MainScreen
import band.effective.office.tablet.ui.root.Root
import band.effective.office.tablet.ui.root.RootComponent
import band.effective.office.tablet.ui.selectRoomScreen.SelectRoomComponent
import band.effective.office.tablet.ui.selectRoomScreen.SelectRoomScreen
import com.arkivanov.decompose.ComponentContext

@Composable
fun App(componentContext: ComponentContext) {
    val rootComponent = RootComponent(componentContext)
    MaterialTheme {
        Root(rootComponent)
    }
}
