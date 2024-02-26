package band.effective.office.tablet.ui.mainScreen.slotComponent

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathMeasure
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import band.effective.office.tablet.domain.model.Slot
import band.effective.office.tablet.features.roomInfo.MainRes
import band.effective.office.tablet.ui.mainScreen.slotComponent.model.SlotUi
import band.effective.office.tablet.ui.mainScreen.slotComponent.store.SlotStore
import band.effective.office.tablet.ui.theme.LocalCustomColorsPalette
import band.effective.office.tablet.ui.theme.deleteProgressColor
import band.effective.office.tablet.ui.theme.h7
import band.effective.office.tablet.ui.theme.subslotColor
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Calendar

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun SlotList(component: SlotComponent) {
    val state by component.state.collectAsState()
    SlotList(
        slots = state.slots,
        onClick = { component.sendIntent(SlotStore.Intent.ClickOnSlot(this)) },
        onCancel = { component.sendIntent(SlotStore.Intent.OnCancelDelete(it)) }
    )
}

@Composable
private fun SlotList(
    slots: List<SlotUi>,
    onClick: SlotUi.() -> Unit,
    onCancel: (SlotUi.DeleteSlot) -> Unit
) {
    val borderShape = CircleShape

    LazyColumn(Modifier.padding(start = 30.dp, top = 0.dp, end = 30.dp, bottom = 30.dp)) {
        items(items = slots) {
            val itemModifier = Modifier
                .fillMaxWidth()
                .clip(borderShape)
                .background(MaterialTheme.colors.surface)
                .clickable { it.onClick() }
            when (it) {
                is SlotUi.DeleteSlot -> DeletedSlotView(
                    modifier = itemModifier,
                    slotUi = it,
                    onCancel = onCancel,
                    paddingValues = PaddingValues(vertical = 15.dp, horizontal = 30.dp)
                )

                is SlotUi.MultiSlot -> MultiSlotView(
                    mainItemModifier = itemModifier.border(
                        width = 5.dp,
                        color = it.slot.borderColor(),
                        shape = borderShape
                    )
                        .padding(vertical = 15.dp, horizontal = 30.dp),
                    itemModifier = itemModifier.border(
                        width = 5.dp,
                        color = subslotColor,
                        shape = borderShape
                    ).padding(vertical = 15.dp, horizontal = 30.dp),
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
private fun DeletedSlotView(
    modifier: Modifier = Modifier,
    slotUi: SlotUi.DeleteSlot,
    onCancel: (SlotUi.DeleteSlot) -> Unit,
    paddingValues: PaddingValues
) {
    Box(modifier = modifier.height(IntrinsicSize.Min).width(IntrinsicSize.Min)) {
        var isCancelability by remember { mutableStateOf(true) }
        SlotView(modifier = modifier.padding(paddingValues), slotUi = slotUi) {
            if (isCancelability) {
                Text(
                    modifier = Modifier.clickable { onCancel(slotUi) },
                    text = MainRes.string.cancel,
                    style = MaterialTheme.typography.h7,
                    color = Color.White
                )
            }
        }
        BorderIndicator(onDispose = {
            slotUi.onDelete()
            isCancelability = false
        }, stokeWidth = 10.dp)
    }
}

//https://stackoverflow.com/questions/75745905/rectangle-border-progress-bar
@Composable
private fun BorderIndicator(
    modifier: Modifier = Modifier.fillMaxSize(),
    startDurationInSeconds: Int = 10,
    stokeWidth: Dp,
    onDispose: () -> Unit
) {


    var targetValue by remember {
        mutableStateOf(100f)
    }
    // This is the progress path which wis changed using path measure
    val pathWithProgress by remember { mutableStateOf(Path()) }

    // using path
    val pathMeasure by remember { mutableStateOf(PathMeasure()) }

    val path = remember { Path() }

    val progress by animateFloatAsState(
        targetValue = targetValue,
        animationSpec = tween(startDurationInSeconds * 1000, easing = LinearEasing), label = ""
    )

    Box(contentAlignment = Alignment.Center) {
        Canvas(modifier = modifier) {
            if (path.isEmpty) {
                path.addRoundRect(
                    RoundRect(
                        Rect(offset = Offset.Zero, size),
                        cornerRadius = CornerRadius(100.dp.toPx(), 100.dp.toPx())
                    )
                )
            }
            pathWithProgress.reset()
            pathMeasure.setPath(path, forceClosed = false)
            pathMeasure.getSegment(
                startDistance = 0f,
                stopDistance = pathMeasure.length * progress / 100f,
                destination = pathWithProgress,
                startWithMoveTo = true
            )

            drawPath(
                path = path,
                style = Stroke(stokeWidth.toPx()),
                color = Color.Gray
            )

            drawPath(
                path = pathWithProgress,
                style = Stroke(stokeWidth.toPx()),
                color = deleteProgressColor
            )
        }
    }
    LaunchedEffect(Unit) {
        targetValue = 0f
        delay(startDurationInSeconds * 1000L)
        onDispose()
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
        SlotView(mainItemModifier, slotUi) {
            val rotateDegrees = if (slotUi.isOpen) 180f else 0f
            Image(
                modifier = Modifier.fillMaxHeight().rotate(rotateDegrees),
                painter = painterResource(MainRes.image.arrow_to_down),
                contentDescription = null
            )
        }
        if (slotUi.isOpen) {
            slotUi.subSlots.forEach {
                Spacer(Modifier.height(20.dp))
                SlotView(itemModifier.clickable { onItemClick(it) }, it)
            }
        }
    }
}

@Composable
private fun SlotView(
    modifier: Modifier = Modifier,
    slotUi: SlotUi,
    content: @Composable () -> Unit = {}
) {
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
        content()
    }
}

@Composable
private fun Slot.borderColor() = when (this) {
    is Slot.EmptySlot -> LocalCustomColorsPalette.current.freeStatus
    is Slot.EventSlot -> LocalCustomColorsPalette.current.busyStatus
    is Slot.MultiEventSlot -> LocalCustomColorsPalette.current.busyStatus
}

private fun Slot.subtitle() = when (this) {
    is Slot.EmptySlot -> MainRes.string.empty_slot.format(freeTime(this).toString())
    is Slot.EventSlot -> MainRes.string.event_slot.format(eventInfo.organizer.fullName)
    is Slot.MultiEventSlot -> MainRes.string.multislot.format(events.size.toString())
}

fun freeTime(slot: Slot): Int {
    val timeInMillis = slot.finish.timeInMillis - slot.start.timeInMillis
    return when {
        timeInMillis > 0 -> (timeInMillis / 60000).toInt()
        else -> 0
    }
}

private fun Calendar.toFormattedString(pattern: String) = SimpleDateFormat(pattern).format(time)