package band.effective.office.elevator.ui.booking.components.modals

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import band.effective.office.elevator.ExtendedThemeColors
import band.effective.office.elevator.MainRes
import band.effective.office.elevator.components.DropDownMenu
import band.effective.office.elevator.components.EffectiveButton
import band.effective.office.elevator.domain.models.BookingPeriod
import band.effective.office.elevator.textInBorderGray
import band.effective.office.elevator.ui.booking.models.Frequency
import dev.icerock.moko.resources.compose.stringResource
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun BookingRepeat(
    periodMeasure: BookingPeriod,
    backButtonClicked: () -> Unit,
    dropDownClick: (Int) -> Unit,
    confirmBooking: (Frequency) -> Unit,
    onSelected: () -> Unit,
    onDaySelected: (Int) -> Unit,
) {
    var periodMeasureState = remember {mutableStateOf("Week")}
    val periodicity = remember { mutableStateOf("1") }

    val listDaysOfWeek = listOf(
        MainRes.strings.Monday,
        MainRes.strings.Tuesday,
        MainRes.strings.Wednesday,
        MainRes.strings.Thursday,
        MainRes.strings.Friday
    )

    val selected1 = remember {
        mutableStateOf(true)
    }
    val selected2 = remember {
        mutableStateOf(false)
    }
    val selected3 = remember {
        mutableStateOf(false)
    }
    val list = mutableListOf<Pair<String, Int>>()
    val endDate = remember { mutableStateOf("01.01.2023") }
    val endPeriod = remember { mutableStateOf("1") }

    var researchClose = mutableStateOf(Triple(Pair("",""), "",""))

    val isExpandedContextMenu = remember { mutableStateOf(false) }
    val localDensity = LocalDensity.current
    var itemWidthDp by remember {
        mutableStateOf(0.dp)
    }
    var itemHeightDp by remember {
        mutableStateOf(0.dp)
    }
    var menuOffset by remember { mutableStateOf(0.dp) }
    var dropDownWidthDp by remember {
        mutableStateOf(50.dp)
    }
    val coroutine = rememberCoroutineScope()

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
            .height(height = 500.dp)
            .verticalScroll(state = rememberScrollState())
            .padding(bottom = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(height = 8.dp))
        Divider(
            modifier = Modifier
                .fillMaxWidth(fraction = .3f)
                .height(height = 4.dp)
                .background(
                    color = ExtendedThemeColors.colors.dividerColor,
                    shape = RoundedCornerShape(size = 16.dp)
                )
                .padding(
                    bottom = 8.dp,
                    top = 8.dp
                )
        )

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
                        color = ExtendedThemeColors.colors._66x
                    )
            )

            Column(
                modifier = Modifier.onGloballyPositioned { coordinates ->
                    itemWidthDp = with(localDensity) { coordinates.size.width.toDp() }
                    itemHeightDp = with(localDensity) { coordinates.size.height.toDp() }
                }
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
                            value = periodicity.value,
                            onValueChange = {
                                periodicity.value=it
                            },
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier
                                .padding(top = 12.dp, bottom = 12.dp)
                                .weight(.3f),
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            keyboardActions = KeyboardActions.Default
                        )
                        OutlinedTextField(
                            enabled = true,
                            readOnly = true,
                            value =
                            when(periodMeasureState.value) {
                                "Week" -> stringResource(MainRes.strings.week)
                                "Month" -> stringResource(MainRes.strings.month)
                                else -> stringResource(MainRes.strings.year)
                                                           },
                            onValueChange = {

                            },
                            shape = RoundedCornerShape(8.dp),
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                disabledTextColor = LocalContentColor.current.copy(LocalContentAlpha.current)
                            ),
                            modifier = Modifier
                                .padding(
                                    start = 12.dp,
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
                                    },
                                    modifier = Modifier.pointerInput(true){
                                        detectTapGestures(
                                            onPress = {
                                                coroutine.launch {
                                                    delay(300L)
                                                    menuOffset = (it.y).toDp()
                                                }
                                            }
                                        )
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
                Box (modifier = Modifier.fillMaxSize(.6f)){
                    Box (modifier = Modifier
                        .fillMaxWidth(0.6f).offset(itemWidthDp - dropDownWidthDp,menuOffset)) {
                        DropDownMenu(
                            expanded = isExpandedContextMenu.value,
                            content = {
                                BookingRepeatContextMenu(onClick = {
                                    when(it){
                                        "Неделя" -> periodMeasureState.value = "Week"
                                        "Месяц" -> periodMeasureState.value = "Month"
                                        "Год" -> periodMeasureState.value = "Year"
                                        else -> periodMeasureState.value = it
                                    }
                                    isExpandedContextMenu.value=!isExpandedContextMenu.value

                                })
                            },
                            modifier = Modifier.padding(end = 14.dp)
                                .onGloballyPositioned { coordinates ->
                                    dropDownWidthDp = with(localDensity) { coordinates.size.width.toDp() }
                                }
                        )
                    }
                }
                Column {
                    if(periodMeasureState.value == "Week") {
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

                        LazyRow(
                            modifier = Modifier.padding(
                                start = 54.dp,
                                end = 16.dp,
                                top = 16.dp,
                                bottom = 16.dp
                            )
                        ) {
                            itemsIndexed(listDaysOfWeek) { index, days ->
                                var isExpanded = remember { mutableStateOf(false) }
                                val name: String = stringResource(days)
                                Button(
                                    onClick = {
                                        isExpanded.value = !isExpanded.value
                                        list.add(Pair(first = name, second = index))
                                    },
                                    contentPadding = PaddingValues(
                                        horizontal = 12.dp,
                                        vertical = 6.dp
                                    ),
                                    colors = ButtonDefaults.buttonColors(
                                        backgroundColor = ExtendedThemeColors.colors.whiteColor
                                    ),
                                    modifier = Modifier
                                        .wrapContentWidth().padding(end = 12.dp),
                                    border = BorderStroke(
                                        width = 1.dp,
                                        color = if (isExpanded.value)
                                            ExtendedThemeColors.colors.purple_heart_800
                                        else textInBorderGray
                                    ),
                                    shape = RoundedCornerShape(12.dp),
                                    elevation = ButtonDefaults.elevation(0.dp, 2.dp, 0.dp)
                                ) {
                                    Row {
                                        Icon(
                                            imageVector = Icons.Rounded.Done,
                                            tint = ExtendedThemeColors.colors.purple_heart_800,
                                            modifier = Modifier.size(
                                                if (isExpanded.value) 20.dp
                                                else 0.dp
                                            )
                                                .align(Alignment.CenterVertically),
                                            contentDescription = "done button"
                                        )
                                    }
                                    Text(
                                        text = stringResource(days),
                                        style = MaterialTheme.typography.body2.copy(
                                            color = if (isExpanded.value) ExtendedThemeColors.colors.purple_heart_800
                                            else textInBorderGray
                                        ),
                                    )
                                }
                            }
                        }
                    }

                    Divider(
                        modifier = Modifier
                            .fillMaxWidth(fraction = 1.0f)
                            .height(height = 1.dp)
                            .background(
                                color = ExtendedThemeColors.colors._66x
                            )
                    )

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.Start),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 54.dp, end = 16.dp, top = 16.dp)
                    ) {
                        Text(
                            text = stringResource(MainRes.strings.book_finish),
                            style = MaterialTheme.typography.button.copy(
                                fontWeight = FontWeight(
                                    weight = 400
                                )
                            ),
                            textAlign = TextAlign.Start
                        )
                    }

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(0.dp, Alignment.Start),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 54.dp, end = 16.dp, top = 8.dp)
                    ) {
//                        Button(
//                            colors = ButtonDefaults.buttonColors(
//                                backgroundColor = Color.Transparent
//                            ),
//                            elevation = Elevation(),
//                            modifier = Modifier.fillMaxWidth().wrapContentHeight(),
//                            onClick = {
//                                selected1.value = !selected1.value
//                                selected2.value = false
//                                selected3.value = false
//                            }.also { onSelected() },
//                            contentPadding = PaddingValues(all = 0.dp)
//                        ) {
//                            Row(
//                                horizontalArrangement = Arrangement.spacedBy(0.dp, Alignment.Start),
//                                verticalAlignment = Alignment.CenterVertically,
//                            ) {
                                RadioButton(
                                    //enabled = false,
                                    selected = selected1.value,
                                    onClick = {
                                        selected1.value = true//!selected1.value
                                        selected2.value = false
                                        selected3.value = false
                                    }.also { onSelected() },
                                    colors = RadioButtonDefaults.colors(
                                        disabledSelectedColor = MaterialTheme.colors.primary,
                                        disabledUnselectedColor = Color.Black,
                                        selectedColor = MaterialTheme.colors.primary
                                    )
                                )

                        Spacer(modifier = Modifier.width(width = 16.dp))

                                Text(
                                    text = stringResource(MainRes.strings.never),
                                    style = MaterialTheme.typography.button.copy(
                                        color = ExtendedThemeColors.colors.radioTextColor,
                                        fontWeight = FontWeight(400)
                                    ),
                                    modifier = Modifier.wrapContentHeight()
                                )
                            //}
                        //}
                    }

                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 54.dp, end = 16.dp, top = 6.dp)
                    ) {
                        RadioButton(
                            selected = selected2.value,
                            onClick = {
                                selected2.value = true
                                selected1.value = false
                                selected3.value = false
                        }.also { onSelected() },
                            colors = RadioButtonDefaults.colors(
                                disabledSelectedColor = MaterialTheme.colors.primary,
                                disabledUnselectedColor = Color.Black,
                                selectedColor = MaterialTheme.colors.primary
                            )
                        )

                        Spacer(modifier = Modifier.width(width = 16.dp))

                        OutlinedTextField(
                            value = endDate.value,
                            onValueChange = {
                                endDate.value=it
                            },
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier
                                .padding(top = 12.dp, bottom = 12.dp)
                                .weight(0.4f),
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            keyboardActions = KeyboardActions.Default
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
                        RadioButton(
                            selected = selected3.value,
                            onClick = {
                                selected3.value = true
                                selected1.value = false
                                selected2.value = false
                        }.also { onSelected() },
                            colors = RadioButtonDefaults.colors(
                                disabledSelectedColor = MaterialTheme.colors.primary,
                                disabledUnselectedColor = Color.Black,
                                selectedColor = MaterialTheme.colors.primary
                            ))
                        Text(
                            text = stringResource(MainRes.strings.booking_1),
                            style = MaterialTheme.typography.button.copy(
                                color = ExtendedThemeColors.colors.radioTextColor,
                                fontWeight = FontWeight(400)
                            ),
                            modifier = Modifier.wrapContentWidth()
                        )
                        Spacer(modifier = Modifier.width(width = 16.dp))

                        OutlinedTextField(
                            value = endPeriod.value,
                            onValueChange = {
                                endPeriod.value=it
                            },
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier
                                .padding(top = 12.dp, bottom = 12.dp)
                                .weight(weight = 0.1f),
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            keyboardActions = KeyboardActions.Default
                        )

                        Spacer(modifier = Modifier.width(width = 16.dp))

                        Text(
                            text = stringResource(MainRes.strings.booking_2),
                            style = MaterialTheme.typography.button.copy(
                                color = ExtendedThemeColors.colors.radioTextColor,
                                fontWeight = FontWeight(400)
                            ),
                            modifier = Modifier.wrapContentWidth().wrapContentHeight()
                        )

                        Spacer(modifier = Modifier.width(width = 48.dp))
                    }

                    EffectiveButton(
                        buttonText = stringResource(MainRes.strings.confirm_booking),
                        onClick = {
                            if(selected2.value)
                                researchClose.value=Triple(Pair("Date", endDate.value), periodicity.value, periodMeasureState.value)
                            else{
                                if(selected3.value)
                                    researchClose.value=Triple(Pair("CoupleTimes", endPeriod.value), periodicity.value, periodMeasureState.value)
                                else
                                    researchClose.value=Triple(Pair("Never",""), periodicity.value, periodMeasureState.value)
                            }
                            val frequency = Frequency(days = list.toList(), researchEnd =researchClose.value)
                            confirmBooking(frequency)
                        },
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                    )
                }
            }
        }
    }
}