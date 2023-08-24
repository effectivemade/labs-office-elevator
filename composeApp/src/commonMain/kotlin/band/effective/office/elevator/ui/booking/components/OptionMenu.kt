package band.effective.office.elevator.ui.booking.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import band.effective.office.elevator.ExtendedThemeColors
import band.effective.office.elevator.MainRes
import band.effective.office.elevator.ui.booking.components.modals.noPeriodReserve
import band.effective.office.elevator.ui.booking.models.Frequency
import band.effective.office.elevator.ui.booking.models.WorkSpaceType
import band.effective.office.elevator.ui.models.TypesList
import band.effective.office.elevator.utils.MonthLocalizations
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource
import kotlinx.datetime.LocalDate

@Composable
fun OptionMenu(
    isExpandedCard: Boolean,
    isExpandedOptions: Boolean,
    onClickOpenBookPeriod: () -> Unit,
    onClickChangeZone: (WorkSpaceType) -> Unit,
    startDate: LocalDate,
    finishDate: LocalDate,
    frequency: Frequency,
    repeatBooking: StringResource,
    onClickChangeSelectedType: (TypesList) -> Unit,
    selectedTypesList: TypesList
) {
    val startMonth = MonthLocalizations.getMonthName(
        month = startDate.month,
        locale = Locale(languageTag = Locale.current.language)
    )

    val finishMonth = MonthLocalizations.getMonthName(
        month = finishDate.month,
        locale = Locale(languageTag = Locale.current.language)
    )

    val startDay = startDate.dayOfMonth
    val finishDay = finishDate.dayOfMonth

    val startYear = startDate.year
    val finishYear = finishDate.year

    val repeatBookingsOnShow = when(repeatBooking){
        MainRes.strings.every_work_day_repeat ->  stringResource( repeatBooking) + " "
        MainRes.strings.every_week -> stringResource( repeatBooking) + " "
        MainRes.strings.every_month -> stringResource( repeatBooking) + " "
        else -> ""
    }
    val period = when(frequency.getResearchEnd().third){
        "Week" -> "недели"
        "Month" -> "месяц"
        else -> "лет"
    }
    val periodicity = when(frequency.getResearchEnd().first.first) {
        //"ThisDay" -> ""
        "Never" -> "раз в ${frequency.getResearchEnd().second} ${period} c "
        "Date" -> "раз в ${frequency.getResearchEnd().second} ${period} c "
        else -> ""
    }
    val extendedPeriodInfo = when(frequency.getResearchEnd().first.first){
        "Date" -> " по ${frequency.getResearchEnd().first.second}"
        "CoupleTimes" -> "в ближайшие ${frequency.getResearchEnd().first.second} ${period}"
        else -> ""
    }

    val date = repeatBookingsOnShow + periodicity +
        if (startYear == finishYear) if (startMonth == finishMonth) if (startDay == finishDay) "$startDay $startMonth $startYear" else "$startDay - $finishDay $startMonth $startYear"
        else "$startDay $startMonth - $finishDay $finishMonth $startYear"
        else "$startDay $startMonth $startYear - $finishDay $finishMonth $finishYear" +
                extendedPeriodInfo




    Column {
        AnimatedVisibility(visible = isExpandedCard) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
                    .background(color = Color.White)
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Image(
                        modifier = Modifier.padding(vertical = 20.dp),
                        painter = painterResource(MainRes.images.super_map),
                        contentDescription = "office map"
                    )
                }
            }
        }

        AnimatedVisibility(visible = isExpandedOptions) {
            Column(modifier = Modifier.padding(top = 16.dp).padding(horizontal = 16.dp)) {
                Text(
                    text = stringResource(MainRes.strings.type_booking),
                    style = MaterialTheme.typography.subtitle1,
                    color = ExtendedThemeColors.colors.blackColor
                )
                Row(
                    modifier = Modifier.padding(top = 8.dp).fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    val types = listOf(
                        TypesList(
                            name = MainRes.strings.workplace,
                            icon = MainRes.images.table_icon,
                            type = WorkSpaceType.WORK_PLACE
                        ),
                        TypesList(
                            name = MainRes.strings.meeting_room,
                            icon = MainRes.images.icon_meet,
                            type = WorkSpaceType.MEETING_ROOM
                        )
                    )
                    val selectedType = remember { mutableStateOf(selectedTypesList) }

                    types.forEach { type ->
                        val selected = selectedType.value == type
                        Box(
                            modifier = Modifier.padding(
                                end = 12.dp
                            ).border(
                                width = 1.dp,
                                shape = RoundedCornerShape(8.dp),
                                color = MaterialTheme.colors.secondary
                            ).background(
                                color = if (selected) {
                                    MaterialTheme.colors.background
                                } else {
                                    ExtendedThemeColors.colors.whiteColor
                                }
                            ).selectable(
                                selected = selected,
                                onClick = {
                                    selectedType.value = type
                                    onClickChangeSelectedType(selectedType.value)
                                    onClickChangeZone(type.type)
                                }
                            )
                        ) {
                            Row(modifier = Modifier.padding(horizontal = 12.dp, vertical = 24.dp)) {
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
                    modifier = Modifier.padding(top = 12.dp),
                    text = stringResource(MainRes.strings.booking_period),
                    style = MaterialTheme.typography.subtitle1,
                    color = ExtendedThemeColors.colors.blackColor
                )
                Row(
                    modifier = Modifier.padding(top = 8.dp)
                        .clickable(onClick = onClickOpenBookPeriod),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = onClickOpenBookPeriod
                    ) {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            painter = painterResource(MainRes.images.material_calendar_ic),
                            contentDescription = null,
                            tint = MaterialTheme.colors.secondary
                        )
                    }
                    Text(
                        text = date,
                        modifier = Modifier.padding(start = 8.dp),
                        style = MaterialTheme.typography.body2
                    )
                }
            }
        }
    }
}