package band.effective.office.tv.screen.autoplay

import androidx.compose.runtime.Composable
import band.effective.office.tv.core.ui.autoplay.AutoplayableViewModel
import band.effective.office.tv.screen.navigation.Screen

data class ScreenDescription(
    val screenName: Screen,
    val screen: @Composable () -> Unit,
    val viewModel: AutoplayableViewModel
)