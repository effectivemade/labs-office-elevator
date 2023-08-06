package band.effective.office.elevator.ui.booking

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import band.effective.office.elevator.MainRes
import band.effective.office.elevator.borderPurple
import band.effective.office.elevator.components.ModalCalendar
import band.effective.office.elevator.components.TimePickerModal
import band.effective.office.elevator.components.TitlePage
import band.effective.office.elevator.textGrayColor
import band.effective.office.elevator.textInBorderPurple
import band.effective.office.elevator.ui.booking.components.BookingCard
import band.effective.office.elevator.ui.booking.components.OutlineButtonPurple
import band.effective.office.elevator.ui.booking.components.modals.BookAccept
import band.effective.office.elevator.ui.booking.components.modals.BookingPeriod
import band.effective.office.elevator.ui.booking.components.modals.BookingRepeat
import band.effective.office.elevator.ui.booking.components.modals.BookingRepeatCard
import band.effective.office.elevator.ui.booking.components.modals.BookingSuccess
import band.effective.office.elevator.ui.booking.components.modals.ChooseZone
import band.effective.office.elevator.ui.booking.store.BookingStore
import band.effective.office.elevator.ui.models.TypesList
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BookingScreen(bookingComponent: BookingComponent) {
    val list by bookingComponent.state.collectAsState()
    var showChooseZone = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    var showBookPeriod = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    var showBookAccept = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    var showBookRepeat = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)

    var showRepeatDialog by remember { mutableStateOf(false) }
    var showCalendar by remember { mutableStateOf(false) }
    var showConfirm by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }

    LaunchedEffect(bookingComponent) {
        bookingComponent.label.collect { label ->
            when (label) {
               is BookingStore.Label.OpenChooseZone -> showChooseZone.show()
                is BookingStore.Label.CloseChooseZone -> showChooseZone.hide()
                is BookingStore.Label.OpenBookPeriod -> showBookPeriod.show()
                is BookingStore.Label.CloseBookPeriod -> showBookPeriod.hide()
                is BookingStore.Label.OpenRepeatDialog -> showRepeatDialog = true
                is BookingStore.Label.CloseRepeatDialog -> showRepeatDialog = false
                is BookingStore.Label.OpenBookAccept -> showBookAccept.show()
                is BookingStore.Label.CloseBookAccept -> showBookAccept.hide()
                is BookingStore.Label.OpenCalendar -> showCalendar = true
                is BookingStore.Label.CloseCalendar -> showCalendar = false
                is BookingStore.Label.OpenConfirmBooking -> showConfirm = true
                is BookingStore.Label.CloseConfirmBooking -> showConfirm = false
                is BookingStore.Label.OpenTimeModal -> showTimePicker = true
                is BookingStore.Label.CloseTimeModal -> showTimePicker = false
                is BookingStore.Label.OpenBookRepeat -> showBookRepeat.show()
                is BookingStore.Label.CloseBookRepeat -> showBookRepeat.hide()
            }
        }
    }
    BookingScreenContent(
        showBookRepeat = showBookRepeat,
        showChooseZone = showChooseZone,
        showRepeatDialog = showRepeatDialog,
        showBookPeriod = showBookPeriod,
        showBookAccept = showBookAccept,
        showCalendar = showCalendar,
        showConfirm = showConfirm,
        showTimePicker = showTimePicker,
        currentDate = list.currentDate,
        onClickOpenBookRepeat = {bookingComponent.onEvent(BookingStore.Intent.OpenBookRepeat)},
        onClickCloseBookRepeat = {bookingComponent.onEvent(BookingStore.Intent.CloseBookRepeat)},
        onClickSearchSuitableOptions = {bookingComponent.onEvent(BookingStore.Intent.SearchSuitableOptions)},
        onClickOpenTimeModal = {bookingComponent.onEvent(BookingStore.Intent.OpenTimeModal)},
        onClickCloseTimeModal = {bookingComponent.onEvent(BookingStore.Intent.CloseTimeModal)},
        onClickSelectTime = {time: LocalTime -> bookingComponent.onEvent(BookingStore.Intent.ApplyTime(time = time))},
        onClickCloseBookingConfirm = {bookingComponent.onEvent(BookingStore.Intent.CloseConfirmBooking)},
        onClickOpenBookingConfirm = {bookingComponent.onEvent(BookingStore.Intent.OpenConfirmBooking)},
        onClickCloseChoseZone = {bookingComponent.onEvent(BookingStore.Intent.CloseChooseZone)},
        onClickCloseCalendar = {bookingComponent.onEvent(BookingStore.Intent.CloseCalendar)},
        onClickOpenCalendar = {bookingComponent.onEvent(BookingStore.Intent.OpenCalendar)},
        onClickOpenChoseZone = {bookingComponent.onEvent(BookingStore.Intent.OpenChooseZone)},
        onClickOpenRepeatDialog = {bookingComponent.onEvent(BookingStore.Intent.OpenRepeatDialog)},
        onClickCloseBookPeriod = {bookingComponent.onEvent(BookingStore.Intent.CloseBookPeriod)},
        onClickOpenBookPeriod = {bookingComponent.onEvent(BookingStore.Intent.OpenBookPeriod)},
        onClickMainScreen = {},
        onClickOpenBookAccept = {bookingComponent.onEvent(BookingStore.Intent.OpenBookAccept)},
        onClickCloseBookAccept = { bookingComponent.onEvent(BookingStore.Intent.CloseBookAccept) },
        onClickApplyDate = {date: LocalDate? -> bookingComponent.onEvent(BookingStore.Intent.ApplyDate(date= date))}
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun BookingScreenContent(
    showChooseZone: ModalBottomSheetState,
    showBookPeriod: ModalBottomSheetState,
    onClickOpenBookPeriod: () -> Unit,
    onClickCloseBookPeriod: () -> Unit,
    onClickOpenChoseZone: () -> Unit,
    onClickCloseChoseZone: () -> Unit,
    showRepeatDialog: Boolean,
    onClickOpenRepeatDialog: () -> Unit,
    showBookAccept: ModalBottomSheetState,
    onClickOpenBookAccept: () -> Unit,
    onClickCloseBookAccept: () -> Unit,
    onClickCloseCalendar: () -> Unit,
    onClickOpenCalendar: () -> Unit,
    showCalendar: Boolean,
    onClickApplyDate: (LocalDate?) -> Unit,
    currentDate: LocalDate,
    onClickOpenBookingConfirm: () -> Unit,
    onClickCloseBookingConfirm: () -> Unit,
    showConfirm: Boolean,
    onClickMainScreen: () -> Unit,
    showTimePicker: Boolean,
    onClickOpenTimeModal: () -> Unit,
    onClickCloseTimeModal: () -> Unit,
    onClickSelectTime: (LocalTime) -> Unit,
    onClickSearchSuitableOptions: () -> Unit,
    showBookRepeat: ModalBottomSheetState,
    onClickOpenBookRepeat: () -> Unit,
    onClickCloseBookRepeat: () -> Unit
) {
    val  scrollState = rememberLazyListState()

    var timeTitle by remember { mutableStateOf(MainRes.strings.take_from) }

    var isExpandedCard by rememberSaveable { mutableStateOf(true) }
    val iconRotationStateCard by animateFloatAsState(targetValue = if (isExpandedCard) 90F else 270F)
    var isExpandedOptions by rememberSaveable { mutableStateOf(true) }
    val iconRotationStateOptions by animateFloatAsState(targetValue = if (isExpandedOptions) 90F else 270F)
    if(scrollState.isScrollInProgress){
        isExpandedCard = false
        isExpandedOptions = false
    }
    Box{
        ModalBottomSheetLayout(
            sheetState = AddSheetState(
                showBookPeriod = showBookPeriod,
                showBookAccept = showBookAccept,
                showChooseZone = showChooseZone,
                showBookRepeat = showBookRepeat
            ),
            sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
            sheetContent = {
                if(showBookPeriod.targetValue == ModalBottomSheetValue.Expanded){
                    BookingPeriod(
                        startDate = "Чт, 27 июл. 2023 г.",
                        startTime = "10:30",
                        finishDate = "Чт, 27 июл. 2023 г.",
                        finishTime =  "15:30",
                        repeatBooking = "Бронирование не повторяется",
                        false,
                        closeClick =  onClickCloseBookPeriod,
                        onSwitchChange =  {false},
                        bookStartDate = onClickOpenCalendar,
                        bookFinishDate = onClickOpenCalendar,
                        bookStartTime = {onClickOpenTimeModal()
                            timeTitle = MainRes.strings.take_from},
                        bookFinishTime = {onClickOpenTimeModal()
                            timeTitle = MainRes.strings.take_before},
                        bookingRepeat =  onClickOpenRepeatDialog,
                        onClickSearchSuitableOptions = onClickSearchSuitableOptions
                    )
                }else if (showBookAccept.targetValue == ModalBottomSheetValue.Expanded){
                    BookAccept(
                        onClickCloseBookAccept = onClickCloseBookAccept,
                        confirmBooking = onClickOpenBookingConfirm
                    )
                }else if (showBookRepeat.targetValue == ModalBottomSheetValue.Expanded){
                    BookingRepeat(
                        backButtonClicked = onClickCloseBookRepeat,
                        dropDownClick = {},
                        confirmBooking = {},
                        onSelected = {},
                        onDaySelected = {}
                    )
                }else {
                    ChooseZone(
                        true,
                        onClickCloseChoseZone
                    )
                }
            }
        ) {
            Scaffold(
                topBar = {
                    Box {
                        Column (modifier = Modifier.clip(
                            RoundedCornerShape(bottomEnd = 16.dp, bottomStart = 16.dp)).background(
                            Color.White).padding(bottom = 24.dp)){
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.background(Color.White).padding(horizontal = 16.dp)
                                    .padding(top = 48.dp)
                            ) {
                                TitlePage(
                                    title = stringResource(MainRes.strings.booking)
                                )
                                Spacer(modifier = Modifier.weight(.1f))
                                OutlineButtonPurple(
                                    onClick = { isExpandedCard =!isExpandedCard},
                                    icon1 = MainRes.images.icon_map,
                                    icon2 = MainRes.images.back_button,
                                    title = MainRes.strings.show_map,
                                    rotate = iconRotationStateCard
                                )
                            }
                            OptionMenu( isExpandedCard = isExpandedCard, isExpandedOptions =  isExpandedOptions, onClickOpenBookPeriod = onClickOpenBookPeriod)
                        }
                        Box(
                            modifier = Modifier.align(Alignment.BottomCenter)
                                .graphicsLayer {
                                    translationY = 40f
                                }
                        ) {
                            Button(
                                onClick = {isExpandedOptions = !isExpandedOptions},
                                shape = CircleShape,
                                border = BorderStroke(
                                    width = 1.dp,
                                    color = textGrayColor
                                ),
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = Color.White,
                                    contentColor = textGrayColor
                                ),
                                modifier = Modifier.size(40.dp)
                            ) {
                                Image(
                                    painter = painterResource(MainRes.images.back_button),
                                    contentDescription = null,
                                    modifier = Modifier.size(24.dp).rotate(iconRotationStateOptions),
                                    contentScale = ContentScale.Crop
                                )
                            }
                        }
                    }
                }
            ) {
                Column {
                    Row(modifier = Modifier.fillMaxWidth().background(MaterialTheme.colors.onBackground).
                    padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.Top) {
                        Text(
                            modifier = Modifier.padding(top = 16.dp),
                            text = stringResource(MainRes.strings.suitable_options),
                            style = MaterialTheme.typography.subtitle1.copy(
                                color = Color.Black,
                                fontWeight = FontWeight(500)
                            )
                        )
                        Spacer(modifier = Modifier.weight(.1f))
                        IconButton(
                            onClick = onClickOpenChoseZone, modifier = Modifier.padding(top = 3.dp),
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    painter = painterResource(MainRes.images.icon_location),
                                    tint = textInBorderPurple,
                                    contentDescription = null
                                )
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
                    ListBooking(scrollState, onClickOpenBookAccept)
                }
            }
        }

        if(showRepeatDialog){
            BookingRepeatCard (
                onSelected = onClickOpenBookRepeat,
                 modifier = Modifier.padding(horizontal = 16.dp).align(Alignment.Center)
            )
        }
        if(showCalendar){
            ModalCalendar(
                currentDate = currentDate,
                onClickOk = onClickApplyDate,
                onClickCansel = onClickCloseCalendar,
                 modifier = Modifier.padding(horizontal = 16.dp).align(Alignment.Center)
            )
        }

        if (showTimePicker){
            TimePickerModal(
                titleText = stringResource(timeTitle),
                modifier = Modifier.padding(horizontal = 16.dp).clip(shape = RoundedCornerShape(16.dp)).
                background(Color.White).align(Alignment.Center),
                onClickCansel = onClickCloseTimeModal,
                onClickOk = onClickSelectTime
            )
        }

        if (showConfirm){
            BookingSuccess(
                onMain = onClickMainScreen,
                close = onClickCloseBookingConfirm,
                modifier = Modifier.padding(horizontal = 16.dp).align(Alignment.Center)
            )
        }
    }

}

@OptIn(ExperimentalMaterialApi::class)
fun AddSheetState(
    showBookPeriod: ModalBottomSheetState,
    showBookAccept: ModalBottomSheetState,
    showChooseZone: ModalBottomSheetState,
    showBookRepeat: ModalBottomSheetState
): ModalBottomSheetState {
    return if (showBookAccept.targetValue == ModalBottomSheetValue.Expanded){
        showBookAccept
    }else if (showBookRepeat.targetValue == ModalBottomSheetValue.Expanded) {
      showBookRepeat
    }else if( showBookPeriod.targetValue == ModalBottomSheetValue.Expanded){
        showBookPeriod
    }else{
        showChooseZone
    }
}

@Composable
private fun ListBooking(scrollState: LazyListState, onClickOpenBookAccept: () -> Unit) {
    LazyColumn(modifier =  Modifier.background(MaterialTheme.colors.onBackground).padding(horizontal = 16.dp),
        state = scrollState) {
        val listSeats = listOf(
            "Cassipopea | Стол 1",
            "Cassipopea | Стол 2",
            "Cassipopea | Стол 3",
            "Cassipopea | Стол 4",
            "Cassipopea | Стол 5",
            "Cassipopea | Стол 6",
            "Cassipopea | Стол 7",
            "Cassipopea | Стол 8",
            "Cassipopea | Стол 9",
            "Cassipopea | Стол 10",
        )
        items(listSeats) { seat ->
            BookingCard(
                seat,
                onClickOpenBookAccept
            )
        }
    }
}


@Composable
private fun  OptionMenu(
    isExpandedCard: Boolean,
    isExpandedOptions: Boolean,
    onClickOpenBookPeriod: () -> Unit
) {
    Column {
        AnimatedVisibility(visible = isExpandedCard){
            Column(modifier = Modifier.fillMaxWidth().padding(top = 16.dp).
            background(MaterialTheme.colors.onBackground)
            ) {
                Row (horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                    Image(
                        modifier = Modifier.padding(vertical = 20.dp),
                        painter = painterResource(MainRes.images.icon_map), //TODO(Заменить карту!")
                        contentDescription = null
                    )
                }
            }
        }

        AnimatedVisibility(visible = isExpandedOptions){
            Column(modifier = Modifier.padding(top = 16.dp).padding(horizontal = 16.dp)){
                Text(
                    text = stringResource(MainRes.strings.type_booking),
                    style = MaterialTheme.typography.subtitle1,
                    color = Color.Black
                )
                Row (modifier = Modifier.padding(top = 8.dp).fillMaxWidth(), horizontalArrangement  = Arrangement.Center ){
                    val types = listOf(
                        TypesList(name = MainRes.strings.workplace, icon = MainRes.images.table_icon),
                        TypesList(name = MainRes.strings.meeting_room, icon = MainRes.images.icon_meet)
                    )
                    val selectedType = remember { mutableStateOf(types[0]) }

                    types.forEach {type ->
                        val selected = selectedType.value == type
                        Box(
                            modifier = Modifier.padding(
                                end = 12.dp
                            ).border(
                                width = 1.dp,
                                shape = RoundedCornerShape(8.dp),
                                color = MaterialTheme.colors.secondary
                            ).background(
                                color = if(selected){MaterialTheme.colors.background}else{Color.White}
                            ).selectable(
                                selected = selected,
                                onClick = {
                                    selectedType.value = type
                                }
                            )
                        ){
                            Row (modifier = Modifier.padding(horizontal = 12.dp, vertical = 24.dp)){
                                Icon(
                                    painter = painterResource(type.icon),
                                    contentDescription = null,
                                    tint = MaterialTheme.colors.secondary
                                )
                                Text(
                                    modifier = Modifier.padding(start = 8.dp),
                                    text = stringResource(type.name),
                                    style = MaterialTheme.typography.body2
                                )
                            }
                        }
                    }
                }
                Text(
                    modifier = Modifier.padding(top= 12.dp),
                    text = stringResource(MainRes.strings.booking_period),
                    style = MaterialTheme.typography.subtitle1,
                    color = Color.Black
                )
                Row (modifier = Modifier.padding(top = 8.dp).clickable(onClick = onClickOpenBookPeriod), verticalAlignment = Alignment.CenterVertically){
                    IconButton(
                        onClick = onClickOpenBookPeriod
                    ){
                        Icon(
                            modifier = Modifier.size(24.dp),
                            painter = painterResource(MainRes.images.material_calendar_ic),
                            contentDescription = null,
                            tint = MaterialTheme.colors.secondary
                        )
                    }
                    Text(
                        text = "Пт, 30 июня 12:00 — 14:00", //TODO("Получать текст из календаря")
                        modifier = Modifier.padding(start = 8.dp),
                        style = MaterialTheme.typography.body2
                    )
                }
            }
        }
    }
}

