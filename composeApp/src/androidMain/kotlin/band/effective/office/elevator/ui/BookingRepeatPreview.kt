package band.effective.office.elevator.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import band.effective.office.elevator.ui.booking.components.modals.BookingRepeatCard

@Preview(showBackground = true)
@Composable
fun BookingRepeatPreview() {
    BookingRepeatCard(onSelected = {
        //При нажатии на radio button
    })
}