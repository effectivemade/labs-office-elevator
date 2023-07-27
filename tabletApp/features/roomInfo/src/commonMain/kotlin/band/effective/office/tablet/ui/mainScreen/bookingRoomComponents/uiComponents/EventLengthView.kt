package band.effective.office.tablet.ui.mainScreen.bookingRoomComponents.uiComponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import band.effective.office.tablet.features.roomInfo.MainRes
import band.effective.office.tablet.ui.mainScreen.bookingRoomComponents.RealEventLengthComponent
import band.effective.office.tablet.ui.theme.LocalCustomColorsPalette
import band.effective.office.tablet.ui.theme.h8

@Composable
fun EventLengthView(
    modifier: Modifier = Modifier,
    component: RealEventLengthComponent,
    currentLength: Int,
    isBusy: Boolean
) {
    val space = 50.dp
    Column(modifier = modifier) {
        Text(
            text = MainRes.string.select_length_title,
            color = LocalCustomColorsPalette.current.secondaryTextAndIcon,
            style = MaterialTheme.typography.h8
        )
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                modifier = Modifier.fillMaxHeight().weight(1f).clip(RoundedCornerShape(15.dp)),
                onClick = { component.decrement() },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = LocalCustomColorsPalette.current.elevationBackground
                )
            ) {
                Text(
                    text = MainRes.string.minus_date_button_string,
                    style = MaterialTheme.typography.h6,
                    color = LocalCustomColorsPalette.current.primaryTextAndIcon
                )
            }
            Spacer(modifier = Modifier.width(space))
            Text(
                text = MainRes.string.current_length_string.format(currentLength.toString()),
                color = LocalCustomColorsPalette.current.primaryTextAndIcon,
                style = MaterialTheme.typography.h4
            )
            Spacer(modifier = Modifier.width(space))
            Button(
                modifier = Modifier.fillMaxHeight().weight(1f).clip(RoundedCornerShape(15.dp)),
                onClick = {
                    component.increment() },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = LocalCustomColorsPalette.current.elevationBackground
                )
            ) {
                Text(
                    text = MainRes.string.plus_date_button_string,
                    style = MaterialTheme.typography.h6,
                    color = LocalCustomColorsPalette.current.primaryTextAndIcon
                )
            }
        }
    }

}