package band.effective.office.elevator.common.compose.expects

import androidx.compose.foundation.layout.displayCutoutPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.ui.Modifier

internal actual fun Modifier.notchPadding(): Modifier = displayCutoutPadding().systemBarsPadding()
