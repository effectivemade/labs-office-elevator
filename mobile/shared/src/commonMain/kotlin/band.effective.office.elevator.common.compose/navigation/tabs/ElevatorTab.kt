package band.effective.office.elevator.common.compose.navigation.tabs

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import band.effective.office.elevator.common.compose.navigation.tabs.internal.Tab
import band.effective.office.elevator.common.compose.navigation.tabs.internal.TabOptions
import band.effective.office.elevator.common.compose.screens.home.ElevatorScreen
import band.effective.office.elevator.common.compose.screens.home.ElevatorScreenViewModel
import cafe.adriel.voyager.core.model.rememberScreenModel

internal object ElevatorTab : Tab {

    override val options: TabOptions
        @Composable
        get() {
            val title = "Main"
            val icon = rememberVectorPainter(Icons.Default.Home)

            return remember {
                TabOptions(
                    index = 0u,
                    title = title,
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        val viewModel: ElevatorScreenViewModel = rememberScreenModel { ElevatorScreenViewModel() }
        ElevatorScreen(viewModel)
    }
}