package band.effective.office.elevator.common.compose.screens.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.material.BottomNavigation
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import band.effective.office.elevator.common.compose.SafeArea
import band.effective.office.elevator.common.compose.components.TabNavigationItem
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
            disposeNestedNavigators = true
        ) {
            Scaffold(
                modifier = Modifier,
                content = {
                    CurrentTab()
                },
                bottomBar = {

                    Box(modifier = Modifier.background(MaterialTheme.colors.primary)) {
                        BottomNavigation(
                            modifier = Modifier.height(55.dp + SafeArea.current.value.calculateBottomPadding())
                        ) {
                            TabNavigationItem(ElevatorTab)
                            TabNavigationItem(ProfileTab)
                        }
                    }
                }
            )
        }
    }
}