package band.effective.office.tablet.ui.mainScreen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import band.effective.office.tablet.ui.mainScreen.components.MainScreenComponent

@RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
@Composable
fun MainScreen(component: MainComponent) {
    val state by component.state.collectAsState()
    when{
        state.isError -> {}
        state.isLoad -> {}
        state.isData -> {
            MainScreenComponent(
                room = state.roomInfo,
                onSelectOtherRoom = { component.sendEvent(MainScreenEvent.OnCLick) },
                mockComponent = component.mockSettingsComponent
            )
        }
    }
}