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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import band.effective.office.tablet.features.roomInfo.MainRes
import band.effective.office.tablet.ui.mainScreen.bookingRoomComponents.RealEventOrganizerComponent
import band.effective.office.tablet.ui.theme.LocalCustomColorsPalette
import band.effective.office.tablet.ui.theme.h8
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
            color = LocalCustomColorsPalette.current.secondaryTextAndIcon,
            style = MaterialTheme.typography.h8
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
                    .background(color = LocalCustomColorsPalette.current.elevationBackground)
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (selectedItem.notSelect()) {
                    Text(
                        text = MainRes.string.selectbox_organizer_title,
                        color = LocalCustomColorsPalette.current.secondaryTextAndIcon
                    )
                } else {
                    Text(
                        text = selectedItem
                    )
                }

                Image(
                    modifier = Modifier,
                    painter = painterResource(MainRes.image.arrow_to_down),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(color = LocalCustomColorsPalette.current.secondaryTextAndIcon)
                )
            }

            MaterialTheme(shapes = MaterialTheme.shapes.copy(medium = RoundedCornerShape(15.dp))) {
                ExposedDropdownMenu(
                    modifier = Modifier
                        .background(
                            color = LocalCustomColorsPalette.current.elevationBackground
                        ),
                    expanded = expended,
                    onDismissRequest = { component.onExpandedChange() }
                ) {
                    organizers.forEach { organizer ->
                        val isPressed = remember { mutableStateOf(false) }
                        val colorItem = if (isPressed.value)
                            MaterialTheme.colors.primary.copy(alpha = 0.6f) else LocalCustomColorsPalette.current.elevationBackground
                        DropdownMenuItem(
                            modifier = Modifier.background(color = colorItem),
                            onClick = {
                                isPressed.value = !isPressed.value
                                component.onSelectItem(organizer)
                            }) {
                            Text(
                                text = organizer
                            )
                        }
                    }
                }
            }

        }
    }
}

private fun String.notSelect() = this == ""