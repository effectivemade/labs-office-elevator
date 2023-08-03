package band.effective.office.elevator.ui.booking.components.modals

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import band.effective.office.elevator.ExtendedTheme
import band.effective.office.elevator.LocalExtendedColors
import band.effective.office.elevator.MainRes
import band.effective.office.elevator.borderPurple
import band.effective.office.elevator.textInBorderGray
import band.effective.office.elevator.textInBorderPurple
import band.effective.office.elevator.theme_light_onPrimary
import band.effective.office.elevator.theme_light_primary_color
import band.effective.office.elevator.theme_light_tertiary_color
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.compose.stringResource
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun ChooseZone(zone: Boolean) {

    val sheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed)
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = sheetState
    )
    val scope = rememberCoroutineScope()
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
    }
    else {
        highListNames = highRoomsNames
        lowListNames = lowRoomsNames
    }

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetShape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp),
        sheetContent = {
            Column(modifier = Modifier.fillMaxWidth().background(Color.Green)) {
                Spacer(modifier = Modifier.padding(vertical = 10.dp))
                Divider(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .fillMaxWidth(fraction = .3f)
                        .height(4.dp)
                        .background(
                            color = ExtendedTheme.colors.dividedColor,
                            shape = RoundedCornerShape(size = 16.dp)
                        )
                        .padding(
                            bottom = 8.dp
                        )
                )
                Row(modifier = Modifier.padding(top = 10.dp, start = 16.dp, end = 16.dp)) {
                    IconButton(
                        onClick = { scope.launch { sheetState.collapse() } },
                        modifier = Modifier
                            .align(Alignment.Top)
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Close,
                            contentDescription = "Krestik",
                            tint = theme_light_tertiary_color
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
                        color = theme_light_tertiary_color,
                        modifier = Modifier.padding(vertical = 10.dp)
                    )
                }
                Divider(
                    modifier = Modifier
                        .fillMaxWidth(fraction = 1.0f)
                        .height(height = 1.dp)
                        .background(
                            color = ExtendedTheme.colors._x66
                        )
                )
                Column(
                    modifier = Modifier.padding(
                        top = 16.dp,
                        bottom = 6.dp,
                        start = 16.dp
                    )
                        .fillMaxWidth()
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
                Button(
                    onClick = { scope.launch { sheetState.collapse() } },
                    modifier = Modifier
                        .align(alignment = Alignment.CenterHorizontally)
                        .fillMaxWidth()
                        .height(60.dp)
                        .padding(horizontal = 15.dp, vertical = 10.dp),
                    shape = RoundedCornerShape(32.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = LocalExtendedColors.current.trinidad_600,
                        contentColor = theme_light_onPrimary
                    )
                ) {

                    Text(
                        text = stringResource(MainRes.strings._confirm),
                        fontSize = 16.sp,
                        fontWeight = FontWeight(500)
                    )
                }
            }
        },
        sheetPeekHeight = 0.dp
    ) {
        Box(modifier = Modifier.padding(horizontal = 45.dp)) {
            Button(
                onClick = {
                    scope.launch {
                        if (sheetState.isCollapsed)
                            sheetState.expand()
                        else
                            sheetState.collapse()
                    }
                },
                modifier = Modifier.align(alignment = Alignment.Center)
            ) {

                Text(
                    modifier = Modifier.padding(start = 8.dp),
                    text = stringResource(MainRes.strings.select_zones),
                    style = MaterialTheme.typography.subtitle1.copy(
                        color = borderPurple,
                        fontWeight = FontWeight(400)
                    )
                )
            }

        }
    }
}

@Composable
fun WorkingZones(text: StringResource) {
    var isExpanded by remember { mutableStateOf(false) }
    Button(
        onClick = { isExpanded = !isExpanded },
        colors = ButtonDefaults.buttonColors(theme_light_primary_color),
        modifier = Modifier
            .padding(end = 10.dp)
            .fillMaxWidth(fraction = 0.5f),
        border = BorderStroke(
            width = 1.dp,
            color = if (!isExpanded) textInBorderPurple else textInBorderGray
        ),
        shape = RoundedCornerShape(12.dp),
        elevation = ButtonDefaults.elevation(0.dp, 2.dp, 0.dp)
    ) {
        Row {
            Icon(
                imageVector = Icons.Rounded.Done,
                tint = textInBorderPurple,
                modifier = Modifier.size(
                    if (!isExpanded) 20.dp
                    else 0.dp
                )
                    .align(Alignment.CenterVertically),
                contentDescription = "galochka"
            )
            //Spacer(modifier = Modifier.padding(horizontal = 5.dp))
            Text(
                text = stringResource(text),
                fontSize = 16.sp,
                fontWeight = FontWeight(500),
                color = if (!isExpanded) textInBorderPurple
                else textInBorderGray
            )
        }
    }
}