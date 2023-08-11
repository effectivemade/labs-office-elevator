package band.effective.office.elevator.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import band.effective.office.elevator.ui.booking.components.modals.BookingRepeat

@Preview(showBackground = true)
@Composable
fun BookingRepeat() {
    BookingRepeat(
        backButtonClicked = {
            //Закрытие диалогового окна
        }, dropDownClick = {
            //Выпадающий список
        }, confirmBooking = {
            //Подтвердить
        },
        onSelected = {
            //Нажатие на Radio button
        },
        onDaySelected = { selectedDay ->
            //Нажатие на день недели
        })
}