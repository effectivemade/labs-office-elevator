package band.effective.office.tablet.ui.freeNegotiationsScreen.ui.freeNegotiationsScreen.uiComponents

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import band.effective.office.tablet.ui.mainScreen.roomInfoComponents.uiComponent.DateTimeComponent

@RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
@Composable
fun PanelView(
    modifier: Modifier,
    nameRoom: String,
    onMainScreen: (reset: Boolean) -> Unit
) {
    Box(
        modifier = modifier
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            DateTimeComponent(
                modifier = Modifier
                    .padding(start = 25.dp, top = 25.dp)
            )
            ButtonBackOnMain(
                modifier = Modifier
                    .fillMaxWidth(0.2f)
                    .padding(start = 25.dp),
                nameRoom = nameRoom,
                onMainScreen = onMainScreen
            )
        }
    }
}