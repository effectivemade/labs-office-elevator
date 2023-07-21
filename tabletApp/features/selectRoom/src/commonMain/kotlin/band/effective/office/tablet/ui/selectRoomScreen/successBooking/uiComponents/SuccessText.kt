package band.effective.office.tablet.ui.selectRoomScreen.successBooking.uiComponents

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import band.effective.office.tablet.features.selectRoom.MainRes
import band.effective.office.tablet.ui.theme.LocalCustomColorsPalette
import band.effective.office.tablet.utils.date
import band.effective.office.tablet.utils.time24

@Composable
fun SuccessText(modifier: Modifier, nameRoom: String) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = MainRes.string.success_text.format(
                nameRoom = nameRoom
            ),
            style = MaterialTheme.typography.h4,
            color = LocalCustomColorsPalette.current.onSuccess
        )
    }
}