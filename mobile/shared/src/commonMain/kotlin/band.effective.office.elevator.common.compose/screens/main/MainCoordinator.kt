package band.effective.office.elevator.common.compose.screens.main

import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import band.effective.office.elevator.common.compose.expects.ImageVector
import band.effective.office.elevator.common.compose.navigation.NavRoute
import band.effective.office.elevator.common.compose.navigation.bottom.BottomNavItems
import band.effective.office.elevator.common.compose.screens.home.ElevatorScreen
import band.effective.office.elevator.common.compose.screens.profile.ProfileScreen
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.NavOptions
import moe.tlaster.precompose.navigation.PopUpTo
import moe.tlaster.precompose.navigation.rememberNavigator

@Composable
internal fun MainCoordinator(onBack: () -> Unit) {
    val navigator = rememberNavigator()
    var selectedItem by remember { mutableStateOf("") }
    BottomNavigation {
        BottomNavItems.values().forEach { item ->
            BottomNavigationItem(
                label = {
                    Text(item.label)
                },
                icon = {
                    ImageVector(
                        item.imageResource,
                        modifier = Modifier.size(32.dp)
                    )
                },
                selected = selectedItem == item.linkedRoute.route,
                onClick = {
                    navigator.navigate(
                        route = item.linkedRoute.route,
                        NavOptions(
                            launchSingleTop = true,
                            popUpTo = PopUpTo.First(true)
                        ),
                    )
                }
            )
        }
    }

    NavHost(
        navigator = navigator,
        initialRoute = NavRoute.Main.Home.route
    ) {
        scene(NavRoute.Main.Home.route) {
            selectedItem = it.route.route
            ElevatorScreen(onSignOut = onBack)
        }
        scene(NavRoute.Main.Profile.route) {
            selectedItem = it.route.route
            ProfileScreen()
        }
    }
}