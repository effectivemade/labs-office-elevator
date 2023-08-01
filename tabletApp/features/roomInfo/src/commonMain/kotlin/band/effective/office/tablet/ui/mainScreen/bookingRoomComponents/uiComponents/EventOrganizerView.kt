package band.effective.office.tablet.ui.mainScreen.bookingRoomComponents.uiComponents

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import band.effective.office.tablet.features.roomInfo.MainRes
import band.effective.office.tablet.ui.theme.LocalCustomColorsPalette
import band.effective.office.tablet.ui.theme.h8
import io.github.skeptick.libres.compose.painterResource

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun EventOrganizerView(
    modifier: Modifier = Modifier,
    organizers: List<String>,
    expanded: Boolean,
    selectedItem: String,
    onExpandedChange: () -> Unit,
    onSelectItem: (String) -> Unit,

    ) {
    val focusManager = LocalFocusManager.current
    Column(modifier = modifier) {
        Text(
            text = MainRes.string.selectbox_organizer_title,
            color = LocalCustomColorsPalette.current.secondaryTextAndIcon,
            style = MaterialTheme.typography.h8
        )
        Spacer(modifier = Modifier.height(10.dp))
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { onExpandedChange() }
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
                TextField(
                    modifier = Modifier.fillMaxWidth(0.8f),
                    value = selectedItem,
                    onValueChange = { onSelectItem(it) },
                    placeholder = {
                        Text(
                            text = MainRes.string.selectbox_organizer_title,
                            color = LocalCustomColorsPalette.current.tertiaryTextAndIcon
                        )
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent
                    ),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            defaultKeyboardAction(ImeAction.Done)
                            focusManager.clearFocus()
                            onSelectItem(if (organizers.contains(selectedItem)) selectedItem else "")
                            onExpandedChange()
                        }
                    ),
                )
                Image(
                    modifier = Modifier,
                    painter = painterResource(MainRes.image.arrow_to_down),
                    contentDescription = null
                )
            }

            ExposedDropdownMenu(
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colors.surface
                    ),
                expanded = expanded,
                onDismissRequest = {}
            ) {
                Column(
                    modifier = Modifier.background(
                        color = LocalCustomColorsPalette.current.elevationBackground,
                        shape = RoundedCornerShape(15.dp)
                    )
                ) {
                    organizers.forEach { organizer ->
                        if (!organizer.lowercase()
                                .contains(selectedItem.lowercase())
                        ) return@forEach
                        DropdownMenuItem(onClick = {
                            onSelectItem(organizer)
                            onExpandedChange()
                            focusManager.clearFocus()
                        }) {
                            Text(text = organizer)
                        }
                    }
                }
            }

        }
    }
}
