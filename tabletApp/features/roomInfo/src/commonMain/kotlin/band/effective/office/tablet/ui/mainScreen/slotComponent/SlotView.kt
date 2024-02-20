package band.effective.office.tablet.ui.mainScreen.slotComponent

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import band.effective.office.tablet.domain.model.Slot
import band.effective.office.tablet.features.roomInfo.MainRes
import band.effective.office.tablet.ui.mainScreen.slotComponent.store.SlotStore
import band.effective.office.tablet.ui.theme.LocalCustomColorsPalette
import band.effective.office.tablet.ui.theme.h7
import java.text.SimpleDateFormat
import java.util.Calendar

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun SlotList(component: SlotComponent) {
    val state by component.state.collectAsState()
    SlotList(
        slots = state.slots,
        openSlots = state.openMultiSlots
    ) { component.sendIntent(SlotStore.Intent.ClickOnSlot(this)) }
}

@Composable
private fun SlotList(slots: List<Slot>, openSlots: List<Slot>, onClick: Slot.() -> Unit) {
    LazyColumn(Modifier.padding(start = 30.dp, top = 0.dp, end = 30.dp, bottom = 30.dp)) {
        items(items = slots) {
            val isOpen =
                when {
                    it !is Slot.MultiEventSlot -> null
                    openSlots.contains(it) -> true
                    else -> false
                }
            SlotView(slot = it, isOpen = isOpen, onClick = onClick)
            Spacer(Modifier.height(20.dp))
        }
    }
}

@Composable
private fun SlotView(slot: Slot, isOpen: Boolean?, onClick: Slot.() -> Unit) {
    val borderShape = CircleShape
    Row(modifier = Modifier
        .fillMaxWidth()
        .clip(borderShape)
        .border(width = 5.dp, color = slot.borderColor(), shape = borderShape)
        .background(MaterialTheme.colors.surface)
        .padding(vertical = 15.dp, horizontal = 30.dp)
        .clickable { slot.onClick() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = "${slot.start.toFormattedString("HH:mm")} - ${slot.finish.toFormattedString("HH:mm")}",
                style = MaterialTheme.typography.h5,
                color = MaterialTheme.colors.onPrimary
            )
            Text(
                text = slot.subtitle(),
                style = MaterialTheme.typography.h7,
                color = Color.White
            )
        }
        if (isOpen != null) {
            val rotateDegrees = if (isOpen) 180f else 0f
            Image(
                modifier = Modifier.fillMaxHeight().rotate(rotateDegrees),
                painter = painterResource(MainRes.image.arrow_to_down),
                contentDescription = null
            )
        }
    }
}

@Composable
private fun Slot.borderColor() = when (this) {
    is Slot.EmptySlot -> LocalCustomColorsPalette.current.freeStatus
    is Slot.EventSlot -> LocalCustomColorsPalette.current.busyStatus //TODO()
    is Slot.MultiEventSlot -> LocalCustomColorsPalette.current.busyStatus
}

private fun Slot.subtitle() = when (this) {
    is Slot.EmptySlot -> "Свободно" //TODO: get text from figma
    is Slot.EventSlot -> "Занято ${eventInfo.organizer.fullName}"
    is Slot.MultiEventSlot -> "${events.size} брони" //TODO склонеия
}

private fun Calendar.toFormattedString(pattern: String) = SimpleDateFormat(pattern).format(time)