package band.effective.office.tablet.ui.selectRoomScreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@Composable
fun SelectRoomScreen(component: RealSelectRoomComponent){
    val state by component.state.collectAsState()

    when{
        state.isData -> {
           // SelectRoomView(component)
            CheckButton(component)
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