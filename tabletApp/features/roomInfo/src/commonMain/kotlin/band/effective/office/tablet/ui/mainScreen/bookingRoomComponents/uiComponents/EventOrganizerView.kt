package band.effective.office.tablet.ui.mainScreen.bookingRoomComponents.uiComponents

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import band.effective.office.tablet.features.roomInfo.MainRes
import band.effective.office.tablet.ui.mainScreen.bookingRoomComponents.RealEventOrganizerComponent
import io.github.skeptick.libres.compose.painterResource

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun EventOrganizerView(
    modifier: Modifier = Modifier,
    component: RealEventOrganizerComponent,
    organizers: List<String>
) {
    val expended by component.expanded.collectAsState()
    val selectedItem by component.selectedItem.collectAsState()
    Column(modifier = modifier) {
        Text(
            text = MainRes.string.select_organizer_title,
            color = Color(0xFF808080),
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(10.dp))
        ExposedDropdownMenuBox(
            expanded = expended,
            onExpandedChange = { component.onExpandedChange() }
        ) {
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(15.dp))
                    .fillMaxSize()
                    .background(color = Color(0xFF302D2C))
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (selectedItem.notSelect()) {
                    Text(
                        text = MainRes.string.selectbox_organizer_title,
                        color = Color(0xFF777777)
                    )
                } else {
                    Text(
                        text = selectedItem,
                        color = Color(0xFFFAFAFA)
                    )
                }

                Image(
                    modifier = Modifier,
                    painter = painterResource(MainRes.image.arrow_to_down),
                    contentDescription = null
                )
            }

            ExposedDropdownMenu(
                modifier = Modifier
                    .background(
                        color = Color(0xFF252322)
                    ),
                expanded = expended,
                onDismissRequest = { component.onExpandedChange() }
            ) {
                Column(
                    modifier = Modifier.background(
                        color = Color(0xFF302D2C),
                        shape = RoundedCornerShape(15.dp)
                    )
                ) {
                    organizers.forEach { organizer ->
                        DropdownMenuItem(onClick = {
                            component.onSelectItem(organizer)
                        }) {
                            Text(text = organizer, color = Color(0xFFFAFAFA))
                        }
                    }
                }
            }

        }
    }
}

private fun String.notSelect() = this == ""