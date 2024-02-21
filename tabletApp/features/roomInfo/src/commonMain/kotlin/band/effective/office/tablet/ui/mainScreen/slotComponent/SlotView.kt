package band.effective.office.tablet.ui.mainScreen.slotComponent

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.animateContentSize
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
import band.effective.office.tablet.ui.mainScreen.slotComponent.model.SlotUi
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
        slots = state.slots
    ) { component.sendIntent(SlotStore.Intent.ClickOnSlot(this)) }
}

@Composable
private fun SlotList(slots: List<SlotUi>, onClick: SlotUi.() -> Unit) {
    val borderShape = CircleShape

    LazyColumn(Modifier.padding(start = 30.dp, top = 0.dp, end = 30.dp, bottom = 30.dp)) {
        items(items = slots) {
            val itemModifier = Modifier
                .fillMaxWidth()
                .clip(borderShape)
                .background(MaterialTheme.colors.surface)
                .clickable { it.onClick() }
            when (it) {
                is SlotUi.DeleteSlot -> TODO()
                is SlotUi.MultiSlot -> MultiSlotView(
                    mainItemModifier = itemModifier.border(
                        width = 5.dp,
                        color = it.slot.borderColor(),
                        shape = borderShape
                    )
                        .padding(vertical = 15.dp, horizontal = 30.dp),
                    itemModifier = itemModifier.border(
                        width = 5.dp,
                        color = Color.Yellow,
                        shape = borderShape
                    ).padding(vertical = 15.dp, horizontal = 30.dp), //TODO() collor
                    slotUi = it,
                    onItemClick = { it.onClick() }
                )

                is SlotUi.SimpleSlot -> SlotView(
                    modifier = itemModifier.border(
                        width = 5.dp,
                        color = it.slot.borderColor(),
                        shape = borderShape
                    )
                        .padding(vertical = 15.dp, horizontal = 30.dp),
                    slotUi = it
                )
            }
            Spacer(Modifier.height(20.dp))
        }
    }
}

@Composable
private fun MultiSlotView(
    mainItemModifier: Modifier = Modifier,
    itemModifier: Modifier = Modifier,
    slotUi: SlotUi.MultiSlot,
    onItemClick: (SlotUi) -> Unit
) {
    Column(Modifier.animateContentSize()) {
        SlotView(mainItemModifier, slotUi, slotUi.isOpen)
        if (slotUi.isOpen) {
            slotUi.subSlots.forEach {
                Spacer(Modifier.height(20.dp))
                SlotView(itemModifier.clickable { onItemClick(it) }, it)
            }
        }
    }
}

@Composable
private fun SlotView(modifier: Modifier = Modifier, slotUi: SlotUi, isOpen: Boolean? = null) {
    val slot = slotUi.slot
    Row(
        modifier = modifier,
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