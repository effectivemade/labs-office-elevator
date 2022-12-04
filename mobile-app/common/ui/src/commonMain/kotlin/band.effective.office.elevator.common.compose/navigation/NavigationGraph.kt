package band.effective.office.elevator.common.compose.navigation

import band.effective.office.elevator.common.compose.NavigationTree
import band.effective.office.elevator.common.compose.screens.home.HomeScreen
import ru.alexgladkov.odyssey.compose.extensions.screen
import ru.alexgladkov.odyssey.compose.navigation.RootComposeBuilder

fun RootComposeBuilder.navigationGraph() {
    screen(NavigationTree.Actions.name) {
        HomeScreen()
    }
}
