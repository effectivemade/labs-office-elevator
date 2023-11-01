package band.effective.office.elevator.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.intl.Locale
import band.effective.office.elevator.MainRes
import band.effective.office.elevator.domain.models.BookingPeriod
import band.effective.office.elevator.domain.models.TypeEndPeriodBooking
import band.effective.office.elevator.domain.models.listToString
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.compose.stringResource
import kotlinx.datetime.LocalDate

@Composable
fun stringFromBookPeriod(
    startDate: LocalDate,
    finishDate: LocalDate,
    bookingPeriod: BookingPeriod,
    typeEndPeriodBooking: TypeEndPeriodBooking,
    repeatBooking: StringResource
): String {

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

    val repeatBookingsOnShow = when (repeatBooking) {
        MainRes.strings.every_work_day_repeat -> stringResource(repeatBooking) + " "
        MainRes.strings.every_week -> stringResource(repeatBooking) + " "
        MainRes.strings.every_month -> stringResource(repeatBooking) + " "
        else -> ""
    }
    val period =
        when (bookingPeriod) {
            is BookingPeriod.Week -> "недел(и) "
            is BookingPeriod.Month -> "месяц(ев) "
            is BookingPeriod.Year -> "лет "
            else -> ""
        }

    val periodicity = when (bookingPeriod) {
        is BookingPeriod.Week -> "раз в ${bookingPeriod.durationPeriod}  $period (${bookingPeriod.selectedDayOfWeek.listToString()}) c "
        is BookingPeriod.Month -> "раз в ${bookingPeriod.monthPeriod}  $period"
        is BookingPeriod.Year -> "раз в ${bookingPeriod.yearPeriod}  $period"
        else -> ""
    }

    val extendedPeriodInfo = when (typeEndPeriodBooking) {
        is TypeEndPeriodBooking.DatePeriodEnd -> " по ${dateFormat(typeEndPeriodBooking.date)}"
        is TypeEndPeriodBooking.CountRepeat ->
            "повторится ${typeEndPeriodBooking.count} раз(а)"

        else -> ""
    }


    return if (repeatBookingsOnShow == "")
        periodicity +
        if (startYear == finishYear)
            if (startMonth == finishMonth)
                if (startDay == finishDay)
                    "$startDay $startMonth $startYear"
                else "$startDay - $finishDay $startMonth $startYear"
            else "$startDay $startMonth - $finishDay $finishMonth $startYear"
        else "$startDay $startMonth $startYear - $finishDay $finishMonth $finishYear" +
                extendedPeriodInfo
    else repeatBookingsOnShow
}