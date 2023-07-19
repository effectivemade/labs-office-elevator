package band.effective.office.tablet.ui.selectRoomScreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.window.Dialog
import band.effective.office.tablet.ui.selectRoomScreen.successBooking.SuccessSelectRoomView

@Composable
fun SelectRoomScreen(component: SelectRoomComponent) {
    val state by component.state.collectAsState()

    Dialog(
        onDismissRequest = { component.close() }
    )
    {
        component.onBooking()
        when {
            state.isData -> {
                SelectRoomView(
                    state.booking,
                    { component.close() },
                    { component.bookRoom() }
                )
            }

            state.isSuccess -> {
                SuccessSelectRoomView(
                    state.booking
                ) { component.close() }
            }

            state.isError -> {
                /* (Margarita Djinjolia)
            not in design */
            }

            else -> {
                /* (Margarita Djinjolia)
            no load in design */
            }
        }
    }

}