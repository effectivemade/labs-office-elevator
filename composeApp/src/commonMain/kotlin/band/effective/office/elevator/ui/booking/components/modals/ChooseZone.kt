package band.effective.office.elevator.ui.booking.components.modals

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Done
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import band.effective.office.elevator.ExtendedThemeColors
import band.effective.office.elevator.MainRes
import band.effective.office.elevator.components.EffectiveButton
import band.effective.office.elevator.textInBorderGray
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun ChooseZone(zone: Boolean, onClickCloseChoseZone: () -> Unit) {
    val highZonesNames = listOf(
        MainRes.strings.sirius_zone,
        MainRes.strings.antares_zone,
        MainRes.strings.mars_zone
    )
    val lowZonesNames = listOf(
        MainRes.strings.cassiopeia_zone,
        MainRes.strings.arrakis_zone
    )
    val highRoomsNames = listOf(
        MainRes.strings.moon_room,
        MainRes.strings.sun_room,
        MainRes.strings.mercury_room,
        MainRes.strings.pluto_room
    )
    val lowRoomsNames = listOf(
        MainRes.strings.pluto_room
    )
    val highListNames: List<StringResource>
    val lowListNames: List<StringResource>
    if (zone) {
        highListNames = highZonesNames
        lowListNames = lowZonesNames
    } else {
        highListNames = highRoomsNames
        lowListNames = lowRoomsNames
    }
    Column(modifier = Modifier.fillMaxWidth().background(Color.White)) {
        Spacer(modifier = Modifier.padding(vertical = 10.dp))
        Divider(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth(fraction = .3f)
                .height(4.dp)
                .background(
                    color = ExtendedThemeColors.colors.dividerColor,
                    shape = RoundedCornerShape(size = 16.dp)
                )
                .padding(
                    bottom = 8.dp
                )
        )
        Row(modifier = Modifier.padding(top = 10.dp, start = 16.dp, end = 16.dp)) {
            IconButton(
                onClick = onClickCloseChoseZone,
                modifier = Modifier
                    .align(Alignment.Top)
            ) {
                Icon(
                    imageVector = Icons.Rounded.Close,
                    contentDescription = "Krestik",
                    tint = Color.Black
                )
            }
            Text(
                text = stringResource(
                    if (zone) MainRes.strings.selection_zones
                    else MainRes.strings.selection_rooms
                ),
                style = MaterialTheme.typography.subtitle1,
                fontSize = 20.sp,
                fontWeight = FontWeight(600),
                color = Color.Black,
                modifier = Modifier.padding(vertical = 10.dp)
            )
        }
        Divider(
            modifier = Modifier
                .fillMaxWidth(fraction = 1.0f)
                .height(height = 1.dp)
                .background(
                    color = ExtendedThemeColors.colors._66x
                )
        )
        Column(
            modifier = Modifier.padding(
                top = 16.dp,
                bottom = 6.dp,
                start = 16.dp
            ).fillMaxWidth()
        ) {
            LazyRow {
                items(highListNames) { highListName ->
                    WorkingZones(highListName)
                }
            }
            LazyRow {
                items(lowListNames) { lowListName ->
                    WorkingZones(lowListName)
                }
            }
        }
        Spacer(modifier  = Modifier.height(18.dp))
        EffectiveButton(
            buttonText = stringResource(MainRes.strings.confirm_booking),
            onClick = onClickCloseChoseZone,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp).padding(bottom = 11.dp)
        )
    }
}

@Composable
fun WorkingZones(text: StringResource) {
    var isExpanded by remember { mutableStateOf(false) }
    Button(
        onClick = { isExpanded = !isExpanded },
        colors = ButtonDefaults.buttonColors(ExtendedThemeColors.colors.whiteColor),
        modifier = Modifier
            .padding(end = 10.dp)
            .fillMaxWidth(fraction = 0.5f),
        border = BorderStroke(
            width = 1.dp,
            color = if (!isExpanded) ExtendedThemeColors.colors.purple_heart_800 else textInBorderGray
        ),
        shape = RoundedCornerShape(12.dp),
        elevation = ButtonDefaults.elevation(0.dp, 2.dp, 0.dp)
    ) {
        Row {
            Icon(
                imageVector = Icons.Rounded.Done,
                tint = ExtendedThemeColors.colors.purple_heart_800,
                modifier = Modifier.size(
                    if (!isExpanded) 20.dp
                    else 0.dp
                )
                    .align(Alignment.CenterVertically),
                contentDescription = "galochka"
            )
            Text(
                text = stringResource(text),
                fontSize = 16.sp,
                fontWeight = FontWeight(500),
                color = if (!isExpanded) ExtendedThemeColors.colors.purple_heart_800
                else textInBorderGray
            )
        }
    }
}