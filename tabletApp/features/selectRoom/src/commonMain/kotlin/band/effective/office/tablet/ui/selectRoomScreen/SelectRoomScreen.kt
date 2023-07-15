package band.effective.office.tablet.ui.selectRoomScreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.window.Dialog
import band.effective.office.tablet.ui.selectRoomScreen.successBooking.SuccessSelectRoomView

@Composable
fun SelectRoomScreen(component: SelectRoomComponentImpl){
    val state by component.state.collectAsState()

    Dialog(
        onDismissRequest = { component.close() }
    )
    {
        println("AGAIN")
        when {
            state.isData -> {
                SelectRoomView(component)
            }

            state.isLoad -> {
                /* (Margarita Djinjolia)
            not in design */
            }

            state.isError -> {
                /* (Margarita Djinjolia)
            not in design */
            }

            else -> {
                SuccessSelectRoomView(component)
            }
        }
    }

}