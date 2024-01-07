package band.effective.office.tablet.ui.mainScreen.slotComponent

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import band.effective.office.tablet.domain.model.Slot
import java.text.SimpleDateFormat

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun SlotList(component: SlotComponent) {
    val state by component.state.collectAsState()
    SlotList(state.slots)
}

@Composable
fun SlotList(slots: List<Slot>) {
    LazyColumn {
        items(items = slots) {
            SlotView(it)
        }
    }
}

@Composable
fun SlotView(slot: Slot) {
    when (slot) {
        is Slot.EmptySlot -> EmptySlotView(slot)
        is Slot.EventSlot -> EventSlotView(slot)
    }
}

@Composable
fun EmptySlotView(slot: Slot.EmptySlot) {
    val formatter = SimpleDateFormat("HH:mm")
    Text("${formatter.format(slot.start.time)} - ${formatter.format(slot.finish.time)}")
}

@Composable
fun EventSlotView(slot: Slot.EventSlot) {
    val formatter = SimpleDateFormat("HH:mm")
    Text("${formatter.format(slot.start.time)} - ${formatter.format(slot.finish.time)}: ${slot.eventInfo.organizer}")
}