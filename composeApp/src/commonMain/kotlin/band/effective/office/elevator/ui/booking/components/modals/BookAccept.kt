package band.effective.office.elevator.ui.booking.components.modals

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import band.effective.office.elevator.utils.DayOfWeekLocalizations
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import band.effective.office.elevator.ExtendedThemeColors
import band.effective.office.elevator.MainRes
import band.effective.office.elevator.components.EffectiveButton
import band.effective.office.elevator.domain.models.BookingInfo
import band.effective.office.elevator.domain.models.BookingPeriod
import band.effective.office.elevator.textInBorderGray
import band.effective.office.elevator.ui.booking.models.Frequency
import band.effective.office.elevator.utils.MonthLocalizations
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun BookAccept(
    onClickCloseBookAccept: () -> Unit,
    confirmBooking: () -> Unit,
    bookingInfo: BookingInfo,
    frequency: Frequency,
    period: BookingPeriod
) {
    var value = with(bookingInfo) {
        when (period) {
            is BookingPeriod.Another -> "${frequency.toString()} ${dateOfStart.date.dayOfMonth} " + MonthLocalizations.getMonthName(
                month = dateOfStart.date.month,
                locale = Locale.current
            )

            is BookingPeriod.EveryWorkDay -> stringResource(MainRes.strings.every_work_day)
            is BookingPeriod.Month -> stringResource(MainRes.strings.every_month) + ", " + dateOfStart.dayOfMonth
            is BookingPeriod.NoPeriod -> stringResource(MainRes.strings.booking_not_repeat)
            is BookingPeriod.Week -> stringResource(MainRes.strings.every_week) + ", в " + DayOfWeekLocalizations.getDayOfWeek(
                dayOfWeek = dateOfStart.dayOfWeek,
                locale = Locale.current
            )

            is BookingPeriod.Year -> stringResource(MainRes.strings.every_month)
        }
    }

    value += with(bookingInfo) {
        " ${dateOfStart.time.hour.toString().padStart(2, '0')}:${
            dateOfStart.time.minute.toString().padStart(2, '0')
        } - ${dateOfEnd.time.hour.toString().padStart(2, '0')}:${
            dateOfStart.time.minute.toString().padStart(2, '0')
        }"
    }
    Box {
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
                    onClick = onClickCloseBookAccept,
                    modifier = Modifier
                        .align(Alignment.Top)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Close,
                        contentDescription = "Krestik",
                        tint = ExtendedThemeColors.colors.blackColor
                    )
                }
                Column(modifier = Modifier.padding(horizontal = 5.dp)) {
                    Text(
                        text = bookingInfo.seatName,
                        style = MaterialTheme.typography.subtitle1,
                        fontSize = 20.sp,
                        fontWeight = FontWeight(600),
                        color = ExtendedThemeColors.colors.blackColor,
                        modifier = Modifier.padding(top = 10.dp, bottom = 5.dp)
                    )
                    Text(
                        text = with(bookingInfo) {
                            "${frequency.toString()} ${dateOfStart.date.dayOfMonth} " + MonthLocalizations.getMonthName(
                                month = dateOfStart.date.month,
                                locale = Locale(languageTag = Locale.current.language)
                            ) + " ${dateOfStart.time.hour.toString()}:${
                                with(
                                    dateOfStart.time
                                ) { if (minute.toString().length < 2) "0$minute" else minute.toString() }
                            } - ${dateOfEnd.time.hour.toString()}:${
                                with(
                                    dateOfStart.time
                                ) {
                                    if (minute.toString().length < 2) "0$minute" else minute.toString()
                                }
                            }"
                        },
                        style = MaterialTheme.typography.subtitle1,
                        fontSize = 16.sp,
                        fontWeight = FontWeight(400),
                        color = textInBorderGray,
                        modifier = Modifier.padding(bottom = 27.dp)
                    )
                }
            }
            EffectiveButton(
                buttonText = stringResource(MainRes.strings.confirm_booking),
                onClick = confirmBooking,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
            )
        }
    }
}