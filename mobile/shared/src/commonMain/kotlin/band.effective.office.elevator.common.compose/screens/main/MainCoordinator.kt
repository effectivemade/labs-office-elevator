package band.effective.office.elevator.common.compose.screens.main

import androidx.compose.material.BottomNavigation
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import band.effective.office.elevator.common.compose.components.TabNavigationItem
import band.effective.office.elevator.common.compose.expects.notchPadding
import band.effective.office.elevator.common.compose.navigation.tabs.ElevatorTab
import band.effective.office.elevator.common.compose.navigation.tabs.ProfileTab
import band.effective.office.elevator.common.compose.navigation.tabs.internal.CurrentTab
import band.effective.office.elevator.common.compose.navigation.tabs.internal.TabNavigator
import cafe.adriel.voyager.core.screen.Screen

internal class MainScreenWrapper : Screen {
    @Composable
    override fun Content() {
        TabNavigator(
            ElevatorTab,
        ) {
            Scaffold(
                modifier = Modifier.notchPadding(),
                content = {
                    CurrentTab()
                },
                bottomBar = {
                    BottomNavigation {
                        TabNavigationItem(ElevatorTab)
                        TabNavigationItem(ProfileTab)
                    }
                }
            )
        }
    }
}