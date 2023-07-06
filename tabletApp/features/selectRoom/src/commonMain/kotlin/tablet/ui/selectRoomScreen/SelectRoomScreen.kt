package tablet.ui.selectRoomScreen

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@Composable
fun SelectRoomScreen(component: RealSelectRoomComponent){
    val state by component.state.collectAsState()

    when{
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
    }

}

@Composable
fun SelectRoomView(
    component: RealSelectRoomComponent
){
    Text(text = component.booking.nameRoom)
}