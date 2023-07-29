package band.effective.office.tablet.ui.freeNegotiationsScreen.ui.freeNegotiationsScreen.uiComponents

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import band.effective.office.tablet.domain.model.RoomInfo
import band.effective.office.tablet.features.freeNegotiationsScreen.MainRes
import band.effective.office.tablet.ui.theme.LocalCustomColorsPalette
import band.effective.office.tablet.ui.theme.h7

@Composable
fun ButtonBookingView(
    modifier: Modifier,
    roomInfo: RoomInfo
){
    val enabled = roomInfo.currentEvent == null
    Button(
        modifier = modifier,
        onClick = {

        },
        elevation = ButtonDefaults.elevation(0.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.background,
            contentColor = MaterialTheme.colors.primary,
            disabledBackgroundColor = MaterialTheme.colors.background,
            disabledContentColor = LocalCustomColorsPalette.current.disabledPrimaryButton
        ),
        shape = RoundedCornerShape(100.dp),
        enabled = enabled
    ) {
        Text(
            text = MainRes.string.occupy,
            style = MaterialTheme.typography.h7
        )
    }
}