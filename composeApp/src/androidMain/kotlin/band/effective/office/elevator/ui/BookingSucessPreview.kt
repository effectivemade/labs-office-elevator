package band.effective.office.elevator.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import band.effective.office.elevator.ui.booking.components.BookingPeriod
import band.effective.office.elevator.ui.booking.components.BookingSuccess


@Preview(showBackground = true)
@Composable
fun BookingSuccessPreview() {
    val switchValue = remember {
        mutableStateOf(value = false)
    }
    BookingPeriod(
        startDate = "Чт, 27 июл. 2023 г.", //Дата начала букинга
        startTime = "10:30", //Время начала букинга
        finishDate = "Чт, 27 июл. 2023 г.", //Дата конца букинга
        finishTime = "15:30", //Время конца букинга
        repeatBooking = "Бронирование не повторяется", //Режим повтора букинга
        closeClick = {
                     //Кнопка закрытия всей модалки
        },
        onSwitchChange = { switch ->
            switchValue.value = switch
            //Для смены свича
        },
        switchChecked = switchValue.value,
        bookStartDate = {
            //Кнопка для выбора начальной даты
        },
        bookFinishDate = {
            //Кнопка для выбора конечной даты
        },
        confirmBooking = {
            //Кнопка для подтверждения букинга
        },
        bookingRepeat = {
            //Кнопка выбора режима повтора букинга
        }
    )
}