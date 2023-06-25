package band.effective.office.tv.domain.autoplay.model

import androidx.compose.runtime.Composable
import band.effective.office.tv.domain.autoplay.AutoplayableViewModel
import band.effective.office.tv.screen.navigation.Screen

data class ScreenDescription(
    val screenName: Screen, val screen: @Composable () -> Unit, val viewModel: AutoplayableViewModel
)