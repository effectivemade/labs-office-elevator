package band.effective.office.elevator.common.compose.screens.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import band.effective.office.elevator.common.compose.SafeArea
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

    Scaffold(bottomBar = {
        BottomNavigation(backgroundColor = MaterialTheme.colors.background) {
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
    }) { innerPadding ->
        NavHost(
            navigator = navigator,
            initialRoute = NavRoute.Main.Home.route,
            modifier = Modifier.padding(innerPadding)
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
}