package band.effective.office.tablet.ui.freeNegotiationsScreen.ui.freeNegotiationsScreen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import band.effective.office.tablet.ui.common.ErrorMainScreen
import band.effective.office.tablet.ui.freeNegotiationsScreen.ui.freeNegotiationsScreen.uiComponents.PanelView

@RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
@Composable
fun FreeNegotiationsErrorView(nameRoomCurrent: String, onMainScreen: (reset: Boolean) -> Unit) {
    Column {
        PanelView(
            modifier = Modifier
                .fillMaxHeight(0.2f)
                .background(MaterialTheme.colors.surface),
            nameRoom = nameRoomCurrent,
            onMainScreen = onMainScreen
        )
        ErrorMainScreen { onMainScreen(true) }
    }
}