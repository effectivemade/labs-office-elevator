package band.effective.office.elevator.ui.booking.components.modals

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import band.effective.office.elevator.ExtendedTheme
import band.effective.office.elevator.MainRes
import band.effective.office.elevator.components.PrimaryButton
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun DeleteBooking(place: String, fullDate: String, onMainCLick: () -> Unit, onDeleteClick: () -> Unit) {
    val elevation = ButtonDefaults.elevation(
        defaultElevation = 0.dp,
        pressedElevation = 0.dp,
        disabledElevation = 0.dp,
        hoveredElevation = 0.dp,
        focusedElevation = 0.dp
    )

    Column(
        verticalArrangement = Arrangement.spacedBy(32.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(shape = RoundedCornerShape(size = 16.dp))
            .background(color = Color.White)
            .padding(start = 16.dp, top = 24.dp, end = 16.dp, bottom = 24.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = stringResource(
                    resource = MainRes.strings.delete_booking,
                ),
                style = MaterialTheme.typography.h6.merge(
                    other = TextStyle(
                        fontWeight = FontWeight(
                            weight = 500
                        )
                    )
                ),
                textAlign = TextAlign.Center
            )

            Text(
                text = place,
                style = MaterialTheme.typography.body1.merge(
                    other = TextStyle(
                        color = ExtendedTheme.colors._66x
                    )
                ),
                textAlign = TextAlign.Center
            )

            Text(
                text = fullDate,
                style = MaterialTheme.typography.body1.merge(
                    other = TextStyle(
                        color = ExtendedTheme.colors._66x
                    )
                ),
                textAlign = TextAlign.Center
            )
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top),
            horizontalAlignment = Alignment.Start,
        ) {
            PrimaryButton(
                text = stringResource(resource = MainRes.strings.move_to_main_from_booking),
                cornerValue = 40.dp,
                contentTextSize = 16.sp,
                paddingValues = PaddingValues(all = 10.dp),
                elevation = elevation,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = ExtendedTheme.colors.trinidad_600,
                    contentColor = Color.White
                ),
                onButtonClick = onMainCLick
            )
            PrimaryButton(
                text = stringResource(resource = MainRes.strings.delete),
                cornerValue = 40.dp,
                contentTextSize = 16.sp,
                paddingValues = PaddingValues(horizontal = 16.dp, vertical = 10.dp),
                elevation = elevation,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.background,
                    contentColor = ExtendedTheme.colors.trinidad_700
                ),
                border = BorderStroke(
                    width = 2.dp,
                    color = ExtendedTheme.colors.trinidad_700
                ),
                onButtonClick = onDeleteClick
            )
        }
    }
}