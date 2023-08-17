package band.effective.office.elevator.ui.booking.components.modals

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import band.effective.office.elevator.ExtendedThemeColors
import band.effective.office.elevator.MainRes
import band.effective.office.elevator.components.Elevation
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun BookingContextMenu(onClick: (Int) -> Unit) {
    val dropDownList =
        listOf(
            MainRes.strings.show_map,
            MainRes.strings.extend_booking,
            MainRes.strings.repeat_booking,
            MainRes.strings.delete_booking
        )

    Column(modifier = Modifier
            .padding(end = 24.dp, bottom = 24.dp)
            .clip(shape = RoundedCornerShape(8.dp))
            .background(color = Color.White)
        .wrapContentSize()
    ) {
        for (item in dropDownList) {
            with(stringResource(item)) {
            Column(modifier = Modifier.wrapContentSize()) {
                Button(
                    onClick = {
                        onClick(dropDownList.indexOf(item))
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Transparent,
                        contentColor = MaterialTheme.colors.secondary
                    ),
                    elevation = Elevation()
                ) {
                    Text(text = this@with,
                        modifier = Modifier.wrapContentSize())
                }
                if (!dropDownList.indexOf(item).equals(dropDownList.last()))
                    Divider(
                        modifier = Modifier.height(1.dp)
                            .background(color = ExtendedThemeColors.colors._66x)
                    )
            }
            }
        }
    }
}