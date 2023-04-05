package cafe.adriel.voyager.core.lifecycle.main

import androidx.compose.material.BottomNavigation
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import band.effective.office.elevator.common.compose.components.TabNavigationItem
import band.effective.office.elevator.common.compose.expects.notchPadding
import band.effective.office.elevator.common.compose.navigation.tabs.ElevatorTab
import band.effective.office.elevator.common.compose.navigation.tabs.ProfileTab
import band.effective.office.elevator.common.compose.navigation.tabs.internal.CurrentTab
import band.effective.office.elevator.common.compose.navigation.tabs.internal.TabNavigator
import cafe.adriel.voyager.core.screen.Screen
import io.github.aakira.napier.Napier

internal class MainScreenWrapper : Screen {
    @Composable
    override fun Content() {
        TabNavigator(
            ElevatorTab,
            disposeNestedNavigators = true
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