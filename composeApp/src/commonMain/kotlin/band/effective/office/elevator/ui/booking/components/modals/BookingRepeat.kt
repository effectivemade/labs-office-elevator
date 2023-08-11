package band.effective.office.elevator.ui.booking.components.modals

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import band.effective.office.elevator.ExtendedTheme
import band.effective.office.elevator.MainRes
import band.effective.office.elevator.components.EffectiveButton
import band.effective.office.elevator.textInBorderGray
import band.effective.office.elevator.textInBorderPurple
import band.effective.office.elevator.theme_light_primary_color
import dev.icerock.moko.resources.compose.stringResource

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BookingRepeat(
    backButtonClicked: () -> Unit,
    dropDownClick: (Int) -> Unit,
    confirmBooking: () -> Unit,
    onSelected: () -> Unit,
    onDaySelected: (Int) -> Unit
) {
    val selected1 = remember {
        mutableStateOf(true)
    }
    val selected2 = remember {
        mutableStateOf(false)
    }
    val selected3 = remember {
        mutableStateOf(false)
    }

    val weekNames = listOf(
        MainRes.strings.Monday,
        MainRes.strings.Tuesday,
        MainRes.strings.Wednesday,
        MainRes.strings.Thursday,
        MainRes.strings.Friday,
        MainRes.strings.Saturday,
        MainRes.strings.Sunday,
    )

    val isExpandedContextMenu = remember { mutableStateOf(false) }

    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(
                color = Color.White,
                shape = RoundedCornerShape(
                    topStart = 16.dp,
                    topEnd = 16.dp,
                    bottomEnd = 0.dp,
                    bottomStart = 0.dp
                )
            )
            .padding(bottom = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(height = 8.dp))
        Divider(
            modifier = Modifier
                .fillMaxWidth(fraction = .3f)
                .height(height = 4.dp)
                .background(
                    color = ExtendedTheme.colors.dividerColor,
                    shape = RoundedCornerShape(size = 16.dp)
                )
                .padding(
                    bottom = 8.dp,
                    top = 8.dp
                )
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.Start),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 16.dp)
        ) {
            IconButton(
                onClick = backButtonClicked,
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "back button"
                )
            }
            Text(
                text = stringResource(resource = MainRes.strings.booking_repeat),
                style = MaterialTheme.typography.h6.copy(fontWeight = FontWeight(500)),
                textAlign = TextAlign.Center
            )
        }

        Divider(
            modifier = Modifier
                .fillMaxWidth(fraction = 1.0f)
                .height(height = 1.dp)
                .background(
                    color = ExtendedTheme.colors._66x
                )
        )

        Column(
            modifier = Modifier
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.Start),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 54.dp, end = 16.dp, top = 16.dp)
            ) {
                Text(
                    text = stringResource(resource = MainRes.strings.booking_repeat_in),
                    style = MaterialTheme.typography.button.copy(fontWeight = FontWeight(400))
                )
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.Start),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(start = 54.dp, end = 16.dp)
            ) {
                OutlinedTextField(
                    value = "1",
                    onValueChange = {

                    },
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .padding(top = 12.dp, bottom = 12.dp)
                        .weight(.3f)
                )
                OutlinedTextField(
                    enabled = false,
                    readOnly = true,
                    value = stringResource(MainRes.strings.week),
                    onValueChange = {

                    },
                    shape = RoundedCornerShape(8.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        disabledTextColor = LocalContentColor.current.copy(LocalContentAlpha.current)
                    ),
                    modifier = Modifier
                        .padding(
                            start = 16.dp,
                            top = 12.dp,
                            end = 12.dp,
                            bottom = 12.dp
                        )
                        .weight(.7f),
                    trailingIcon = {
                        IconButton(
                            onClick =
                            {
                                isExpandedContextMenu.value = !isExpandedContextMenu.value
                            }
                        ) {
                            Image(
                                modifier = Modifier,
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = "drop down",
                                contentScale = ContentScale.None
                            )
                        }
                    }
                )
            }

            Box {
                Column {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.Start),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 54.dp, end = 16.dp, top = 16.dp)
                    ) {
                        Text(
                            text = stringResource(MainRes.strings.when_repeat),
                            style = MaterialTheme.typography.button.copy(
                                fontWeight = FontWeight(
                                    weight = 400
                                )
                            )
                        )
                    }

                    LazyVerticalStaggeredGrid(
                        columns = StaggeredGridCells.Fixed(count = 4),
                        verticalItemSpacing = 12.dp,
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 54.dp, end = 16.dp, top = 16.dp, bottom = 16.dp),
                        content = {
                            items(weekNames.size) { index ->
                                var isExpanded by remember { mutableStateOf(false) }
                                Button(
                                    onClick = {
                                        isExpanded = !isExpanded
                                    }.also { onDaySelected(index) },
                                    colors = ButtonDefaults.buttonColors(theme_light_primary_color),
                                    contentPadding = PaddingValues(
                                        horizontal = 12.dp,
                                        vertical = 6.dp
                                    ),
                                    modifier = Modifier
                                        .wrapContentWidth(),
                                    border = BorderStroke(
                                        width = 1.dp,
                                        color = if (isExpanded) textInBorderPurple else textInBorderGray
                                    ),
                                    shape = RoundedCornerShape(12.dp),
                                    elevation = ButtonDefaults.elevation(0.dp, 2.dp, 0.dp)
                                ) {
                                    Row {
                                        androidx.compose.material.Icon(
                                            imageVector = Icons.Rounded.Done,
                                            tint = textInBorderPurple,
                                            modifier = Modifier.size(
                                                if (isExpanded) 20.dp
                                                else 0.dp
                                            )
                                                .align(Alignment.CenterVertically),
                                            contentDescription = "done button"
                                        )
                                        androidx.compose.material.Text(
                                            text = stringResource(weekNames[index]),
                                            style = MaterialTheme.typography.body2.copy(
                                                color = if (isExpanded) textInBorderPurple
                                                else textInBorderGray
                                            ),
                                        )
                                    }
                                }
                            }
                        }
                    )
                }

                if (isExpandedContextMenu.value) {
                    DropDownMenu(onClick = { item ->
                        isExpandedContextMenu.value = !isExpandedContextMenu.value
                        dropDownClick(item)
                    })
                }
            }

            Divider(
                modifier = Modifier
                    .fillMaxWidth(fraction = 1.0f)
                    .height(height = 1.dp)
                    .background(
                        color = ExtendedTheme.colors._66x
                    )
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.Start),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 54.dp, end = 16.dp, top = 16.dp, bottom = 8.dp)
            ) {
                Text(
                    text = stringResource(MainRes.strings.book_finish),
                    style = MaterialTheme.typography.button.copy(fontWeight = FontWeight(weight = 400)),
                    textAlign = TextAlign.Start
                )
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(0.dp, Alignment.Start),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 54.dp, end = 16.dp, top = 16.dp)
            ) {
                Button(
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Transparent
                    ),
                    elevation = ButtonDefaults.elevation(
                        defaultElevation = 0.dp,
                        pressedElevation = 0.dp,
                        disabledElevation = 0.dp,
                        hoveredElevation = 0.dp,
                        focusedElevation = 0.dp
                    ),
                    modifier = Modifier.fillMaxWidth().wrapContentHeight(),
                    onClick = {
                        selected1.value = !selected1.value
                        selected2.value = false
                        selected3.value = false
                    }.also { onSelected() },
                    contentPadding = PaddingValues(all = 0.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(0.dp, Alignment.Start),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        RadioButton(
                            enabled = false,
                            selected = selected1.value,
                            onClick = { },
                            colors = RadioButtonDefaults.colors(
                                disabledSelectedColor = MaterialTheme.colors.primary,
                                disabledUnselectedColor = Color.Black
                            )
                        )
                        Text(
                            text = stringResource(MainRes.strings.never),
                            style = MaterialTheme.typography.button.copy(
                                color = ExtendedTheme.colors.radioTextColor,
                                fontWeight = FontWeight(400)
                            ),
                            modifier = Modifier.fillMaxWidth().wrapContentHeight()
                        )
                    }
                }
            }

            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 54.dp, end = 16.dp, top = 8.dp)
            ) {
                RadioButton(selected = selected2.value, onClick = {
                    selected2.value = !selected2.value
                    selected1.value = false
                    selected3.value = false
                }.also { onSelected() })

                Spacer(modifier = Modifier.width(width = 16.dp))

                OutlinedTextField(
                    value = "01.01.2023",
                    onValueChange = {

                    },
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .padding(top = 12.dp, bottom = 12.dp)
                        .weight(0.4f)
                )

                Spacer(modifier = Modifier.width(width = 48.dp))
            }

            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 54.dp, end = 16.dp, top = 8.dp, bottom = 8.dp)
            ) {
                RadioButton(selected = selected3.value, onClick = {
                    selected3.value = !selected3.value
                    selected1.value = false
                    selected2.value = false
                }.also { onSelected() })
                Text(
                    text = stringResource(MainRes.strings.booking_1),
                    style = MaterialTheme.typography.button.copy(
                        color = ExtendedTheme.colors.radioTextColor,
                        fontWeight = FontWeight(400)
                    ),
                    modifier = Modifier.wrapContentWidth()
                )
                Spacer(modifier = Modifier.width(width = 16.dp))

                OutlinedTextField(
                    value = "1",
                    onValueChange = {

                    },
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .padding(top = 12.dp, bottom = 12.dp)
                        .weight(weight = 0.1f)
                )

                Spacer(modifier = Modifier.width(width = 16.dp))

                Text(
                    text = stringResource(MainRes.strings.booking_2),
                    style = MaterialTheme.typography.button.copy(
                        color = ExtendedTheme.colors.radioTextColor,
                        fontWeight = FontWeight(400)
                    ),
                    modifier = Modifier.wrapContentWidth().wrapContentHeight()
                )

                Spacer(modifier = Modifier.width(width = 48.dp))
            }

                EffectiveButton(
                    buttonText = stringResource(MainRes.strings.confirm_booking),
                    onClick = confirmBooking,
                    modifier = Modifier.padding(horizontal = 16.dp),
                )
            }
        }
    }