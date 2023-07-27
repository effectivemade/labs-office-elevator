package band.effective.office.tablet.ui.freeNegotiationsScreen.ui.freeNegotiationsScreen

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import band.effective.office.tablet.domain.model.EventInfo
import band.effective.office.tablet.ui.freeNegotiationsScreen.ui.freeNegotiationsScreen.store.FreeNegotiationsStore


@Composable
fun FreeNegotiationsScreen(component: FreeNegotiationsComponent) {
    val state by component.state.collectAsState()

    component.onIntent(FreeNegotiationsStore.Intent.SetEvent(EventInfo.emptyEvent))

    Text(
        text = state.eventInfo.organizer,
        color = Color.Red
    )
}