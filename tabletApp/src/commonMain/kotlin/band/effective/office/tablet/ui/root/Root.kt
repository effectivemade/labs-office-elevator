package band.effective.office.tablet.ui.root

import androidx.compose.runtime.Composable
import band.effective.office.tablet.ui.freeNegotiationsScreen.ui.freeNegotiationsScreen.FreeNegotiationsScreen
import band.effective.office.tablet.ui.mainScreen.mainScreen.MainScreen
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children

@Composable
fun Root(component: RootComponent) {
    Children(
        stack = component.childStack
    ) { child ->
        when (val instance = child.instance) {
            is RootComponent.Child.MainChild -> MainScreen(instance.component)
            is RootComponent.Child.SelectRoomChild -> FreeNegotiationsScreen(instance.component)
        }
    }
}